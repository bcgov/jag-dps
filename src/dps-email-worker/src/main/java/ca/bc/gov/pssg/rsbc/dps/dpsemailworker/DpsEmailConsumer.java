package ca.bc.gov.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;

import ca.bc.gov.pssg.rsbc.dps.dpsemailworker.services.ProcessEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;

@Component
public class DpsEmailConsumer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StorageService storageService;
    private final ProcessEmailService processEmailService;

    public DpsEmailConsumer(StorageService storageService,
                            ProcessEmailService processEmailService) {
        this.storageService = storageService;
        this.processEmailService = processEmailService;
    }

    @RabbitListener(queues = Keys.EMAIL_QUEUE_NAME)
    public void receiveMessage(DpsMetadata message) {

        logger.info("received new {}", message);

        try {
            logger.debug("attempting to get message meta data [{}]", message);
            DpsFileInfo dpsFileInfo = message.fileInfo;
            String content = storageService.get(dpsFileInfo.fileId);

            logger.info("message attachment content retrieved [{}]", dpsFileInfo.fileId);
            //TODO: What needs to be done here


            //Let poller know we are good.
            processEmailService.sendProccessedMessage(message);
        } catch (Exception e) {
            logger.error("Error in {} while processing message: ", e.getClass().getSimpleName(), e);
        }
    }

}
