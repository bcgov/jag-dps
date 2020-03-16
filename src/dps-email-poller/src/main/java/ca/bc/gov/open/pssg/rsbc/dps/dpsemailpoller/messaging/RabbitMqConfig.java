package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Configures RabbitMq Producer
 *
 *
 * @author alexjoybc@github
 *
 */
@Configuration
public class RabbitMqConfig {

    @Value("${dps.tenant}")
    private String dpsTenant;

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

    /**
     * Configures a new durable email message topic. this topic is persistent.
     * @return
     */
    @Bean
    public TopicExchange emailMessageTopic() {
        return new TopicExchange(Keys.EMAIL_MESSAGE_VALUE, true, false);
    }


    /**
     * Configures jackson mapper to map object to json.
     * @param builder
     * @return
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }


    /**
     * Configures the rabbitmq json message converter with jackson
     * @param objectMapper
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     *
     * Configure the email message template to push documents to the exchange.
     *
     * @param connectionFactory
     * @param rabbitProperties
     * @param objectMapper
     * @return
     */
    @Bean
    public RabbitTemplate emailMessageTopicTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(this.emailMessageTopic().getName());
        rabbitTemplate.setMessageConverter(this.jsonMessageConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    MessagingService messagingService(RabbitTemplate emailMessageTopTemplate) {
        return new MessagingServiceImpl(emailMessageTopTemplate);
    }
}
