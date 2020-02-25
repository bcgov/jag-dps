package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgDrawDownBalanceOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgPartyOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private OrganizationServiceImpl sut;

    private static final String CASE_SUCCESS = "1";
    private static final String CASE_FAIL = "2";
    private static final String CASE_EXCEPTION = "3";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String VALIDATION_RESULT = "valid";
    private static final String ERROR_VALIDATION_RESULT = "invalid";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private OrgApi orgApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        ValidateOrgDrawDownBalanceOrdsResponse successResponse1 = new ValidateOrgDrawDownBalanceOrdsResponse();
        successResponse1.setValidationResult(VALIDATION_RESULT);
        successResponse1.setStatusMessage(STATUS_MESSAGE);
        successResponse1.setStatusCode(STATUS_CODE);

        ValidateOrgDrawDownBalanceOrdsResponse errorResponse1 = new ValidateOrgDrawDownBalanceOrdsResponse();
        errorResponse1.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse1.setStatusMessage(ERROR_MESSAGE);
        errorResponse1.setStatusCode(ERROR_CODE);

        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq(CASE_SUCCESS), Mockito.anyString(), Mockito.anyString())).thenReturn(successResponse1);
        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq(CASE_FAIL), Mockito.anyString(), Mockito.anyString())).thenReturn(errorResponse1);
        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq(CASE_EXCEPTION), Mockito.anyString(), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));

        ValidateOrgPartyOrdsResponse successResponse2 = new ValidateOrgPartyOrdsResponse();
        successResponse2.setValidationResult(VALIDATION_RESULT);
        successResponse2.setStatusMessage(STATUS_MESSAGE);
        successResponse2.setStatusCode(STATUS_CODE);

        ValidateOrgPartyOrdsResponse errorResponse2 = new ValidateOrgPartyOrdsResponse();
        errorResponse2.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse2.setStatusMessage(ERROR_MESSAGE);
        errorResponse2.setStatusCode(ERROR_CODE);

        Mockito.when(orgApiMock.validateOrgParty(Mockito.eq(CASE_SUCCESS), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(successResponse2);
        Mockito.when(orgApiMock.validateOrgParty(Mockito.eq(CASE_FAIL), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(errorResponse2);
        Mockito.when(orgApiMock.validateOrgParty(Mockito.eq(CASE_EXCEPTION), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));

        sut = new OrganizationServiceImpl(orgApiMock);
    }

    @Test
    public void withValidResponseDdbShouldReturnValidResponse() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest(CASE_SUCCESS, "a", "b"));

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponseDdbShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest(CASE_FAIL, "a", "b"));

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withApiExceptionDdbShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest(CASE_EXCEPTION, "a", "b"));

        Assertions.assertEquals(FigaroOrdsClientConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroOrdsClientConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(API_EXCEPTION, result.getValidationResult());
    }

    @Test
    public void withValidResponsePartyShouldReturnValidResponse() {

        ValidateOrgPartyResponse result = sut.validateOrgParty(new ValidateOrgPartyRequest(CASE_SUCCESS, "a", "b", "c", "d", "e", "f"));

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponsePartyShouldReturnValid() {

        ValidateOrgPartyResponse result = sut.validateOrgParty(new ValidateOrgPartyRequest(CASE_FAIL, "a", "b", "c", "d", "e", "f"));

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withApiExceptionPartyShouldReturnValid() {

        ValidateOrgPartyResponse result = sut.validateOrgParty(new ValidateOrgPartyRequest(CASE_EXCEPTION, "a", "b", "c", "d", "e", "f"));

        Assertions.assertEquals(FigaroOrdsClientConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroOrdsClientConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(API_EXCEPTION, result.getValidationResult());
    }
}