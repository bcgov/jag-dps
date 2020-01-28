package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidateApplicantForSharingControllerTest {


    private static final String VALID_STATUS_CODE = "0";
    private static final String INVALID_STATUS_CODE = "-2";
    private static final String VALID = "valid";
    private static final String INVALID = "invalid";
    private static final String VALIDATION_RESULT = "validationresult";

    private ValidateApplicantForSharingController sut;

    @Mock
    private FigaroValidation figaroValidationMock;

    @BeforeAll
    public void init() throws FigaroValidationServiceException {

        ValidateApplicantForSharingOrdsResponse validResponse =
                new ValidateApplicantForSharingOrdsResponse().statusCode(VALID_STATUS_CODE).statusMessage(VALID).validationResult(VALIDATION_RESULT);
        ValidateApplicantForSharingOrdsResponse erroResponse =
                new ValidateApplicantForSharingOrdsResponse().statusCode(INVALID_STATUS_CODE).statusMessage(INVALID);

        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(validResponse).when(figaroValidationMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("1")));
        Mockito.doReturn(erroResponse).when(figaroValidationMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("2")));
        Mockito.doThrow(FigaroValidationServiceException.class).when(figaroValidationMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("EXCEPTION")));

        sut = new ValidateApplicantForSharingController(figaroValidationMock);

    }


    @Test
    public void withValidParametersShouldReturnValidResponse() {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("1", "TEST");
        Assertions.assertEquals(VALID_STATUS_CODE, response.getRespCode());
        Assertions.assertEquals(VALID, response.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, response.getValidationResult());
    }

    @Test
    public void withInvlidResponseShouldReturnValidResponse() {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("2", "TEST");
        Assertions.assertEquals(INVALID_STATUS_CODE, response.getRespCode());
        Assertions.assertEquals(INVALID, response.getRespMsg());
    }

    @Test
    public void withOrdsThrowExceptionShouldReturnErrorResponse() {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("EXCEPTION", "TEST");
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE,
                response.getRespMsg());
    }


}
