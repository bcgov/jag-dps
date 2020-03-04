package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.EmailService;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmailPoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EmailService emailService;

    public EmailPoller(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "${mailbox.interval}")
    public void pollForEmails() throws Exception {

        logger.info("Poll for emails - {} ", new Date()); // now);

        FindItemsResults<Item> findResults = emailService.getDpsInboxEmails();

        logger.info("          found - " + Integer.toString(findResults.getTotalCount()) + " Messages found in your Inbox");

    }

}
