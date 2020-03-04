package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeProperties;
import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    private final ExchangeProperties exchangeProperties;

    public EmailConfig(ExchangeProperties exchangeProperties) {
        this.exchangeProperties = exchangeProperties;
    }

    @Bean
    public EmailService emailService(ExchangeService exchangeService) {
        return new EmailServiceImpl(exchangeService, exchangeProperties.getEmailsPerBatch());
    }

}
