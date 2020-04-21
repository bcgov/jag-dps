package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
public class DpsMessagePostProcessor implements MessagePostProcessor {

    private Logger logger = LoggerFactory.getLogger(DpsMessagePostProcessor.class);

    private final DpsMessagingProperties dpsMessagingProperties;

    private final RabbitTemplate parkingLotExchangeRabbitTemplate;

    public DpsMessagePostProcessor(DpsMessagingProperties dpsMessagingProperties,
                                  @Qualifier("parkingLotExchangeRabbitTemplate")RabbitTemplate parkingLotExchangeRabbitTemplate) {
        this.dpsMessagingProperties = dpsMessagingProperties;
        this.parkingLotExchangeRabbitTemplate = parkingLotExchangeRabbitTemplate;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        logger.debug("Message Pre Processor");

        if(message.getMessageProperties() == null) return message;

        // We do not want to run this postProcess on message coming from the ".PL" queue
        // as the purpose of this postProcess is to move them there when we've failed to
        // process them too many times.
        if(message.getMessageProperties().getConsumerQueue().endsWith(".PL")) return message;

        List<Map<String, ?>> xDeathCollection = message.getMessageProperties().getXDeathHeader();

        if(xDeathCollection == null || xDeathCollection.isEmpty()) return message;

        Map<String, ?> xDeath = xDeathCollection.get(0);

        if(xDeath != null &&
                (Long)xDeath.get("count") > dpsMessagingProperties.getRetryCount()) {

            logger.error("Message has reach retry limit of {} retries and will be moved to parking lot", dpsMessagingProperties.getRetryCount());

            parkingLotExchangeRabbitTemplate.convertAndSend(dpsMessagingProperties.getRoutingKey(), message);

            throw new ImmediateAcknowledgeAmqpException("message has been put in parking lot");
        }

        return message;
    }

}
