package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for sftp configuration
 *
 * @author alexjoybc@github
 */
@ConfigurationProperties(prefix = "dps.sftp")
public class SftpProperties {

    private String host;
    private String port;
    private String username;
    private String password;
    private String remoteLocation;
    private String knownHostsFileName;
    private String sshPrivateKey;
    private String sshPrivatePassphrase;
    private boolean allowUnknownKeys;

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

    public String getRemoteLocation() {
        return remoteLocation;
    }

    public void setRemoteLocation(String remoteLocation) {
        this.remoteLocation = remoteLocation;
    }

    public String getKnownHostsFileName() {
        return knownHostsFileName;
    }

    public void setKnownHostsFileName(String knownHostsFileName) {
        this.knownHostsFileName = knownHostsFileName;
    }

    public String getSshPrivateKey() {
        return sshPrivateKey;
    }

    public void setSshPrivateKey(String sshPrivateKey) {
        this.sshPrivateKey = sshPrivateKey;
    }

    public String getSshPrivatePassphrase() {
        return sshPrivatePassphrase;
    }

    public void setSshPrivatePassphrase(String sshPrivatePassphrase) {
        this.sshPrivatePassphrase = sshPrivatePassphrase;
    }

    public boolean isAllowUnknownKeys() {
        return allowUnknownKeys;
    }

    public void setAllowUnknownKeys(boolean allowUnknownKeys) {
        this.allowUnknownKeys = allowUnknownKeys;
    }
}
