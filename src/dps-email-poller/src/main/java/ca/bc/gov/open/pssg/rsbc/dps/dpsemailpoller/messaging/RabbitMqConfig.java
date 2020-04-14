package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class RabbitMqConfig {

    @Bean
    MessagingService messagingService(RabbitTemplate emailMessageTopTemplate) {
        return new MessagingServiceImpl(emailMessageTopTemplate);
    }

}
