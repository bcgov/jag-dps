package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.time.Duration;

@Configuration
@ComponentScan
public class AutoConfiguration {

    private static String WAIT_FORMAT = "{0}_WAIT";

    private final DpsMessagingProperties dpsMessagingProperties;

    public AutoConfiguration(DpsMessagingProperties dpsMessagingProperties) {
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
     * The main exchange
     *
     * @return
     */
    @Bean
    public TopicExchange mainExchange() {
        return new TopicExchange(dpsMessagingProperties.getExchangeName(), true, false);
    }

    /**
     * The main queue to listen to
     *
     * @return
     */
    @Bean
    public Queue mainQueue() {
        return QueueBuilder
                .durable(dpsMessagingProperties.getQueueName())
                .withArgument(Keys.X_DEAD_LETTER_EXCHANGE_ARG, MessageFormat.format(WAIT_FORMAT, dpsMessagingProperties.getExchangeName()))
                .build();
    }

    /**
     * the mainqueue binding
     *
     * @param mainQueue
     * @param exchangeTopic
     * @return
     */
    @Bean
    public Binding mainQueueBinding(
            @Qualifier("mainQueue") Queue mainQueue,
            @Qualifier("mainExchange")TopicExchange exchangeTopic) {
        return BindingBuilder.bind(mainQueue).to(exchangeTopic)
                .with(dpsMessagingProperties.getRoutingKey());
    }

    /**
     * the wait exchange for retries
     *
     * @return
     */
    @Bean
    public TopicExchange waitExchange() {
        return new TopicExchange(MessageFormat.format(WAIT_FORMAT, dpsMessagingProperties.getExchangeName()),
                true, false);
    }

    /**
     * the wait queue
     *
     * @return
     */
    @Bean
    Queue waitQueue() {
        return QueueBuilder
                .durable(MessageFormat.format(WAIT_FORMAT, dpsMessagingProperties.getQueueName()))
                .withArgument(Keys.X_DEAD_LETTER_EXCHANGE_ARG, dpsMessagingProperties.getExchangeName())
                .withArgument(Keys.X_MESSAGE_TTL_ARG,
                        Duration.ofSeconds(dpsMessagingProperties.getRetryDelay()).toMillis())
                .build();
    }

    @Bean
    public Binding waitQueueBinding(
            @Qualifier("waitQueue") Queue waitQueue,
            @Qualifier("waitExchange") TopicExchange exchangeTopic) {
        return BindingBuilder.bind(waitQueue).to(exchangeTopic)
                .with(dpsMessagingProperties.getRoutingKey());
    }


    /**
     * Provides as default factory for RabbitListeners
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
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

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
