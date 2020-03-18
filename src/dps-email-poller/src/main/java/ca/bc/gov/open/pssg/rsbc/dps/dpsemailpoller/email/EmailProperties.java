package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mailbox.poller")
public class EmailProperties {
    private String cron;
    private Integer emailsPerBatch;
    private String errorFolder;
    private String processingFolder;

    public String getCron() { return cron;  }

    public void setCron(String cron) {  this.cron = cron; }

    public Integer getEmailsPerBatch() { return emailsPerBatch; }

    public void setEmailsPerBatch(Integer emailsPerBatch) { this.emailsPerBatch = emailsPerBatch; }

    public String getErrorFolder() { return errorFolder; }

    public void setErrorFolder(String errorFolder) { this.errorFolder = errorFolder; }

    public String getProcessingFolder() { return processingFolder; }

    public void setProcessingFolder(String processingFolder) { this.processingFolder = processingFolder; }

}
