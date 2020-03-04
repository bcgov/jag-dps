package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.EmailService;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailPoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

    public EmailPoller(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "${mailbox.interval}")
    public void pollForEmails() throws Exception {

        logger.info("Poll for emails");

        List<Item> findResults = emailService.getDpsInboxEmails();

        logger.info("          found - " + Integer.toString(findResults.size()) + " Messages found in your Inbox");

    }


    /**
     * This Job remove junk email from the inbox and move them to the error folder.
     */
    @Scheduled(cron = "${mailbox.interval}")
    public void junkRemoval() {


        logger.info("starting polling junk emails");

        try {
            List<Item> junkEmails = emailService.getDpsInboxJunkEmails();
            logger.info("successfully retrieved {} junk emails", junkEmails.size());

            junkEmails.forEach(item -> emailService.moveToErrorFolder(item));
            logger.info("successfully moved {} to errorHold folder", junkEmails.size());
        } catch (DpsEmailException e) {
            logger.error("exception while cleaning junk emails", e);
        }

    }

}
