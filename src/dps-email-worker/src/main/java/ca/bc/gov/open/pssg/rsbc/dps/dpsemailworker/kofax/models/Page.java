package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Page {

    public static class Builder {

        private String importFileName;
        private String originalFileName;
        private String errorCode;
        private String errorMessage;

        public Builder withimportFileName(String importFileName) {
            this.importFileName = importFileName;
            return this;
        }

        public Builder withoriginalFileName(String originalFileName) {
            this.originalFileName = originalFileName;
            return this;
        }

        public Builder witherrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder witherrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
        
        public Page build() {
            Page page = new Page();

            page.importFileName = this.importFileName;
            page.originalFileName = this.originalFileName;
            page.errorCode = this.errorCode;
            page.errorMessage = this.errorMessage;
            return page;
        }

    }

    @XmlAttribute(name = "ImportFileName")
    private String importFileName;

    @XmlAttribute(name = "OriginalFileName")
    private String originalFileName;

    @XmlAttribute(name = "ErrorCode")
    private String errorCode;

    @XmlAttribute(name = "ErrorMessage")
    private String errorMessage;

    protected Page() { }

    public String getImportFileName() {
        return importFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
