package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessagingServiceImpl implements MessagingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String dpsTenant;

    private final RabbitTemplate emailMessageTopicTemplate;

    public MessagingServiceImpl(
            RabbitTemplate emailMessageTopicTemplate,
            String dpsTenant) {
        this.emailMessageTopicTemplate = emailMessageTopicTemplate;
        this.dpsTenant = dpsTenant;
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

        try {
            EmailInfoMessage emailInfoMessage = new EmailInfoMessage(item.getId().getUniqueId(), item.getSubject());

            logger.debug("Attempting to publish message to emailMessage exchange with key [{}], item: [{}]",
                    dpsTenant, emailInfoMessage);

            emailMessageTopicTemplate.convertAndSend(dpsTenant, emailInfoMessage);

            logger.info("Successfully published message to emailMessage exchange with key [{}], item: [{}]",
                    dpsTenant, emailInfoMessage);

        } catch (ServiceLocalException e) {
            throw new DpsEmailException("Exception while sending a message", e.getCause());
        }
    }
}
