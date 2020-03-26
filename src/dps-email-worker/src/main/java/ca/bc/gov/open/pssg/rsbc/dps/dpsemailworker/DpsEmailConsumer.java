package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DpsEmailConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DpsEmailService dpsEmailService;

    public DpsEmailConsumer(DpsEmailService dpsEmailService) {
        this.dpsEmailService = dpsEmailService;
    }

    @RabbitListener(queues = Keys.EMAIL_QUEUE_NAME)
    public void receiveMessage(DpsMetadata message) {

        logger.info("received new {}", message);

        try {
            logger.debug("attempting to get message meta data [{}]", message);

            logger.info("message meta data successfully received [{}]", message);

            //TODO: when id will be generated for kofax, it will replace TBD.
            dpsEmailService.dpsEmailProcessed(message.getBase64EmailId(), "TBD");

        } catch (Exception e) {
            logger.error("Error in {} while processing message: ", e.getClass().getSimpleName(), e);
        }
    }

}
