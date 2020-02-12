package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.messaging.starter.DpsMessagingProperties;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configures RabbitMq Producer
 *
 *
 * @author alexjoybc@github
 *
 */
@Configuration
public class RabbitMqConfiguration {


    @Bean
    public DpsMessagingProperties dpsMessagingProperties() {
        return new DpsMessagingProperties(Keys.OUTPUT_NOTIFICATION_VALUE, Keys.VIPS_VALUE);
    }

}
