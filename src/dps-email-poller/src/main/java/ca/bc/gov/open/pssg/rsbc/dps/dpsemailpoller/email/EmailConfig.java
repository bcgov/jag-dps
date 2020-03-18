package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfig {
    private final EmailProperties emailProperties;

    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public EmailService emailService(ExchangeService exchangeService) {
        return new EmailServiceImpl(exchangeService, emailProperties.getEmailsPerBatch(), emailProperties.getErrorFolder(), emailProperties.getProcessingFolder());
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
