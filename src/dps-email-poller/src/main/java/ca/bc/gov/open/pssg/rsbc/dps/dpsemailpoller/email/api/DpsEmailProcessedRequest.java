package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.api;

public class DpsEmailProcessedRequest {

    public String correlationId;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
