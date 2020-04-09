package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase;

import ca.bc.gov.open.ords.dfcms.client.api.DfcmsApi;
import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;
import ca.bc.gov.open.ords.dfcms.client.api.model.CaseSequenceNumberOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.DfcmsOrdsClientConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CaseServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private CaseServiceImpl sut;

    private static final String SUCCESS_CASE_SEQ_NO = "123";
    private static final String CASE_DESC = "456";
    private static final String TYPE_CODE_SUCCESS = "1";
    private static final String TYPE_CODE_FAIL = "2";
    private static final String TYPE_CODE_EXCEPTION = "3";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private DfcmsApi dfcmsApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        CaseSequenceNumberOrdsResponse successResponse = new CaseSequenceNumberOrdsResponse();
        successResponse.setCaseSequenceNumber(SUCCESS_CASE_SEQ_NO);
        successResponse.setCaseDescription(CASE_DESC);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        CaseSequenceNumberOrdsResponse errorResponse = new CaseSequenceNumberOrdsResponse();
        errorResponse.setCaseSequenceNumber("0");
        errorResponse.setCaseDescription("0");
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);

        Mockito.when(dfcmsApiMock.caseSequenceNumberGet(Mockito.eq(TYPE_CODE_SUCCESS), Mockito.anyString())).thenReturn(successResponse);
        Mockito.when(dfcmsApiMock.caseSequenceNumberGet(Mockito.eq(TYPE_CODE_FAIL), Mockito.anyString())).thenReturn(errorResponse);
        Mockito.when(dfcmsApiMock.caseSequenceNumberGet(Mockito.eq(TYPE_CODE_EXCEPTION), Mockito.anyString())).thenThrow(new ApiException(API_EXCEPTION));

        sut = new CaseServiceImpl(dfcmsApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        CaseSequenceNumberResponse result = sut.caseSequenceNumber(TYPE_CODE_SUCCESS, "a");

        Assertions.assertEquals(SUCCESS_CASE_SEQ_NO, result.getCaseSequenceNumber());
        Assertions.assertEquals(CASE_DESC, result.getCaseDescription());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        CaseSequenceNumberResponse result = sut.caseSequenceNumber(TYPE_CODE_FAIL, "a");

        Assertions.assertEquals("0", result.getCaseSequenceNumber());

    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        CaseSequenceNumberResponse result = sut.caseSequenceNumber(TYPE_CODE_EXCEPTION, "a");

        Assertions.assertEquals(DfcmsOrdsClientConstants.SERVICE_FAILURE_CD, result.getCaseSequenceNumber());
    }
}
