package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dps.registration")
public class RegistrationProperties {

    private boolean enabled;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
