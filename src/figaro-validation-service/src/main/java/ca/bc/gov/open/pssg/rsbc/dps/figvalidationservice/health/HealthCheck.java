package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.health;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.HealthResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.HealthService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    private static final int HTTP_STATUS_OK = 200;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HealthService healthService;

    public HealthCheck(HealthService healthService) { this.healthService = healthService; }

    @Override
    public Health health() {

        int httpStatusCode = check(); // perform a health check for the ORDS health endpoint
        logger.debug("Health Check returns HTTP Status Code: {}", httpStatusCode);

        if (httpStatusCode != HTTP_STATUS_OK) {
            return Health.down().withDetail("HTTP Status Code", httpStatusCode).build();
        }

        return Health.up().build();
    }

    private int check() {

        try {
            HealthResponse response = healthService.health();
            return HTTP_STATUS_OK;

        } catch (ApiException ex) {
            logger.error("Health Service did throw exception: " + ex.getMessage());
            ex.printStackTrace();

            return ex.getCode();
        }
    }
}
