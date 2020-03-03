package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms;

import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase.CaseSequenceNumberResponse;
import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase.CaseService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsValidationServiceConstants;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author alexjoybc@github
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationControllerTest {

    private static final String CODE_SUCCESS = "1234562";
    private static final String DESC_SUCCESS = "2";
    private static final String CODE_FAIL = "1234561";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String FAIL_MESSAGE = "fail";

    private static final String DRIVER_LICENCE_VALID = "1234567";
    private static final String DRIVER_LICENCE_INVALID = "INVALID";
    private static final String SURNAME_CODE_VALID = "PEL";
    private static final String SURNAME_CODE_INVALID = "INVALID#";

    @Mock
    private CaseService caseServiceMock;

    private ValidationController sut;

    @BeforeAll
    public void SetUp() {

        MockitoAnnotations.initMocks(this);

        CaseSequenceNumberResponse successResponse = CaseSequenceNumberResponse.SuccessResponse(CODE_SUCCESS, DESC_SUCCESS, STATUS_CODE, STATUS_MESSAGE);
        CaseSequenceNumberResponse errorResponse = CaseSequenceNumberResponse.ErrorResponse(FAIL_MESSAGE);

        Mockito.when(caseServiceMock.caseSequenceNumber(Mockito.eq(DRIVER_LICENCE_VALID), Mockito.eq(SURNAME_CODE_VALID))).thenReturn(successResponse);
        Mockito.when(caseServiceMock.caseSequenceNumber(Mockito.eq(CODE_FAIL), Mockito.eq(SURNAME_CODE_VALID))).thenReturn(errorResponse);

        sut = new ValidationController(caseServiceMock);
    }

    @Test
    public void withValidDriverLicenceAndSurCodeShouldReturnSuccess() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmsCase(DRIVER_LICENCE_VALID, SURNAME_CODE_VALID);
        Assert.assertEquals(Integer.parseInt(STATUS_CODE), response.getRespCode());
        Assert.assertEquals(STATUS_MESSAGE, response.getRespMsg());
        Assert.assertEquals(CODE_SUCCESS, response.getCaseSequenceNumber());
        Assert.assertEquals(DESC_SUCCESS, response.getCaseDescription());
    }

    @Test
    public void withInvalidDriverLicenceShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmsCase(DRIVER_LICENCE_INVALID, SURNAME_CODE_VALID);
        Assert.assertEquals(DpsValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
    }

    @Test
    public void withInvalidSurcodeShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmsCase(DRIVER_LICENCE_VALID, SURNAME_CODE_INVALID);
        Assert.assertEquals(DpsValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
    }

    @Test
    public void withErrorShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmsCase(CODE_FAIL, SURNAME_CODE_VALID);
        Assert.assertEquals(DpsValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
    }

}
