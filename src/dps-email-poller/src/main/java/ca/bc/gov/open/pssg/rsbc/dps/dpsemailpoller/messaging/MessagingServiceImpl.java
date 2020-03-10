package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class MessagingServiceImpl implements MessagingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${dps.tenant}")
    private String dpsTenant;

    private final RabbitTemplate emailMessageTopicTemplate;

    public MessagingServiceImpl(@Qualifier("emailMessageTopicTemplate") RabbitTemplate emailMessageTopicTemplate) {
        this.emailMessageTopicTemplate = emailMessageTopicTemplate;
    }

    @Override
    public void sendMessage(Item item) {

        logger.info("Send a message to the queue");

        if (dpsTenant == null) {
            logger.error("Missing dpsTenant");
            throw new DpsEmailException("Exception while sending a message - missing dpsTenant");
        }

        if (item == null) {
            logger.error("Missing item");
            throw new DpsEmailException("Exception while sending a message - missing item");
        }

        logger.info("Attempting to publish message to emailMessage exchange with key [{}], item: [{}]", dpsTenant, item);
        emailMessageTopicTemplate.convertAndSend(dpsTenant, item);
        logger.info("Successfully published message to emailMessage exchange with key [{}], item: [{}]", dpsTenant, item);
    }
}
