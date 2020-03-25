package ca.bc.gov.open.pssg.rsbc;

import java.util.Base64;
import java.util.Date;

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
        private Date recvdate;
        private Date sentdate;
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
        public Builder withRecvdate(Date recvdate) { this.recvdate = recvdate; return this; }
        public Builder withSentdate(Date sentdate) { this.sentdate = sentdate; return this; }
        public Builder withBody(String body) { this.body = body; return this; }
        public Builder withNumberOfPages(Integer numberOfPages) { this.numberOfPages = numberOfPages; return this; }
        public Builder withFaxJobID(String faxJobID) { this.faxJobID = faxJobID; return this; }
        public Builder withFileInfo(DpsFileInfo fileInfo) { this.fileInfo = fileInfo; return this; }

        public DpsMetadata build() {
            DpsMetadata result = new DpsMetadata();
            result.emailId = emailId;
            result.applicationID = applicationID;
            result.direction = direction;
            result.inboundChannelType = inboundChannelType;
            result.inboundChannelID = inboundChannelID;
            result.destinationNumber = destinationNumber;
            result.originatingNumber = originatingNumber;
            result.to = to;
            result.from = from;
            result.subject = subject;
            result.receivedDate = recvdate;
            result.sentDate = sentdate;
            result.body = body;
            result.numberOfPages = numberOfPages;
            result.faxJobID = faxJobID;
            result.fileInfo = fileInfo;
            return result;

        }
        
    }

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

    private DpsMetadata() {
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

    public String getApplicationID() {
        return applicationID;
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

}
