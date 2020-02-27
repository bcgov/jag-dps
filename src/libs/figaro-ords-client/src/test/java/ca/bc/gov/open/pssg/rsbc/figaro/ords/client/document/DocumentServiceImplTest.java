package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

import ca.bc.gov.open.ords.figcr.client.api.DocumentApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsRequestBody;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDocumentOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private DocumentServiceImpl sut;

    private static final String SCHEDULE_TYPE_SUCCESS = "1";
    private static final String SCHEDULE_TYPE_FAIL = "2";
    private static final String SCHEDULE_TYPE_EXCEPTION = "3";

    private static final String GUID = "123";
    private static final String SERVER_NAME_SUCCESS = "1";
    private static final String SERVER_NAME_FAIL  = "2";
    private static final String SERVER_NAME_EXCEPTION = "3";

    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private DocumentApi documentApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        //-------------------------------------
        // Setup related to dpsDataIntoFigaroPost
        DpsDataIntoFigaroOrdsResponse successResponse = new DpsDataIntoFigaroOrdsResponse();
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        DpsDataIntoFigaroOrdsResponse errorResponse = new DpsDataIntoFigaroOrdsResponse();
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);

        DpsDataIntoFigaroOrdsRequestBody successRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        successRequestBody.setScheduleType(SCHEDULE_TYPE_SUCCESS);
        Mockito.doReturn(successResponse).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_SUCCESS)));

        DpsDataIntoFigaroOrdsRequestBody errorRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        errorRequestBody.setScheduleType(SCHEDULE_TYPE_FAIL);
        Mockito.doReturn(errorResponse).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_FAIL)));

        DpsDataIntoFigaroOrdsRequestBody exceptionRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        exceptionRequestBody.setScheduleType(SCHEDULE_TYPE_EXCEPTION);
        Mockito.doThrow(new ApiException(API_EXCEPTION)).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_EXCEPTION)));

        //-------------------------------------
        // Setup related to dpsDocumentPost
        DpsDocumentOrdsResponse success2Response = new DpsDocumentOrdsResponse();
        success2Response.setGuid(GUID);
        success2Response.setStatusMessage(STATUS_MESSAGE);
        success2Response.setStatusCode(STATUS_CODE);

        DpsDocumentOrdsResponse error2Response = new DpsDocumentOrdsResponse();
        error2Response.setGuid("");
        error2Response.setStatusMessage(ERROR_MESSAGE);
        error2Response.setStatusCode(ERROR_CODE);

        Mockito.doReturn(success2Response).when(documentApiMock).dpsDocumentPost(ArgumentMatchers.argThat(x -> x.getServerName().equals(SERVER_NAME_SUCCESS)));
        Mockito.doReturn(error2Response).when(documentApiMock).dpsDocumentPost(ArgumentMatchers.argThat(x -> x.getServerName().equals(SERVER_NAME_FAIL)));
        Mockito.doThrow(new ApiException(API_EXCEPTION)).when(documentApiMock).dpsDocumentPost(ArgumentMatchers.argThat(x -> x.getServerName().equals(SERVER_NAME_EXCEPTION)));

        sut = new DocumentServiceImpl(documentApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody.Builder().withScheduleType(SCHEDULE_TYPE_SUCCESS).build();
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody.Builder().withScheduleType(SCHEDULE_TYPE_FAIL).build();
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody.Builder().withScheduleType(SCHEDULE_TYPE_EXCEPTION).build();
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(API_EXCEPTION, result.getRespMsg());
    }

    @Test
    public void withValid2ResponseShouldReturnValidResponse() {

        DpsDocumentRequestBody request = new DpsDocumentRequestBody(SERVER_NAME_SUCCESS, "a");
        DpsDocumentResponse result = sut.dpsDocument(request);

        Assertions.assertEquals(GUID, result.getGuid());
        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withInvalid2ResponseShouldReturnValid() {

        DpsDocumentRequestBody request = new DpsDocumentRequestBody(SERVER_NAME_FAIL, "a");
        DpsDocumentResponse result = sut.dpsDocument(request);

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withApi2ExceptionShouldReturnValid() {

        DpsDocumentRequestBody request = new DpsDocumentRequestBody(SERVER_NAME_EXCEPTION, "a");
        DpsDocumentResponse result = sut.dpsDocument(request);

        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(API_EXCEPTION, result.getRespMsg());
    }
}
