package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateApplicantForSharingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantControllerValidateApplicantForSharingTest {


    private static final String STATUS_CODE = "1";
    private static final String VALIDATION_RESULT = "valid";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_STATUS_CODE = "-2";
    private static final String ERROR_STATUS_MESSAGE = "error";
    private static final String ERROR_VALIDATION_RESULT = "invalid";
    private static final String API_EXCEPTION = "api exception";

    private ApplicantController sut;

    @Mock
    private ApplicantService applicantServiceMock;

    @BeforeAll
    public void setup() throws ApiException {

        MockitoAnnotations.initMocks(this);

        ValidateApplicantForSharingResponse successResponse = ValidateApplicantForSharingResponse.SuccessResponse(VALIDATION_RESULT, STATUS_CODE, STATUS_MESSAGE);

        ValidateApplicantForSharingResponse errorResponse = ValidateApplicantForSharingResponse.ErrorResponse(ERROR_STATUS_MESSAGE);

        Mockito.doReturn(successResponse).when(applicantServiceMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("1")));
        Mockito.doReturn(errorResponse).when(applicantServiceMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("2")));
        Mockito.doThrow(new ApiException(API_EXCEPTION)).when(applicantServiceMock).validateApplicantForSharing(ArgumentMatchers.argThat(x -> x.getApplPartyId().equals("3")));

        sut = new ApplicantController(applicantServiceMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() throws ApiException {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("1", "type");

        Assertions.assertEquals(1, response.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, response.getValidationResult());
    }

    @Test
    public void withErrorResponseShouldReturnErrorResponse() throws ApiException {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("2", "type");

        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getRespMsg());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, response.getValidationResult());
    }

    @Test
    public void withExceptionShouldReturnErrorResponse() throws ApiException {

        ValidateApplicantForSharingResponse response = sut.validateApplicantForSharing("3", "type");

        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, response.getRespMsg());
        Assertions.assertEquals(API_EXCEPTION, response.getValidationResult());
    }
}
