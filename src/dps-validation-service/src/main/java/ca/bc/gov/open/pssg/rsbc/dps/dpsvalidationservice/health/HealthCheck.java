package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.health;

import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class HealthCheck implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean isHealthy = false;
    private final HealthService healthService;

    public HealthCheck(HealthService healthService) {
        this.healthService = healthService;
        ScheduledExecutorService scheduled =
                Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(() -> {
            isHealthy = check().equals("success");
        }, 5, 10, TimeUnit.MINUTES);
    }

    @Override
    public Health health() {

        if(!isHealthy) {
            isHealthy = check().equals("success");
        }

        return isHealthy ? Health.up().build() : Health.down().build();
    }

    private String check() {

        try {
            HealthResponse response = healthService.health();
            logger.info("Health Service returned {}", response.getStatus());
            return response.getStatus();

        } catch (ApiException ex) {
            logger.error("Health Service did throw exception: ", ex);
            return "fail";
        }

    }
}
