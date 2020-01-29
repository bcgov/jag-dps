package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgDrawDownBalanceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
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
    private OrgServiceImpl sut;

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

        ValidateOrgDrawDownBalanceOrdsResponse successResponse = new ValidateOrgDrawDownBalanceOrdsResponse();
        successResponse.setValidationResult(VALIDATION_RESULT);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        ValidateOrgDrawDownBalanceOrdsResponse errorResponse = new ValidateOrgDrawDownBalanceOrdsResponse();
        errorResponse.setValidationResult(ERROR_VALIDATION_RESULT);
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);

        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq("1"), Mockito.anyString(), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq("2"), Mockito.anyString(), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(orgApiMock.validateOrgDrawDownBalance(Mockito.eq("3"), Mockito.anyString(), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));

        sut = new OrgServiceImpl(orgApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest("1", "a", "b"));

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest("2", "a", "b"));
        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest("3", "a", "b"));
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(API_EXCEPTION, result.getValidationResult());
    }
}