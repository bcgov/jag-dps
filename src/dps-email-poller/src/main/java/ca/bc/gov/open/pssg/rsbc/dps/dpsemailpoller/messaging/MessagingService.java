package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;


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
    void sendMessage(DpsMetadata item, String tenant);

}
