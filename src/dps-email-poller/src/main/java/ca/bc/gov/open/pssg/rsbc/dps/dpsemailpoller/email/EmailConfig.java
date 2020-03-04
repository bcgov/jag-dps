package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Bean
    public EmailService emailService(ExchangeService exchangeService) {
        return new EmailServiceImpl(exchangeService, 0);
    }

}
