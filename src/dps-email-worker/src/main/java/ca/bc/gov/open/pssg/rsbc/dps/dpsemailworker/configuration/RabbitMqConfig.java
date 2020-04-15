package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class RabbitMqConfig {

}
