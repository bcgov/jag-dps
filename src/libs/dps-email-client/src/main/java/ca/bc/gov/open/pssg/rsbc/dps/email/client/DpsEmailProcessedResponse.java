package ca.bc.gov.open.pssg.rsbc.dps.email.client;

import java.text.MessageFormat;

/**
 *
 * Represents the Dps Email Processed Response
 *
 * @author carolcarpenterjustice
 *
 */
public class DpsEmailProcessedResponse {

    private boolean acknowledge;
    private String message;

    private DpsEmailProcessedResponse(boolean acknowledge, String message) {
        this.acknowledge = acknowledge;
        this.message = message;
    }

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public String getMessage() {
        return message;
    }

    public static DpsEmailProcessedResponse errorResponse(String errorMessage) {
        return new DpsEmailProcessedResponse(false, errorMessage);
    }

    public static DpsEmailProcessedResponse successResponse(boolean acknowledge, String message) {
        return new DpsEmailProcessedResponse(acknowledge, message);
    }

    @Override
    public String toString() {
        return MessageFormat.format("DpsEmailProcessedResponse: acknowledge [{0}], message [{1}]",
                this.acknowledge, this.message);
    }

}
