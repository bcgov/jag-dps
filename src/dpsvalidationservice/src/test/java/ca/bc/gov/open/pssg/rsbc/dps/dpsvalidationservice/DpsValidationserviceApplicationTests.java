package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

import ca.bc.gov.open.ords.dfcms.client.api.DfcrmsApi;
import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;
import ca.bc.gov.open.ords.dfcms.client.api.model.CaseSequenceNumberResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm.GetValidOpenDFCMCase;
import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.dfcsm.ValidationController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @Test
    void getValidOpenDFCMCase() throws Exception {
        String request = "/getValidOpenDFCMCase/?driversLicense=1234567&surcode=345";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/dpsvalidationservice" + request,
                String.class).contains("getValidOpenDFCMCase"));
    }

}
