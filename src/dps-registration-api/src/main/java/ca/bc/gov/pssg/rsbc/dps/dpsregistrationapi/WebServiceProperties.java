package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dps.ws")
public class WebServiceProperties {

    private boolean loggingEnabled;

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }
}
