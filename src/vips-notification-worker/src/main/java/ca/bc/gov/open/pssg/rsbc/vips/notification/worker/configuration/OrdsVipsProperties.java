package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * This class represents the Ords Vips properties
 *
 * @author shaunmillargov
 *
 */
@ConfigurationProperties(prefix = "vips-notification-worker.service.ords.vips.client")
public class OrdsVipsProperties {

    private String basePath;
    private String username;
    private String password;

    public OrdsVipsProperties() { }

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
