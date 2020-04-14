package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
@ConditionalOnProperty(value = "dps.messaging.type", havingValue = "consumer")
public class DpsMessageErrorHandler implements ErrorHandler  {

        private Logger logger = LoggerFactory.getLogger(DpsMessageErrorHandler.class);

        @Override
        public void handleError(Throwable t) {

            logger.debug("Handling error thrown by rabbitmq listener.");

            if(!(t instanceof ListenerExecutionFailedException)) {
                logger.error("Error not instance of ListenerExecutionFailedException, the message will be acknowledged and discard from the queue.");
                MDC.clear();
                throw new ImmediateAcknowledgeAmqpException(t);
            }

            logger.error("error while processing message.", t.getMessage());
            MDC.clear();
            throw new AmqpRejectAndDontRequeueException(t);

        }

    }
