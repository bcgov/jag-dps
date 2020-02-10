package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.messaging;

import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.Keys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
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
                .durable(Keys.CRRP_QUEUE_NAME)
                .build();
    }

    @Bean
    public TopicExchange outputNotificationTopic() {
        return new TopicExchange(Keys.OUTPUT_NOTIFICATION_VALUE, true, false);
    }

    @Bean
    public Binding documentReadyBinding(Queue crrpOutputNotificationQueue, TopicExchange outputNotificationTopic) {
        return BindingBuilder.bind(crrpOutputNotificationQueue).to(outputNotificationTopic)
                .with(Keys.CRRP_VALUE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
