package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * This class represents the Exchange custom properties
 *
 * @author alexjoybc@github
 *
 */
@ConfigurationProperties(prefix = "exchange.service")
public class ExchangeProperties {

    private String username;
    private String password;
    private String endpoint;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
