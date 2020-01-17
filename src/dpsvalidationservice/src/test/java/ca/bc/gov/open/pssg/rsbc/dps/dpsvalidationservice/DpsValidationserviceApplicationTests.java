package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

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

    GetValidOpenDFCMCase validDFCM = new GetValidOpenDFCMCase();

    @Test
    void getValidOpenDFCMCase() throws Exception {
        String request = "/getValidOpenDFCMCase/?driversLicense=1234567&surcode=345";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dpsvalidationservice" + request,
                String.class)
        ).contains("GetValidOpenDFCMCase");

    }

    @Test
    void getValidOpenDFCMCaseTestParams() {
        String response = validDFCM.getValidOpenDFCMCase("123457", "PEL");
        assertThat(response.contains("<caseDec>"));
    }

    /* Test the input params to match
     *      driversLicense to regex "[0-9]{7}"
     *      surcode to   "^[a-zA-Z&-.]{0,3}"
     * */
    @Test
    void getValidOpenDFCMCaseTestWrongParams() {
        String response = validDFCM.getValidOpenDFCMCase("1234578", "PEL&");
        assertThat(response.equals(DpsValidationServiceConstants.VALIDOPEN_DFCMCASE_ERR_RESPONSE));
    }
}
