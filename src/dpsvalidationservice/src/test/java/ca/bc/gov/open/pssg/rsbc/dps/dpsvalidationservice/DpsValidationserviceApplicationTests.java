package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nancymz
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DpsValidationserviceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getValidOpenDFCMCase() throws Exception {
        String request = "/getValidOpenDFCMCase/?driversLicense=1234567&surcode=345";
        assertThat(this.restTemplate.getForObject("http://localhost:8082" + "/dpsvalidationservice" + request,
                String.class)
        ).contains("GetValidOpenDFCMCase");
    }
}
