package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mailbox.poller")
public class EmailProperties {
    private String cron;
    private Integer emailsPerBatch;
    private String errorFolder;
    private String processingFolder;
    private String processedFolder;
    private String graphExchangeSwitch;
    public boolean isMSGraph() {
        return getGraphExchangeSwitch().equalsIgnoreCase("Graph") ;
    }
    public boolean isMSExchange() {
        return !getGraphExchangeSwitch().equalsIgnoreCase("Graph") ;
    }


    public String getGraphExchangeSwitch() {
        return graphExchangeSwitch;
    }

    public void setGraphExchangeSwitch(String graphExchangeSwitch) {
        this.graphExchangeSwitch = graphExchangeSwitch;
    }
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getEmailsPerBatch() {
        return emailsPerBatch;
    }

    public void setEmailsPerBatch(Integer emailsPerBatch) {
        this.emailsPerBatch = emailsPerBatch;
    }

    public String getErrorFolder() {
        return errorFolder;
    }

    public void setErrorFolder(String errorFolder) {
        this.errorFolder = errorFolder;
    }

    public String getProcessingFolder() {
        return processingFolder;
    }

    public void setProcessingFolder(String processingFolder) {
        this.processingFolder = processingFolder;
    }

    public String getProcessedFolder() {
        return processedFolder;
    }

    public void setProcessedFolder(String processedFolder) {
        this.processedFolder = processedFolder;
    }

}
