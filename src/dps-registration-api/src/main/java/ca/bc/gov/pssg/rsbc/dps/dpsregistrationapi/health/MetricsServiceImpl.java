package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health;

import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;

public class MetricsServiceImpl implements MetricsService {

    private final HealthEndpoint healthEndpoint;

    public MetricsServiceImpl(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @Override
    public HealthComponent health() {
        return healthEndpoint.health();
    }
}
