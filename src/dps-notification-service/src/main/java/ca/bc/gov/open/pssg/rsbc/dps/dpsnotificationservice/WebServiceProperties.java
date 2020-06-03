package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dps.ws")
public class WebServiceProperties {

    private boolean loggingEnabled;

    private String customNamespace;

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public String getCustomNamespace() {
        return customNamespace;
    }

    public void setCustomNamespace(String customNamespace) {
        this.customNamespace = customNamespace;
    }
}
