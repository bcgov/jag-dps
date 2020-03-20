package ca.bc.gov.pssg.rsbc.dps.dpsemailworker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "dps.tenant")
public class TenantProperties {
    private String name;

    public String getName() {  return name;  }

    public void setName(String name) { this.name = name; }

}

