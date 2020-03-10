package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.text.MessageFormat;

public class EmailInfoMessage {

    public String id;
    public String subject;

    @JsonCreator
    public EmailInfoMessage(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public String getSubject() { return subject; }

    @Override
    public String toString() {
        return MessageFormat.format("EmailInfoMessage: id [{0}], subject [{1}]", this.id, this.subject);
    }
}
