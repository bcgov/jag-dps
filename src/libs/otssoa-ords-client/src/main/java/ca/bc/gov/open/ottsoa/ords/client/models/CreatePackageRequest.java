package ca.bc.gov.open.ottsoa.ords.client.models;

import java.util.Date;
import java.util.UUID;

public class CreatePackageRequest {

    public static class Builder {

        private int pageCount;
        private int recordCount;
        private String programType;
        private String formatType;
        private String filename;
        private String source;
        private String recipient;
        private Date receivedDate;
        private UUID importGuid;
        private String businessArea;


        public Builder withPageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder withRecordCount(int recordCount) {
            this.recordCount = recordCount;
            return this;
        }

        public Builder withProgramType(String programType) {
            this.programType = programType;
            return this;
        }

        public Builder withFormatType(String formatType) {
            this.formatType = formatType;
            return this;
        }

        public Builder withFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withRecipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder withReceivedDate(Date receivedDate) {
            this.receivedDate = receivedDate;
            return this;
        }

        public Builder withImportGuid(UUID importGuid) {
            this.importGuid = importGuid;
            return this;
        }

        public Builder withBusinessArea(String businessArea) {
            this.businessArea = businessArea;
            return this;
        }

        public CreatePackageRequest build() {

            CreatePackageRequest request = new CreatePackageRequest();
            request.pageCount = pageCount;
            request.recordCount = recordCount;
            request.programType = programType;
            request.formatType = formatType;
            request.filename = filename;
            request.source = source;
            request.recipient = recipient;
            request.receivedDate = receivedDate;
            request.importGuid = importGuid;
            request.businessArea = businessArea;
            return request;
        }

    }

    private int pageCount;
    private int recordCount;
    private String programType;
    private String formatType;
    private String filename;
    private String source;
    private String recipient;
    private Date receivedDate;
    private UUID importGuid;
    private String businessArea;

    protected CreatePackageRequest() {
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public String getProgramType() {
        return programType;
    }

    public String getFormatType() {
        return formatType;
    }

    public String getFilename() {
        return filename;
    }

    public String getSource() {
        return source;
    }

    public String getRecipient() {
        return recipient;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public UUID getImportGuid() {
        return importGuid;
    }

    public String getBusinessArea() {
        return businessArea;
    }
}
