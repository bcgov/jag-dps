package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler.EWSAutodiscoverAPI;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(ExchangeProperties.class)
public class ExchangeConfig {

    private final ExchangeProperties exchangeProperties;

    public ExchangeConfig(ExchangeProperties exchangeProperties) {
        this.exchangeProperties = exchangeProperties;
    }

    @Bean
    @Scope("prototype")
    @SuppressWarnings("squid:S2095")
    // We are suppressing the warning about Resources should be closed.
    // ExchangeService implements the Closable interface but we are not sure how this should be handled in a spring boot context.
    // we are replacing the warning with a todo
    public ExchangeService exchangeService() throws Exception {

        //TODO: investigate try-with closable reousces in spring boot
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        service.setUrl(new URI(exchangeProperties.getEndpoint()));
        service.setCredentials(new WebCredentials(exchangeProperties.getUsername(), exchangeProperties.getPassword()));
        service.autodiscoverUrl(exchangeProperties.getUsername(), new EWSAutodiscoverAPI.RedirectionUrlCallback());
        service.setTraceEnabled(true);
        return service;

    }

}
