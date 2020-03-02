package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.BamboraClientImpl;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BamboraProperties.class)
public class BamboraConfig {

    @Bean
    public PaymentClient paymentClient(BamboraProperties bamboraProperties) {
        return new BamboraClientImpl(bamboraProperties);
    }

}
