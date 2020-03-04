package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

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
    List<Item> getDpsInboxEmails();

    /**
     * Returns a collection of valid dps emails in the inbox folder.
     *
     * Valid emails are the one with Attachment only.
     *
     * @return
     */
    List<Item> getDpsInboxJunkEmails();

    /**
     * Moves a message to the ErrorHold folder on the mailbox.
     * @param item - an email message
     */
    void moveToErrorFolder(Item item);


    /**
     * Moves a message to the processing folder on the mailbox.
     * @param item - an email message
     */
    void moveToProcessingFolder(Item item);
}
