package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailPoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmailService emailService;
    private final DpsMetadataMapper dpsMetadataMapper;
    private final MessagingService messagingService;
    private final String tenant;

    public EmailPoller(
            EmailService emailService,
            DpsMetadataMapper dpsMetadataMapper,
            MessagingService messagingService,
            @Value("${dps.tenant}") String tenant) {
        this.emailService = emailService;
        this.dpsMetadataMapper = dpsMetadataMapper;
        this.messagingService = messagingService;
        this.tenant = tenant;
    }

    @Scheduled(cron = "${mailbox.poller.cron}")
    public void pollForEmails() {

        logger.debug("perform poll for emails");

        try {

            List<EmailMessage> dpsEmails = emailService.getDpsInboxEmails();
            logger.info("successfully retrieved {} emails", dpsEmails.size());

            dpsEmails.forEach(item -> {

                List<FileAttachment> fileAttachments = emailService.getFileAttachments(item);

                DpsMetadata metadata = dpsMetadataMapper.map(item, this.tenant);

                try {

                    EmailMessage processedItem = emailService.moveToProcessingFolder(item.getId().getUniqueId());
                    metadata.setEmailId(processedItem.getId().getUniqueId());
                    logger.info("successfully moved message to processing folder");

                    messagingService.sendMessage(metadata, this.tenant);
                    logger.info("successfully send message to processing queue");

                } catch (ServiceLocalException e) {
                    logger.error("exception while processing dps emails", e);
                    return;
                }

            });

        } catch (DpsEmailException e) {

            logger.error("exception while processing dps emails", e);
        }
    }

    /**
     * This Job remove junk email from the inbox and move them to the error folder.
     */
    @Scheduled(cron = "${mailbox.poller.cron}")
    public void junkRemoval() {

        logger.debug("perform poll for junk emails");

        try {
            List<EmailMessage> junkEmails = emailService.getDpsInboxJunkEmails();
            logger.info("successfully retrieved {} junk emails", junkEmails.size());

            junkEmails.forEach(item -> {

                try {
                    emailService.moveToErrorFolder(item.getId().getUniqueId());
                } catch (ServiceLocalException e) {
                    return;
                }

                logger.info("successfully moved message to errorHold folder");
            });

        } catch (DpsEmailException e) {
            logger.error("exception while cleaning junk emails", e);
        }
    }

}
