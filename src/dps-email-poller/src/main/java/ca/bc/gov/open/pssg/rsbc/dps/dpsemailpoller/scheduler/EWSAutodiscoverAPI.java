package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;

/**
 * Provides autodiscovery for EWS server (which could change). 
 * 
 * @author 176899
 *
 */
public class EWSAutodiscoverAPI {

    public static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(String redirectionUrl) {
          return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }
}
