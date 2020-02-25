package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantServiceValidateApplicantForSharingTest {


    private static final String STATUS_CODE = "1";
    private static final String VALIDATION_RESULT = "valid";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_STATUS_CODE = "-2";
    private static final String ERROR_STATUS_MESSAGE = "error";
    private static final String ERROR_VALIDATION_RESULT = "invalid";
    private static final String API_EXCEPTION = "api exception";
    private ApplicantServiceImpl sut;

    @Mock
    private ApplicantApi applicantApiMock;

    @BeforeAll
    public void setup() throws ApiException {

        MockitoAnnotations.initMocks(this);

        ValidateApplicantForSharingOrdsResponse successResponse = new ValidateApplicantForSharingOrdsResponse();
        successResponse.setStatusCode(STATUS_CODE);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setValidationResult(VALIDATION_RESULT);

        ValidateApplicantForSharingOrdsResponse errorResponse = new ValidateApplicantForSharingOrdsResponse();
        errorResponse.setStatusCode(ERROR_STATUS_CODE);
        errorResponse.setStatusMessage(ERROR_STATUS_MESSAGE);
        errorResponse.setValidationResult(ERROR_VALIDATION_RESULT);

        Mockito.when(applicantApiMock.validateApplicantForSharing(Mockito.eq("1"), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(applicantApiMock.validateApplicantForSharing(Mockito.eq("2"), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(applicantApiMock.validateApplicantForSharing(Mockito.eq("3"), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));

        sut = new ApplicantServiceImpl(applicantApiMock);

    }


    @Test
    public void withValidResponseShouldReturnValidResponse() throws FigaroValidationServiceException {

        ValidateApplicantForSharingOrdsResponse response = sut.validateApplicantForSharing(new ValidateApplicantForSharingRequest("1", "type"));

        Assertions.assertEquals(STATUS_CODE, response.getStatusCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getStatusMessage());
        Assertions.assertEquals(VALIDATION_RESULT, response.getValidationResult());

    }

    @Test
    public void withErrorResponseShouldReturnErrorResponse() throws FigaroValidationServiceException {

        ValidateApplicantForSharingOrdsResponse response = sut.validateApplicantForSharing(new ValidateApplicantForSharingRequest("2", "type"));

        Assertions.assertEquals(ERROR_STATUS_CODE, response.getStatusCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getStatusMessage());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, response.getValidationResult());

    }

    @Test
    public void withApiExceptionShouldThrowFigaroException() throws FigaroValidationServiceException {

        Assertions.assertThrows(FigaroValidationServiceException.class, () -> {
            ValidateApplicantForSharingOrdsResponse response = sut.validateApplicantForSharing(new ValidateApplicantForSharingRequest("3", "type"));
        });
    }


}
