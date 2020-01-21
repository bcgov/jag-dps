package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * This class represents the Ords Dfcms Client custom properties
 *
 * @author alexjoybc@github
 *
 */
@ConfigurationProperties(prefix = "dpsvalidation.service.ords.figcr.client")
public class OrdsFigcrProperties {

    private String basePath;
    private String username;
    private String password;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
