package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {


    private final DpsMessagingProperties dpsMessagingProperties;

    public AutoConfiguration(DpsMessagingProperties dpsMessagingProperties) {
        this.dpsMessagingProperties = dpsMessagingProperties;
    }

    /**
     * Configures the rabbitMq connection factory.
     * @param rabbitProperties
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {


        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost(), rabbitProperties.getPort());

        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());

        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue crrpOutputNotificationQueue() {
        return QueueBuilder
                .durable(dpsMessagingProperties.getQueueName())
                .build();
    }

    @Bean
    public TopicExchange outputNotificationTopic() {
        return new TopicExchange(dpsMessagingProperties.getExchangeName(), true, false);
    }

    @Bean
    public Binding documentReadyBinding(Queue crrpOutputNotificationQueue, TopicExchange outputNotificationTopic) {
        return BindingBuilder.bind(crrpOutputNotificationQueue).to(outputNotificationTopic)
                .with(dpsMessagingProperties.getRoutingKey());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
