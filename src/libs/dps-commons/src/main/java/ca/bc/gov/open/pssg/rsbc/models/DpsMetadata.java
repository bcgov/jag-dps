package ca.bc.gov.open.pssg.rsbc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class DpsMetadata {

    public static class Builder {

        private String emailId;
        private String applicationID;
        private String direction;
        private String inboundChannelType;
        private String inboundChannelID;
        private String destinationNumber;
        private String originatingNumber;
        private String to;
        private String from;
        private String subject;
        private Date receivedDate;
        private Date sentDate;
        private String body;
        private Integer numberOfPages;
        private String faxJobID;
        private DpsFileInfo fileInfo;

        public Builder withEmailId(String emailId) { this.emailId = emailId; return this; }
        public Builder withApplicationID(String applicationID) { this.applicationID = applicationID; return this; }
        public Builder withDirection(String direction) { this.direction = direction; return this; }
        public Builder withInboundChannelType(String inboundChannelType) { this.inboundChannelType = inboundChannelType; return this; }
        public Builder withInboundChannelID(String inboundChannelID) { this.inboundChannelID = inboundChannelID; return this; }
        public Builder withDestinationNumber(String destinationNumber) { this.destinationNumber = destinationNumber; return this; }
        public Builder withOriginatingNumber(String originatingNumber) { this.originatingNumber = originatingNumber; return this; }
        public Builder withTo(String to) { this.to = to; return this; }
        public Builder withFrom(String from) { this.from = from; return this; }
        public Builder withSubject(String subject) { this.subject = subject; return this; }
        public Builder withRecvdate(Date receivedDate) { this.receivedDate = receivedDate; return this; }
        public Builder withSentdate(Date sentDate) { this.sentDate = sentDate; return this; }
        public Builder withBody(String body) { this.body = body; return this; }
        public Builder withNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; return this; }
        public Builder withFaxJobID(String faxJobID) { this.faxJobID = faxJobID; return this; }
        public Builder withFileInfo(DpsFileInfo fileInfo) { this.fileInfo = fileInfo; return this; }

        public DpsMetadata build() {
            DpsMetadata result = new DpsMetadata();
            result.emailId = emailId;
            result.applicationId = applicationID;
            result.direction = direction;
            result.inboundChannelType = inboundChannelType;
            result.inboundChannelID = inboundChannelID;
            result.destinationNumber = destinationNumber;
            result.originatingNumber = originatingNumber;
            result.to = to;
            result.from = from;
            result.subject = subject;
            result.receivedDate = receivedDate;
            result.sentDate = sentDate;
            result.body = body;
            result.numberOfPages = numberOfPages;
            result.faxJobID = faxJobID;
            result.fileInfo = fileInfo;
            return result;

        }
        
    }

    private UUID transactionId;
    private String emailId;
    private String applicationId;
    private String direction;
    private String inboundChannelType;
    private String inboundChannelID;
    private String destinationNumber;
    private String originatingNumber;
    private String to;
    private String from;
    private String subject;
    private Date receivedDate;
    private Date sentDate;
    private String body;
    private Integer numberOfPages;
    private String faxJobID;
    private DpsFileInfo fileInfo;

    protected DpsMetadata() {
        transactionId = UUID.randomUUID();
    }

    @JsonCreator
    public DpsMetadata(
            @JsonProperty("emailId") String emailId,
            @JsonProperty("applicationID") String applicationID,
            @JsonProperty("direction") String direction,
            @JsonProperty("inboundChannelType") String inboundChannelType,
            @JsonProperty("inboundChannelID") String inboundChannelID,
            @JsonProperty("destinationNumber") String destinationNumber,
            @JsonProperty("originatingNumber") String originatingNumber,
            @JsonProperty("to") String to,
            @JsonProperty("from") String from,
            @JsonProperty("subject") String subject,
            @JsonProperty("receivedDate") Date receivedDate,
            @JsonProperty("sentDate") Date sentDate,
            @JsonProperty("body") String body,
            @JsonProperty("numberOfPages") Integer numberOfPages,
            @JsonProperty("faxJobID") String faxJobID,
            @JsonProperty("fileInfo") DpsFileInfo fileInfo) {
        this.emailId = emailId;
        this.applicationId = applicationID;
        this.direction = direction;
        this.inboundChannelType = inboundChannelType;
        this.inboundChannelID = inboundChannelID;
        this.destinationNumber = destinationNumber;
        this.originatingNumber = originatingNumber;
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.receivedDate = receivedDate;
        this.sentDate = sentDate;
        this.body = body;
        this.numberOfPages = numberOfPages;
        this.faxJobID = faxJobID;
        this.fileInfo = fileInfo;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBase64EmailId() {
        return Base64.getEncoder().encodeToString(emailId.getBytes());
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getDirection() {
        return direction;
    }

    public String getInboundChannelType() {
        return inboundChannelType;
    }

    public String getInboundChannelID() {
        return inboundChannelID;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public String getOriginatingNumber() {
        return originatingNumber;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public String getBody() {
        return body;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public String getFaxJobID() {
        return faxJobID;
    }

    public DpsFileInfo getFileInfo() {
        return fileInfo;
    }

    @Override
    public String toString() {
        return " [" +
                "transactionId=" + transactionId +
                ", emailId='" + emailId + '\'' +
                ", from='" + from + '\'' +
                "] ";
    }
}
