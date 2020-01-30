package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author shaunmillargov
 * <p>
 * ValidateApplicantPartyId Controller test class.
 * <p>
 * Mocks underlying ORDS service class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantServiceValidateApplicantService {


    private static final String VALIDATION_RESULT = "result";
    private static final String STATUS_MESSAGE = "success";
    private static final String STATUS_CODE = "0";
    private static final String ERROR_VALIDATION_RESULT = "error_result";
    private static final String ERROR_STATUS_MESSAGE = "fail";
    private static final String ERROR_STATUS_CODE = "-2";
    
    @Mock
    private ApplicantApi applicantApiMock;

    private ApplicantServiceImpl sut;

    @BeforeAll
    public void SetUp() throws ApiException {

        MockitoAnnotations.initMocks(this);

        ValidateApplicantServiceOrdsResponse successResponse = new ValidateApplicantServiceOrdsResponse();
        successResponse.setValidationResult(VALIDATION_RESULT);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        ValidateApplicantServiceOrdsResponse errorResponse = new ValidateApplicantServiceOrdsResponse();
        errorResponse.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse.setStatusMessage(ERROR_STATUS_MESSAGE);
        errorResponse.setStatusCode(ERROR_STATUS_CODE);

        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq("1"), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq("2"), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq("3"), Mockito.anyString())).thenThrow(ApiException.class);

        sut = new ApplicantServiceImpl(applicantApiMock);

    }

    /**
     * success
     */
    @Test
    public void withValidResponseShouldReturnValidResponse() throws FigaroValidationServiceException {

        ValidateApplicantServiceOrdsResponse response = sut.validateApplicantService("1", "1");

        Assertions.assertEquals(STATUS_CODE, response.getStatusCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getStatusMessage());
        Assertions.assertEquals(VALIDATION_RESULT, response.getValidationResult());

    }

    /**
     * failure to find party id
     */
    @Test
    public void withInvalidResponseShouldReturnInvalidResponse() throws FigaroValidationServiceException {

        ValidateApplicantServiceOrdsResponse response = sut.validateApplicantService("2", "1");

        Assertions.assertEquals(ERROR_STATUS_CODE, response.getStatusCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getStatusMessage());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, response.getValidationResult());

    }

    /**
     * exception test
     */
    @Test
    public void ValidateFigaroControllerException() throws FigaroValidationServiceException {

        Assertions.assertThrows(FigaroValidationServiceException.class, () -> {
            ValidateApplicantServiceOrdsResponse response = sut.validateApplicantService("3", "3");
        });
    }

}

