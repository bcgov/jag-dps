package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import microsoft.exchange.webservices.data.core.service.item.Item;


/**
 * Interface that defines dps messaging service operations.
 *
 * @author alexjoybc@github
 * @author carolcarpenterjustice@github
 */
public interface MessagingService {


    /**
     * Sends a message to the appropriate queue.
     * @param item
     */
    void sendMessage(Item item);

}
