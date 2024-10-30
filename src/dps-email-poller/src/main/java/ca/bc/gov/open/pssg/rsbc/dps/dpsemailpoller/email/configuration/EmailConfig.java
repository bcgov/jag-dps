package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeServiceFactory;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties({EmailProperties.class, ExchangeProperties.class})
public class EmailConfig {
    private final EmailProperties emailProperties;

    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public ExchangeServiceFactory exchangeConfig(ExchangeProperties exchangeProperties) {
        return new ExchangeServiceFactory(exchangeProperties);
    }

    @Bean
    @Scope("prototype")
    public EmailService emailService(ExchangeServiceFactory exchangeServiceFactory) {
        return new EmailServiceImpl(exchangeServiceFactory, emailProperties.getEmailsPerBatch(), emailProperties.getErrorFolder(), emailProperties.getProcessingFolder(), emailProperties.getProcessedFolder());
    }

    @Bean
    public DpsEmailParser dpsEmailParser() {
        return new DpsEmailParserImpl();
    }

    @Bean
    public DpsMetadataMapper dpsMetadataMapper(DpsEmailParser dpsEmailParser) {
        return new DpsMetadataMapperImpl(dpsEmailParser);
    }

    @Bean
    public DpsMSGraphMetadataMapper dpsMSGraphMetadataMapper(DpsEmailParser dpsEmailParser) {
        return new DpsMSGraphMetadataMapperImpl(dpsEmailParser);
    }

}
