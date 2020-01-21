package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm.GetValidOpenDFCMCase;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm.ValidationController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nancymz
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DpsValidationserviceApplicationTests {
    // TODO - Add test cases */
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    // TODO: inject mock ordsDfcmsApi
    ValidationController validDFCM = new ValidationController(null);

    @Test
    void getValidOpenDFCMCase() throws Exception {
        String request = "/getValidOpenDFCMCase/?driversLicense=1234567&surcode=345";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dpsvalidationservice" + request,
                String.class).contains("getValidOpenDFCMCase"));
    }

    @Test
    void withValidDriverLicenceAndSurCodeShouldReturnSuccess() {
            GetValidOpenDFCMCase response = validDFCM.getValidOpenDFCMCase("1234567", "PEL");
            Assert.assertEquals(2, response.getResult());
            Assert.assertEquals("ROUTINE - PROFESSIONAL", response.getCaseDesc());
    }

    @Test
    void withInvalidDriverLicenceShouldReturnErrorResponse() {
        GetValidOpenDFCMCase response = validDFCM.getValidOpenDFCMCase("INVALID", "PEL");
        Assert.assertEquals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD,  response.getResult());
    }

    @Test
    void withInvalidSurcodeShouldReturnErrorResponse() {
        GetValidOpenDFCMCase response = validDFCM.getValidOpenDFCMCase("INVALID", "PEL#");
        Assert.assertEquals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE_CD,  response.getResult());
    }
}
