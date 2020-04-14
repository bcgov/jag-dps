package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DpsMessagePostProcessor implements MessagePostProcessor {

    private Logger logger = LoggerFactory.getLogger(DpsMessagePostProcessor.class);

    private final DpsMessagingProperties dpsMessagingProperties;

    public DpsMessagePostProcessor(DpsMessagingProperties dpsMessagingProperties) {
        this.dpsMessagingProperties = dpsMessagingProperties;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        logger.debug("Message Pre Processor");

        if(message.getMessageProperties() == null) return message;

        List<Map<String, ?>> xDeathCollection = message.getMessageProperties().getXDeathHeader();

        if(xDeathCollection == null || xDeathCollection.isEmpty()) return message;

        Map<String, ?> xDeath = xDeathCollection.get(0);

        if(xDeath != null &&
                (Long)xDeath.get("count") > dpsMessagingProperties.getRetryCount()) {

            logger.error("Message has reach retry limit of {} retries", dpsMessagingProperties.getRetryCount());
            throw new ImmediateAcknowledgeAmqpException("Message has reach retry limit.");
        }

        return message;
    }

}
