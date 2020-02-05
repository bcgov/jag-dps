package ca.bc.gov.open.pssg.rsbc.crrp.notification.worker.document;

import ca.bc.gov.open.ords.figcr.client.api.DocumentApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsRequestBody;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.crrp.notification.worker.FigaroServiceConstants;
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
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_MESSAGE = "error";
    private static final String ERROR_CODE = "-2";

    @Mock
    private DocumentApi documentApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        DpsDataIntoFigaroOrdsResponse successResponse = new DpsDataIntoFigaroOrdsResponse();
        successResponse.setStatusMessage(STATUS_MESSAGE);
        successResponse.setStatusCode(STATUS_CODE);

        DpsDataIntoFigaroOrdsResponse errorResponse = new DpsDataIntoFigaroOrdsResponse();
        errorResponse.setStatusMessage(ERROR_MESSAGE);
        errorResponse.setStatusCode(ERROR_CODE);

        DpsDataIntoFigaroOrdsRequestBody successRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        successRequestBody.setScheduleType(SCHEDULE_TYPE_SUCCESS);
        Mockito.doReturn(successResponse).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_SUCCESS)));

        DpsDataIntoFigaroOrdsRequestBody failRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        failRequestBody.setScheduleType(SCHEDULE_TYPE_FAIL);
        Mockito.doReturn(errorResponse).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_FAIL)));

        DpsDataIntoFigaroOrdsRequestBody exceptionRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        exceptionRequestBody.setScheduleType(SCHEDULE_TYPE_EXCEPTION);
        Mockito.doThrow(new ApiException(API_EXCEPTION)).when(documentApiMock).dpsDataIntoFigaroPost(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_EXCEPTION)));

        sut = new DocumentServiceImpl(documentApiMock);
    }

    @Test
    public void withValidResponseShouldReturnValidResponse() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody(SCHEDULE_TYPE_SUCCESS, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody(SCHEDULE_TYPE_FAIL, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(-2, result.getRespCode());
        Assertions.assertEquals(ERROR_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withApiExceptionShouldReturnValid() {

        DpsDataIntoFigaroRequestBody request = new DpsDataIntoFigaroRequestBody(SCHEDULE_TYPE_EXCEPTION, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
        DpsDataIntoFigaroResponse result = sut.dpsDataIntoFigaro(request);

        Assertions.assertEquals(FigaroServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
    }
}
