package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateOrgApplicantServiceResponse;
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
 * validateOrgApplicantService service test class.
 * <p>
 * Mocks underlying ORDS service class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantServiceValidateOrgTest {


    public static final String CASE_2 = "2";
    private static final String VALIDATION_RESULT = "result";
    private static final String STATUS_MESSAGE = "success";
    private static final String STATUS_CODE = "0";
    private static final String ERROR_VALIDATION_RESULT = "error_result";
    private static final String ERROR_STATUS_MESSAGE = "fail";
    private static final String ERROR_STATUS_CODE = "-2";
    public static final String CASE_1 = "1";
    public static final String CASE_3 = "3";

    @Mock
    private ApplicantApi applicantApiMock;

    private ApplicantServiceImpl sut;

    @BeforeAll
    public void SetUp() throws ApiException {

        MockitoAnnotations.initMocks(this);

        ValidateOrgApplicantServiceOrdsResponse successResponse = new ValidateOrgApplicantServiceOrdsResponse();
        successResponse.setValidationResult(VALIDATION_RESULT);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        ValidateOrgApplicantServiceOrdsResponse errorResponse = new ValidateOrgApplicantServiceOrdsResponse();
        errorResponse.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse.setStatusMessage(ERROR_STATUS_MESSAGE);
        errorResponse.setStatusCode(ERROR_STATUS_CODE);

        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq(CASE_1), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq(CASE_2), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(applicantApiMock.validateOrgApplicantService(Mockito.eq(CASE_3 +
                ""), Mockito.anyString())).thenThrow(ApiException.class);

        sut = new ApplicantServiceImpl(applicantApiMock);
    }

    /**
     * success
     */
    @Test
    public void withValidResponseShouldReturnValidResponse() throws ApiException {

        ValidateOrgApplicantServiceResponse response = sut.validateOrgApplicantService(CASE_1, CASE_1);

        Assertions.assertEquals(Integer.parseInt(STATUS_CODE), response.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, response.getValidationResult());
    }

    /**
     * failure to find party id
     */
    @Test
    public void withInvalidResponseShouldReturnInvalidResponse() throws ApiException {

        ValidateOrgApplicantServiceResponse response = sut.validateOrgApplicantService(CASE_2, CASE_2);

        Assertions.assertEquals(Integer.parseInt(ERROR_STATUS_CODE), response.getRespCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, response.getValidationResult());
    }

    /**
     * exception test
     */
    @Test
    public void WithApiExceptionShouldThrowException() {

        Assertions.assertThrows(ApiException.class, () -> {
            ValidateOrgApplicantServiceResponse response = sut.validateOrgApplicantService(CASE_3, CASE_3);
        });
    }

}

