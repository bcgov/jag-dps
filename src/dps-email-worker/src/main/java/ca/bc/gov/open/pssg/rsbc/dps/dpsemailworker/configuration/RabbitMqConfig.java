package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.messaging.starter.DpsMessagingProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class RabbitMqConfig {
    private final TenantProperties tenantProperties;

    public RabbitMqConfig(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    @Bean
    public DpsMessagingProperties dpsMessagingProperties() {
        return new DpsMessagingProperties(Keys.OUTPUT_NOTIFICATION_VALUE, tenantProperties.getName(), 3, 10);
    }
}
