package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dps.email.service")
public class DpsEmailProperties {

    String basePath;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }


}
