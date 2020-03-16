package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessagingServiceImpl implements MessagingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate emailMessageTopicTemplate;

    public MessagingServiceImpl(
            RabbitTemplate emailMessageTopicTemplate) {
        this.emailMessageTopicTemplate = emailMessageTopicTemplate;
    }

    @Override
    public void sendMessage(DpsMetadata dpsMetadata, String tenant) {

        logger.info("Send a message to the queue");

        if (tenant == null) {
            logger.error("Missing dpsTenant");
            throw new DpsEmailException("Exception while sending a message - missing dpsTenant");
        }

        if (dpsMetadata == null) {
            logger.error("Missing dpsMetadata");
            throw new DpsEmailException("Exception while sending a message - missing dpsMetadata");
        }

            logger.debug("Attempting to publish message to emailMessage exchange with key [{}], item: [{}]",
                    tenant, dpsMetadata);

            emailMessageTopicTemplate.convertAndSend(tenant, dpsMetadata);

            logger.info("Successfully published message to emailMessage exchange with key [{}], item: [{}]",
                    tenant, dpsMetadata);

    }
}
