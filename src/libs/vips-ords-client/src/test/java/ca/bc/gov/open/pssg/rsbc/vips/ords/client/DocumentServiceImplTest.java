package ca.bc.gov.open.pssg.rsbc.vips.ords.client;

import ca.bc.gov.open.ords.vips.client.api.DocumentApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;
import ca.bc.gov.open.ords.vips.client.api.model.VipsDocumentOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.document.DocumentServiceImpl;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.document.VipsDocumentResponse;
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

        Assertions.assertEquals(VipsOrdsClientConstants.FIGARO_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(VipsOrdsClientConstants.FIGARO_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
    }
}
