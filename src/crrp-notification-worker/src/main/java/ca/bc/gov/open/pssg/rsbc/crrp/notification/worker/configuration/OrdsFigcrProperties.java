package ca.bc.gov.open.pssg.rsbc.crrp.notification.worker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * This class represents the Figaro Validation custom properties
 *
 * @author shaunmillargov
 *
 */
@ConfigurationProperties(prefix = "crrp-notification-worker.service.ords.figcr.client")
public class OrdsFigcrProperties {

    private String basePath;
    private String username;
    private String password;

    public OrdsFigcrProperties() { }

    public String getBasepath() {
        return basePath;
    }

    public void setBasepath(String basepath) {
        this.basePath = basepath;
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
