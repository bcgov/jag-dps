package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bambora")
public class BamboraProperties {

    private String merchantId;
    private String hashkey;
    private String timezone;

    private String hostedPaymentEndpoint;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getHostedPaymentEndpoint() {
        return hostedPaymentEndpoint;
    }

    public void setHostedPaymentEndpoint(String hostedPaymentEndpoint) {
        this.hostedPaymentEndpoint = hostedPaymentEndpoint;
    }

}
