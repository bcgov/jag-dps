package my.pkg.name;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.credential.WebCredentials;

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

    public static ExchangeService connectViaExchangeAutodiscover(String email, String password) {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        try {

            service.setCredentials(new WebCredentials(email, password));
            service.autodiscoverUrl(email, new RedirectionUrlCallback());
            service.setTraceEnabled(true);
            Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
            System.out.println("messages: " + inbox.getTotalCount());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }
}
