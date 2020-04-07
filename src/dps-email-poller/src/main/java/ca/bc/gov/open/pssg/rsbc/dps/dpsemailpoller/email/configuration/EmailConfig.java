package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.*;
import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfig {
    private final EmailProperties emailProperties;

    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    @Scope("prototype")
    public EmailService emailService(ExchangeService exchangeService) {
        return new EmailServiceImpl(exchangeService, emailProperties.getEmailsPerBatch(), emailProperties.getErrorFolder(), emailProperties.getProcessingFolder(), emailProperties.getProcessedFolder());
    }

    @Bean
    public DpsEmailParser dpsEmailParser() {
        return new DpsEmailParserImpl();
    }

    @Bean
    public DpsMetadataMapper dpsMetadataMapper(DpsEmailParser dpsEmailParser) {
        return new DpsMetadataMapperImpl(dpsEmailParser);
    }

}
