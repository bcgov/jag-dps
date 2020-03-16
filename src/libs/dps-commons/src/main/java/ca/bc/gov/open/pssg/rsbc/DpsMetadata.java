package ca.bc.gov.open.pssg.rsbc;

import java.util.Date;

public class DpsMetadata {

    public static class Builder {

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
        private String attachmentName;
        private String attachmentContentType;

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
        public Builder withAttachmentName(String attachmentName) { this.attachmentName = attachmentName; return this; }
        public Builder withAttachmentContentType(String attachmentContentType) { this.attachmentContentType = attachmentContentType; return this; }

        public DpsMetadata build() {
            DpsMetadata result = new DpsMetadata();

            result.applicationID = applicationID;
            result.direction = direction;
            result.inboundChannelType = inboundChannelType;
            result.inboundChannelID = inboundChannelID;
            result.destinationNumber = destinationNumber;
            result.originatingNumber = originatingNumber;
            result.to = to;
            result.from = from;
            result.subject = subject;
            result.recvdate = recvdate;
            result.sentdate = sentdate;
            result.body = body;
            result.numberOfPages = numberOfPages;
            result.faxJobID = faxJobID;
            result.attachmentName = attachmentName;
            result.attachmentContentType = attachmentContentType;
            return result;

        }
        
    }

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
    private String attachmentName;
    private String attachmentContentType;

    private DpsMetadata() {
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

    public Date getRecvdate() {
        return recvdate;
    }

    public Date getSentdate() {
        return sentdate;
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

    public String getAttachmentName() {
        return attachmentName;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

}
