package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms;

import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.DfcmsOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase.CaseSequenceNumberResponse;
import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase.CaseService;
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

        CaseSequenceNumberResponse successResponse = CaseSequenceNumberResponse.successResponse(CODE_SUCCESS, DESC_SUCCESS);
        CaseSequenceNumberResponse errorResponse = CaseSequenceNumberResponse.errorResponse();

        Mockito.when(caseServiceMock.caseSequenceNumber(Mockito.eq(DRIVER_LICENCE_VALID), Mockito.eq(SURNAME_CODE_VALID))).thenReturn(successResponse);
        Mockito.when(caseServiceMock.caseSequenceNumber(Mockito.eq(CODE_FAIL), Mockito.eq(SURNAME_CODE_VALID))).thenReturn(errorResponse);

        sut = new ValidationController(caseServiceMock);
    }

    @Test
    public void withValidDriverLicenceAndSurCodeShouldReturnSuccess() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmCase(DRIVER_LICENCE_VALID, SURNAME_CODE_VALID);
        Assert.assertEquals(CODE_SUCCESS, response.getCaseSequenceNumber());
        Assert.assertEquals(DESC_SUCCESS, response.getCaseDescription());
    }

    @Test
    public void withInvalidDriverLicenceShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmCase(DRIVER_LICENCE_INVALID, SURNAME_CODE_VALID);
        Assert.assertEquals(DfcmsOrdsClientConstants.SERVICE_FAILURE_CD, response.getCaseSequenceNumber());
    }

    @Test
    public void withInvalidSurcodeShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmCase(DRIVER_LICENCE_VALID, SURNAME_CODE_INVALID);
        Assert.assertEquals(DfcmsOrdsClientConstants.SERVICE_FAILURE_CD, response.getCaseSequenceNumber());
    }

    @Test
    public void withErrorShouldReturnErrorResponse() {
        CaseSequenceNumberResponse response = sut.getValidOpenDfcmCase(CODE_FAIL, SURNAME_CODE_VALID);
        Assert.assertEquals(DfcmsOrdsClientConstants.SERVICE_FAILURE_CD, response.getCaseSequenceNumber());
    }

}
