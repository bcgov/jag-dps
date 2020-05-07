package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class OttsoaHealthCheck implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean isHealthy = false;
    private final OtssoaApi otssoaApi;

    public OttsoaHealthCheck(OtssoaApi otssoaApi) {
        this.otssoaApi = otssoaApi;
        ScheduledExecutorService scheduled =
                Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(() -> {
            isHealthy = check().equals("success");
        }, 5, 10, TimeUnit.MINUTES);
    }

    @Override
    public Health health() {

        if (!isHealthy) {
            isHealthy = check();
        }

        return isHealthy ? Health.up().build() : Health.down().build();
    }

    private Boolean check() {

        try {
            this.otssoaApi.healthGet();
        } catch (ApiException ex) {
            logger.error("Health Service did throw exception: ", ex);
            return false;
        }
        return true;
    }
}
