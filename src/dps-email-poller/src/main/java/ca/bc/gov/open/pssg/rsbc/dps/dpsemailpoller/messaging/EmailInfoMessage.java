package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;

public class EmailInfoMessage {

    public String id;

    @JsonCreator
    public EmailInfoMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
