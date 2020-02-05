package ca.bc.gov.open.pssg.rsbc.crrp.notification.worker.document;

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
public class DocumentControllerTest {

    private static final String SCHEDULE_TYPE_SUCCESS = "aaa";
    private static final String SCHEDULE_TYPE_FAIL = "bbb";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String ERROR_VALIDATION_RESULT = "invalid";

    @Mock
    private DocumentService documentServiceMock;

    private DocumentController sut;

    @BeforeAll
    public void setup() {

        MockitoAnnotations.initMocks(this);

        DpsDataIntoFigaroResponse successResponse = DpsDataIntoFigaroResponse.SuccessResponse(STATUS_CODE, STATUS_MESSAGE);

        DpsDataIntoFigaroResponse errorResponse = DpsDataIntoFigaroResponse.ErrorResponse(ERROR_VALIDATION_RESULT);

        Mockito.doReturn(successResponse).when(documentServiceMock).dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_SUCCESS)));
        Mockito.doReturn(errorResponse).when(documentServiceMock).dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(SCHEDULE_TYPE_FAIL)));

        sut = new DocumentController(documentServiceMock);
    }

    @Test
    public void withValidResponseShouldReturnValid() {

        DpsDataIntoFigaroResponse result = sut.DpsDataIntoFigaro(SCHEDULE_TYPE_SUCCESS, "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e");

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        DpsDataIntoFigaroResponse result = sut.DpsDataIntoFigaro(SCHEDULE_TYPE_FAIL, "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e", "a", "b", "c", "d", "e");

        Assertions.assertEquals(FigaroServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
    }
}
