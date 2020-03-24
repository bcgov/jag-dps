package ca.bc.gov.open.pssg.rsbc.dps.email.client;

import ca.bc.gov.open.dps.email.client.api.DpsEmailProcessingApi;
import ca.bc.gov.open.dps.email.client.api.handler.ApiException;
import ca.bc.gov.open.dps.email.client.api.model.DpsEmailResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsEmailServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private DpsEmailServiceImpl sut;

    private static final Boolean SUCCESS_EMAIL = true;
    private static final Boolean FAIL_EMAIL = false;
    private static final String SUCCESS_ID = "1";
    private static final String FAIL_ID = "2";
    private static final String SUCCESS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "error";
    private static final String ERROR_ID = "3";

    @Mock
    private DpsEmailProcessingApi dpsEmailProcessingApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        DpsEmailResponse successResponse = new DpsEmailResponse();
        successResponse.setAcknowledge(SUCCESS_EMAIL);
        successResponse.setMessage(SUCCESS_MESSAGE);

        DpsEmailResponse errorResponse = new DpsEmailResponse();
        errorResponse.setAcknowledge(FAIL_EMAIL);
        errorResponse.setMessage(FAIL_MESSAGE);

        Mockito.when(dpsEmailProcessingApiMock.processedUsingPOST(Mockito.eq(SUCCESS_ID))).thenReturn(successResponse);
        Mockito.when(dpsEmailProcessingApiMock.processedUsingPOST(Mockito.eq(FAIL_ID))).thenReturn(errorResponse);
        Mockito.when(dpsEmailProcessingApiMock.processedUsingPOST(Mockito.eq(ERROR_ID))).thenThrow(new ApiException(API_EXCEPTION));

        sut = new DpsEmailServiceImpl(dpsEmailProcessingApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        DpsEmailProcessedResponse result = sut.dpsEmailProcessed(SUCCESS_ID);

        Assertions.assertEquals(SUCCESS_EMAIL, result.isAcknowledge());
        Assertions.assertEquals(SUCCESS_MESSAGE, result.getMessage());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        DpsEmailProcessedResponse result = sut.dpsEmailProcessed(FAIL_ID);

        Assertions.assertEquals(FAIL_EMAIL, result.isAcknowledge());
        Assertions.assertEquals(FAIL_MESSAGE, result.getMessage());
    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        DpsEmailProcessedResponse result = sut.dpsEmailProcessed(ERROR_ID);

        Assertions.assertEquals(FAIL_EMAIL, result.isAcknowledge());
        Assertions.assertEquals(API_EXCEPTION, result.getMessage());
    }
}
