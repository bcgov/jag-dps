package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.time.Duration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(DpsMessagingProperties.class)
public class MessageAutoConfiguration {

    private final DpsMessagingProperties dpsMessagingProperties;

    public MessageAutoConfiguration(DpsMessagingProperties dpsMessagingProperties) {
        this.dpsMessagingProperties = dpsMessagingProperties;
    }

    /**
     * Configures the rabbitMq connection factory.
     *
     * @param rabbitProperties
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost(),
                rabbitProperties.getPort());

        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());

        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * Main Exchange
     * @return
     */
    @Bean
    public TopicExchange mainExchange() {
        return new TopicExchange(dpsMessagingProperties.getExchangeName(), true, false);
    }

    /**
     *
     * Configure the default rabbit template
     *
     * @param connectionFactory
     * @param rabbitProperties
     * @param objectMapper
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RabbitTemplate.class)
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "producer")
    public RabbitTemplate mainExchangeRabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(dpsMessagingProperties.getExchangeName());
        rabbitTemplate.setMessageConverter(this.jsonMessageConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
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
     * Main Queue
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public Queue mainQueue() {
        return QueueBuilder
                .durable(dpsMessagingProperties.getMainQueueName())
                .withArgument(Keys.X_DEAD_LETTER_EXCHANGE_ARG, dpsMessagingProperties.getDeadLetterQueueName())
                .build();
    }

    /**
     * Binds the main queue to the main exchange
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public Binding mainQueueBinding(
            @Qualifier("mainQueue") Queue queue,
            @Qualifier("mainExchange")TopicExchange exchange) {
        return bindQueueToExchange(queue, exchange, dpsMessagingProperties.getRoutingKey());
    }

    /**
     * dead letter queue
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public TopicExchange dlqExchange() {
        return new TopicExchange(dpsMessagingProperties.getDeadLetterQueueName(),
                true, false);
    }

    /**
     * the dead letter queue
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    Queue dlqQueue() {
        return QueueBuilder
                .durable(dpsMessagingProperties.getDeadLetterQueueName())
                .withArgument(Keys.X_DEAD_LETTER_EXCHANGE_ARG, dpsMessagingProperties.getExchangeName())
                .withArgument(Keys.X_MESSAGE_TTL_ARG,
                        Duration.ofSeconds(dpsMessagingProperties.getRetryDelay()).toMillis())
                .build();
    }

    /**
     * Binds the dlq to the dlq exchange
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public Binding dlqQueueBinding(
            @Qualifier("dlqQueue") Queue queue,
            @Qualifier("dlqExchange") TopicExchange exchange) {
        return bindQueueToExchange(queue, exchange, dpsMessagingProperties.getRoutingKey());
    }

    /**
     * The parking lot exchange
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public TopicExchange parkingLotExchange() {
        return new TopicExchange(dpsMessagingProperties.getParkingLotQueueName(),
                true, false);
    }

    /**
     * the parking lot queue
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    Queue parkingLotQueue() {
        return QueueBuilder
                .durable(dpsMessagingProperties.getParkingLotQueueName())
                .build();
    }

    /**
     * Binds the parking lot queue to the parking lot exchange
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public Binding parkingLotQueueBinding(
            @Qualifier("parkingLotQueue") Queue queue,
            @Qualifier("parkingLotExchange") TopicExchange exchange) {
        return bindQueueToExchange(queue, exchange, dpsMessagingProperties.getRoutingKey());
    }


    @Bean
    @ConditionalOnMissingBean(RabbitTemplate.class)
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public RabbitTemplate parkingLotExchangeRabbitTemplate(ConnectionFactory connectionFactory, RabbitProperties rabbitProperties, ObjectMapper objectMapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(MessageFormat.format("{0}.{1}.PL", dpsMessagingProperties.getRoutingKey(), dpsMessagingProperties.getExchangeName()));
        rabbitTemplate.setMessageConverter(this.jsonMessageConverter(objectMapper));
        return rabbitTemplate;
    }

    private Binding bindQueueToExchange(Queue queue, TopicExchange exhange, String routingKey) {
        return BindingBuilder.bind(queue).to(exhange)
                .with(routingKey);
    }

    /**
     * Provides as default factory for RabbitListeners
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               @Qualifier("jackson2JsonMessageConverter") Jackson2JsonMessageConverter messageConverter,
                                                                               DpsMessagePostProcessor dpsMessagePostProcessor,
                                                                               DpsMessageErrorHandler dpsMessageErrorHandler) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAfterReceivePostProcessors(dpsMessagePostProcessor);
        factory.setErrorHandler(dpsMessageErrorHandler);
        factory.setMissingQueuesFatal(false);
        return factory;
    }



}
