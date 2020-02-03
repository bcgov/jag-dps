package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.ords.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * This class represents the Figaro Client custom properties
 *
 * @author shaunmillargov
 *
 */
@ConfigurationProperties(prefix = "ords.figcr.client")
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
