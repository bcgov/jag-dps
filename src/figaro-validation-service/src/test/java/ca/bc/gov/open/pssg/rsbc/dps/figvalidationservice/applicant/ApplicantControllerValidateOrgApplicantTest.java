package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantServiceImpl;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateOrgApplicantServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantControllerValidateOrgApplicantTest {

    // test class for validateOrgApplicantService methods.

    @Mock
    private ApplicantServiceImpl applicantServiceMock;

    private ValidateOrgApplicantServiceResponse validateOrgApplicantServiceResponse;

    private ApplicantController sut;

    /*
     * Valid input arguments
     */
    private final String VALID_ORG_PARTY_ID = "442";
    private final String VALID_APPL_PARTY_ID = "32432";

    /*
     * In Valid input arguments
     */
    private final String INVALID_ORG_PARTY_ID = "4423";
    private final String INVALID_APPL_PARTY_ID = "3243";

    /**
     * Exception Input argument
     */
    private final String EXCEPTION_ORG_PARTY_ID = "00000";
    private final String EXCEPTION_APPL_PARTY_ID = "00000";

    /*
     * Valid controller response
     */
    private final String VALID_RESPONSE_MESSAGE = "Validation passed.";
    private final int VALID_RESPONSE_CODE = 0;
    private final String VALID_VALIDATION_RESULT = "P";

    /*
     * In Valid org_party_id response
     */
    private final String INVALID_ORG_PARTY_ID_RESPONSE_MESSAGE = "Validation Failure: Organization Party ID 4423 was " +
			"not found.";
    private final String INVALID_VALIDATION_RESULT = "F";

    /*
     * In Valid appl_party_id response
     */
    private final String INVALID_APPL_PARTY_ID_RESPONSE_MESSAGE = "Validation Failure: Applicant Party ID 3243 was " +
			"not found.";

    /*
     * Exception controller response
     */
    private final int EXCEPTION_CONTROLLER_RESPCD = -1;

    /*
     * service response
     *
     */
    private final String VALID_SERVICE_STATUS_CODE = "0";
    private final String VALID_SERVICE_STATUS_MESSAGE = "Validation passed.";
    private final String VALID_SERVICE_VALIDATION_RESULT = "P";

    private final String INVALID_ORG_ID_SERVICE_STATUS_MESSAGE = "Validation Failure: Organization Party ID 4423 was " +
			"not found.";
    private final String INVALID_APPL_ID_SERVICE_STATUS_MESSAGE = "Validation Failure: Applicant Party ID 3243 was " +
			"not found.";

    @BeforeAll
    public void SetUp() throws ApiException {
        MockitoAnnotations.initMocks(this);

        ValidateOrgApplicantServiceResponse validServiceResp = ValidateOrgApplicantServiceResponse.SuccessResponse(
                VALID_SERVICE_VALIDATION_RESULT, VALID_SERVICE_STATUS_CODE, VALID_SERVICE_STATUS_MESSAGE);

        ValidateOrgApplicantServiceResponse inValidOrgIdOrdsServiceResp = ValidateOrgApplicantServiceResponse.ErrorResponse(INVALID_ORG_ID_SERVICE_STATUS_MESSAGE);

        ValidateOrgApplicantServiceResponse inValidApplIdOrdsServiceResp = ValidateOrgApplicantServiceResponse.ErrorResponse(INVALID_APPL_ID_SERVICE_STATUS_MESSAGE);

        Mockito.when(applicantServiceMock.validateOrgApplicantService(VALID_APPL_PARTY_ID, VALID_ORG_PARTY_ID))
                .thenReturn(validServiceResp);

        Mockito.when(applicantServiceMock.validateOrgApplicantService(VALID_APPL_PARTY_ID, INVALID_ORG_PARTY_ID))
                .thenReturn(inValidOrgIdOrdsServiceResp);

        Mockito.when(applicantServiceMock.validateOrgApplicantService(INVALID_APPL_PARTY_ID, VALID_ORG_PARTY_ID))
                .thenReturn(inValidApplIdOrdsServiceResp);

        Mockito.when(
                applicantServiceMock.validateOrgApplicantService(EXCEPTION_ORG_PARTY_ID, EXCEPTION_APPL_PARTY_ID))
                .thenThrow(ApiException.class);

        sut = new ApplicantController(applicantServiceMock);
    }

    /**
     * success
     */
    @Test
    public void validateOrgApplicantServiceControllerSuccess() throws ApiException {

        validateOrgApplicantServiceResponse = sut
                .validateOrgApplicantService(VALID_ORG_PARTY_ID, VALID_APPL_PARTY_ID);
        Assertions.assertEquals(VALID_VALIDATION_RESULT, validateOrgApplicantServiceResponse.getValidationResult());
        Assertions.assertEquals(VALID_RESPONSE_MESSAGE, validateOrgApplicantServiceResponse.getRespMsg());
        Assertions.assertEquals(VALID_RESPONSE_CODE, validateOrgApplicantServiceResponse.getRespCode());
    }

    /**
     * failure response when Invalid Org_Party_Id is passed as input argument
     */
    @Test
    public void InvalidOrgPartyIdFail() throws ApiException {

        validateOrgApplicantServiceResponse = sut
                .validateOrgApplicantService(INVALID_ORG_PARTY_ID, VALID_APPL_PARTY_ID);
        Assertions.assertEquals(INVALID_VALIDATION_RESULT, validateOrgApplicantServiceResponse.getValidationResult());
        Assertions.assertEquals(INVALID_ORG_PARTY_ID_RESPONSE_MESSAGE, validateOrgApplicantServiceResponse.getRespMsg());
        Assertions.assertEquals(-1, validateOrgApplicantServiceResponse.getRespCode());
    }

    /**
     * failure response when Invalid Appl_Party_Id input argument
     */
    @Test
    public void InvalidApplPartyIdFail() throws ApiException {

        validateOrgApplicantServiceResponse = sut
                .validateOrgApplicantService(VALID_ORG_PARTY_ID, INVALID_APPL_PARTY_ID);
        Assertions.assertEquals(INVALID_VALIDATION_RESULT, validateOrgApplicantServiceResponse.getValidationResult());
        Assertions.assertEquals(INVALID_APPL_PARTY_ID_RESPONSE_MESSAGE, validateOrgApplicantServiceResponse.getRespMsg());
        Assertions.assertEquals(-1, validateOrgApplicantServiceResponse.getRespCode());
    }

    /**
     * exception test
     */
    @Test
    public void validateOrgApplicantServiceControllerException() throws ApiException {

        validateOrgApplicantServiceResponse = sut.validateOrgApplicantService(EXCEPTION_ORG_PARTY_ID, EXCEPTION_APPL_PARTY_ID);
        Assertions.assertEquals(EXCEPTION_CONTROLLER_RESPCD, validateOrgApplicantServiceResponse.getRespCode());
    }

}

