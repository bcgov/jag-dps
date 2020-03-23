package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;

import java.util.List;

/**
 * Interface that defines dps email operations.
 *
 * @author alexjoybc@github
 * @author carolcarpenterjustice@github
 */
public interface EmailService {


    /**
     * Returns a collection of valid dps emails in the inbox folder.
     *
     * Valid emails are the one with Attachment only.
     *
     * @return
     */
    List<EmailMessage> getDpsInboxEmails();

    /**
     * Returns a collection of valid dps emails in the inbox folder.
     *
     * Valid emails are the one with Attachment only.
     *
     * @return
     */
    List<EmailMessage> getDpsInboxJunkEmails();

    /**
     * Moves a message to the ErrorHold folder on the mailbox.
     * @param String - an email message
     */
    EmailMessage moveToErrorFolder(String id);


    /**
     * Moves a message to the processing folder on the mailbox.
     * @param String - an email message
     */
    EmailMessage moveToProcessingFolder(String id);

    /**
     * Moves a message to the processing folder on the mailbox.
     * @param String - an email message
     */
    EmailMessage moveToProcessedFolder(String id);
}
