package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.sftp;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for sftp configuration
 *
 * @author alexjoybc@github
 *
 */
@ConfigurationProperties(prefix = "dps.sftp")
public class SftpProperties {

    private String host;
    private String port;
    private String username;
    private String password;
    private String knownHosts;
    private String remoteLocation;

    public String getKnownHosts() {
        return knownHosts;
    }

    public void setKnownHosts(String knownHosts) {
        this.knownHosts = knownHosts;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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

    public String getRemoteLocation() { return remoteLocation; }

    public void setRemoteLocation(String remoteLocation) { this.remoteLocation = remoteLocation; }

}
