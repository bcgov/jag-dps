package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models;

public class DpsEmailResponse {

    private boolean acknowledge;

    private String message;

    private DpsEmailResponse(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    private DpsEmailResponse(boolean acknowledge, String message) {
        this(acknowledge);
        this.message = message;
    }

    public static DpsEmailResponse Success() {
        return new DpsEmailResponse(true);
    }

    public static DpsEmailResponse Error(String message) {
        return new DpsEmailResponse(false, message);
    }

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public String getMessage() {
        return message;
    }
}
