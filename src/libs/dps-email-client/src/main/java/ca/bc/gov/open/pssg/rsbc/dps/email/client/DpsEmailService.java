package ca.bc.gov.open.pssg.rsbc.dps.email.client;

/**
 * Collection of services for dps email client.
 *
 * @author carolcarpenterjustice
 */
public interface DpsEmailService {

     DpsEmailProcessedResponse dpsEmailProcessed(String id, String correlationId);
     DpsEmailProcessedResponse dpsEmailFailed(String id, String correlationId);
}
