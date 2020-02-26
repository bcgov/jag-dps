package ca.bc.gov.open.pssg.rsbc.vips.ords.client.document;

import ca.bc.gov.open.ords.vips.client.api.DocumentApi;
import ca.bc.gov.open.ords.vips.client.api.HealthApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;
import ca.bc.gov.open.ords.vips.client.api.model.HealthOrdsResponse;
import ca.bc.gov.open.ords.vips.client.api.model.VipsDocumentOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.VipsOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.health.HealthResponse;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.health.HealthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private DocumentServiceImpl sut;

    private static final String DOCUMENT_ID = "123";
    private static final String TYPE_CODE_SUCCESS = "1";
    private static final String TYPE_CODE_FAIL = "2";
    private static final String TYPE_CODE_EXCEPTION = "3";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private DocumentApi documentApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        VipsDocumentOrdsResponse successResponse = new VipsDocumentOrdsResponse();
        successResponse.setDocumentId(DOCUMENT_ID);
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        VipsDocumentOrdsResponse errorResponse = new VipsDocumentOrdsResponse();
        errorResponse.setDocumentId("");
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);

        Mockito.when(documentApiMock.vipsDocumentPost(Mockito.eq(TYPE_CODE_SUCCESS), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(successResponse);
        Mockito.when(documentApiMock.vipsDocumentPost(Mockito.eq(TYPE_CODE_FAIL), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(errorResponse);
        Mockito.when(documentApiMock.vipsDocumentPost(Mockito.eq(TYPE_CODE_EXCEPTION), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenThrow(new ApiException(API_EXCEPTION));

        sut = new DocumentServiceImpl(documentApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        VipsDocumentResponse result = sut.vipsDocument(TYPE_CODE_SUCCESS, "a", "b", "c", "d", null);

        Assertions.assertEquals(DOCUMENT_ID, result.getDocumentId());
        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        VipsDocumentResponse result = sut.vipsDocument(TYPE_CODE_FAIL, "a", "b", "c", "d", null);

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        VipsDocumentResponse result = sut.vipsDocument(TYPE_CODE_EXCEPTION, "a", "b", "c", "d", null);

        Assertions.assertEquals(VipsOrdsClientConstants.SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(API_EXCEPTION, result.getRespMsg());
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public static class HealthServiceImplTest {

        public static final String API_EXCEPTION = "api exception";
        private HealthServiceImpl sut;

        private static final String APP_ID = "FIGCRP";
        private static final String METHOD = "health_check";
        private static final String STATUS = "success";
        private static final String HOST = "devdb";
        private static final String INSTANCE = "deva";

        @Mock
        private HealthApi healthApiMock;

        @BeforeAll
        public void setup() throws ApiException {
            MockitoAnnotations.initMocks(this);

            sut = new HealthServiceImpl(healthApiMock);
        }

        @Test
        public void withHealthReturnValidResponse() throws ApiException {

            HealthOrdsResponse successResponse = new HealthOrdsResponse();
            successResponse.setAppid(APP_ID);
            successResponse.setMethod(METHOD);
            successResponse.setStatus(STATUS);
            successResponse.setHost(HOST);
            successResponse.setInstance(INSTANCE);

            Mockito.when(healthApiMock.health()).thenReturn(successResponse);

            HealthResponse result = sut.health();

            Assertions.assertEquals(APP_ID, result.getAppid());
            Assertions.assertEquals(METHOD, result.getMethod());
            Assertions.assertEquals(STATUS, result.getStatus());
            Assertions.assertEquals(HOST, result.getHost());
            Assertions.assertEquals(INSTANCE, result.getInstance());
        }
    }
}
