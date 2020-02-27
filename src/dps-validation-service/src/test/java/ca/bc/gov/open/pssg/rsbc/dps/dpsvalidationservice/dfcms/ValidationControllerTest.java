package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms;

import ca.bc.gov.open.ords.dfcms.client.api.DfcmsApi;
import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;
import ca.bc.gov.open.ords.dfcms.client.api.model.CaseSequenceNumberResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsValidationServiceConstants;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms.GetValidOpenDFCMCase;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcms.ValidationController;
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


    public static final String EXPECTED_STATUS = "2";
    public static final String EXPECTED_DESCRIPTION = "ROUTINE - PROFESSIONAL";
    @Mock
    public DfcmsApi dfcmsApiMock;

    private ValidationController sut;

    @BeforeAll
    public void SetUp() {

        CaseSequenceNumberResponse caseSequenceNumberResponse = new CaseSequenceNumberResponse();
        caseSequenceNumberResponse.setCaseDescription(EXPECTED_DESCRIPTION);
        caseSequenceNumberResponse.setCaseSequenceNumber(EXPECTED_STATUS);

        MockitoAnnotations.initMocks(this);
        try {
            Mockito.when(dfcmsApiMock.caseSequenceNumberGet("1234567", "PEL"))
                    .thenReturn(caseSequenceNumberResponse);

            // emulating non 200 response
            Mockito.when(dfcmsApiMock.caseSequenceNumberGet("1234568", "EXP")).thenThrow(ApiException.class);

        } catch (ApiException e) {
            e.printStackTrace();
        }

        sut = new ValidationController(dfcmsApiMock);

    }

    @Test
    public void withValidDriverLicenceAndSurCodeShouldReturnSuccess() {
        GetValidOpenDFCMCase response = sut.getValidOpenDFCMCase("1234567", "PEL");
        Assert.assertEquals(EXPECTED_STATUS, response.getResult());
        Assert.assertEquals(EXPECTED_DESCRIPTION, response.getCaseDesc());
    }

    @Test
    public void withInvalidDriverLicenceShouldReturnErrorResponse() {
        GetValidOpenDFCMCase response = sut.getValidOpenDFCMCase("INVALID", "PEL");
        Assert.assertEquals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD, response.getResult());
    }

    @Test
    public void withInvalidSurcodeShouldReturnErrorResponse() {
        GetValidOpenDFCMCase response = sut.getValidOpenDFCMCase("INVALID", "PEL#");
        Assert.assertEquals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD, response.getResult());
    }

    @Test
    public void withClientThrowingExceptionShouldReturnErrorResponse() {
        GetValidOpenDFCMCase response = sut.getValidOpenDFCMCase("1234568", "EXP");
        Assert.assertEquals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD, response.getResult());
    }

}
