package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kofax")
public class KofaxProperties {

    private String userId;

    private String password;

    private String enableAutoSeparationAndFormid;

    private String relativeImageFilePath;

    private String batchFieldImportId;

    private String batchFieldProgramType;

    private String batchFieldImportDate;

    private String batchFieldFaxReceiveDate;

    private String batchFieldOrigNum;

    private String fileNameDatePattern;

    private String xmlDatePattern;

    private String errorLocation;

    private String errorHoldLocation;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnableAutoSeparationAndFormid() {
        return enableAutoSeparationAndFormid;
    }

    public void setEnableAutoSeparationAndFormid(String enableAutoSeparationAndFormid) {
        this.enableAutoSeparationAndFormid = enableAutoSeparationAndFormid;
    }

    public String getRelativeImageFilePath() {
        return relativeImageFilePath;
    }

    public void setRelativeImageFilePath(String relativeImageFilePath) {
        this.relativeImageFilePath = relativeImageFilePath;
    }

    public String getBatchFieldImportId() {
        return batchFieldImportId;
    }

    public void setBatchFieldImportId(String batchFieldImportId) {
        this.batchFieldImportId = batchFieldImportId;
    }

    public String getBatchFieldProgramType() {
        return batchFieldProgramType;
    }

    public void setBatchFieldProgramType(String batchFieldProgramType) {
        this.batchFieldProgramType = batchFieldProgramType;
    }

    public String getBatchFieldImportDate() {
        return batchFieldImportDate;
    }

    public void setBatchFieldImportDate(String batchFieldImportDate) {
        this.batchFieldImportDate = batchFieldImportDate;
    }

    public String getBatchFieldFaxReceiveDate() {
        return batchFieldFaxReceiveDate;
    }

    public void setBatchFieldFaxReceiveDate(String batchFieldFaxReceiveDate) {
        this.batchFieldFaxReceiveDate = batchFieldFaxReceiveDate;
    }

    public String getBatchFieldOrigNum() {
        return batchFieldOrigNum;
    }

    public void setBatchFieldOrigNum(String batchFieldOrigNum) {
        this.batchFieldOrigNum = batchFieldOrigNum;
    }

    public String getFileNameDatePattern() {
        return fileNameDatePattern;
    }

    public void setFileNameDatePattern(String fileNameDatePattern) {
        this.fileNameDatePattern = fileNameDatePattern;
    }

    public String getXmlDatePattern() {
        return xmlDatePattern;
    }

    public void setXmlDatePattern(String xmlDatePattern) {
        this.xmlDatePattern = xmlDatePattern;
    }

    public String getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
    }

    public String getErrorHoldLocation() {
        return errorHoldLocation;
    }

    public void setErrorHoldLocation(String errorHoldLocation) {
        this.errorHoldLocation = errorHoldLocation;
    }
}
