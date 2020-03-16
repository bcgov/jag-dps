package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeProperties;
import microsoft.exchange.webservices.data.core.ExchangeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Value("${mailbox.emails.per.batch}")
    private int emailsPerBatch;

    @Value("${mailbox.error.folder}")
    private String mailboxErrorFolder;

    @Value("${mailbox.processing.folder}")
    private String mailboxProcessingFolder;

    private final ExchangeProperties exchangeProperties;

    public EmailConfig(ExchangeProperties exchangeProperties) {
        this.exchangeProperties = exchangeProperties;
    }

    @Bean
    public EmailService emailService(ExchangeService exchangeService) {
        return new EmailServiceImpl(exchangeService, emailsPerBatch, mailboxErrorFolder, mailboxProcessingFolder);
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
