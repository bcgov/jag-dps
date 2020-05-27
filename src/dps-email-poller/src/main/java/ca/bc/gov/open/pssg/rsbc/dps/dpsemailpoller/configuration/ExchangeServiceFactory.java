package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler.EWSAutodiscoverAPI;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.WebCredentials;

import java.net.URI;

public class ExchangeServiceFactory {

    private final ExchangeProperties exchangeProperties;

    public ExchangeServiceFactory(ExchangeProperties exchangeProperties) {
        this.exchangeProperties = exchangeProperties;
    }

    public ExchangeService createService() throws Exception {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        service.setUrl(new URI(exchangeProperties.getEndpoint()));
        service.setCredentials(new WebCredentials(exchangeProperties.getUsername(), exchangeProperties.getPassword()));
        service.autodiscoverUrl(exchangeProperties.getUsername(), new EWSAutodiscoverAPI.RedirectionUrlCallback());
        service.setTraceEnabled(true);
        return service;
    }

}
