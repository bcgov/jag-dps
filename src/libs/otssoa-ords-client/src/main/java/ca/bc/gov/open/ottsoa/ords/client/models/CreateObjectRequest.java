package ca.bc.gov.open.ottsoa.ords.client.models;

import java.util.Date;
import java.util.UUID;

public class CreateObjectRequest {
    
    public static class Builder {

        private String clientNumber;
        private String contentId;
        private String contentType;
        private Date completionDate;
        private UUID importGuid;
        private String clientName;
        private String actionSystem;
        private String actionUser;
        private String actionMethod;
        private String imageUpload;
        private String caseUpdate;
        private String caseResults;
        private String packageFormatType;

        public Builder withClientNumber(String clientNumber) { this.clientNumber = clientNumber; return this; }
        public Builder withContentId(String contentId) { this.contentId = contentId; return this; }
        public Builder withContentType(String contentType) { this.contentType = contentType; return this; }
        public Builder withCompletionDate(Date completionDate) { this.completionDate = completionDate; return this; }
        public Builder withImportGuid(UUID importGuid) { this.importGuid = importGuid; return this; }
        public Builder withClientName(String clientName) { this.clientName = clientName; return this; }
        public Builder withActionSystem(String actionSystem) { this.actionSystem = actionSystem; return this; }
        public Builder withActionUser(String actionUser) { this.actionUser = actionUser; return this; }
        public Builder withActionMethod(String actionMethod) { this.actionMethod = actionMethod; return this; }
        public Builder withImageUpload(String imageUpload) { this.imageUpload = imageUpload; return this; }
        public Builder withCaseUpdate(String caseUpdate) { this.caseUpdate = caseUpdate; return this; }
        public Builder withCaseResults(String caseResults) { this.caseResults = caseResults; return this; }
        public Builder withPackageFormatType(String packageFormatType) { this.packageFormatType = packageFormatType; return this; }
        
        public CreateObjectRequest build() {
            
            CreateObjectRequest result = new CreateObjectRequest();
            result.clientNumber = this.clientNumber;
            result.contentId = this.contentId;
            result.contentType = this.contentType;
            result.completionDate = this.completionDate;
            result.importGuid = this.importGuid;
            result.clientName = this.clientName;
            result.actionSystem = this.actionSystem;
            result.actionUser = this.actionUser;
            result.actionMethod = this.actionMethod;
            result.imageUpload = this.imageUpload;
            result.caseUpdate = this.caseUpdate;
            result.caseResults = this.caseResults;
            result.packageFormatType = this.packageFormatType;
            return result;
        }
        
    }
    
    
    private String clientNumber;
    private String contentId;
    private String contentType;
    private Date completionDate;
    private UUID importGuid;
    private String clientName;
    private String actionSystem;
    private String actionUser;
    private String actionMethod;
    private String imageUpload;
    private String caseUpdate;
    private String caseResults;
    private String packageFormatType;

    protected CreateObjectRequest() {
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public String getContentId() {
        return contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public UUID getImportGuid() {
        return importGuid;
    }

    public String getClientName() {
        return clientName;
    }

    public String getActionSystem() {
        return actionSystem;
    }

    public String getActionUser() {
        return actionUser;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public String getImageUpload() {
        return imageUpload;
    }

    public String getCaseUpdate() {
        return caseUpdate;
    }

    public String getCaseResults() {
        return caseResults;
    }

    public String getPackageFormatType() {
        return packageFormatType;
    }
}
