package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.health;

import ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.DpsEmailPollerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DpsEmailPollerApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DpsEmailPollerApplication dpsEmailPollerApplication;

    @Test
    void contextLoaded() {
        assertThat(dpsEmailPollerApplication).isNotNull();
    }

}
