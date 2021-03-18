package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.health;

import ca.bc.gov.open.jag.ordsvipsclient.api.HealthApi;
import ca.bc.gov.open.jag.ordsvipsclient.api.handler.ApiException;
import ca.bc.gov.open.jag.ordsvipsclient.api.model.HealthOrdsResponse;
import ca.bc.gov.open.jagvipsclient.health.HealthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HealthCheckTest {

    private static final int HTTP_STATUS_OK = 200;

    @Mock
    private HealthApi healthApi;

    @Mock
    private HealthService healthServiceMock;

    private HealthCheck sut;

    @BeforeAll
    public void setup() throws ApiException {

        MockitoAnnotations.initMocks(this);

        sut = new HealthCheck(healthServiceMock);
    }

    @Test
    public void withValidResponseShouldReturnValid() {

        Health health = sut.health();

        Assertions.assertEquals(Status.UP,  health.getStatus());
    }

    @Test
    public void withExceptionResponse() throws ApiException {

        Mockito.when(healthApi.health()).thenThrow(ApiException.class);

        Assertions.assertThrows(ApiException.class, () -> {
            HealthOrdsResponse response = healthApi.health();
        });
    }
}
