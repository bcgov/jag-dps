package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EmailPoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExchangeService exchangeService;

    public EmailPoller(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @Scheduled(cron = "${mailbox.interval}")
    public void pollForEmails() throws Exception {
        // long now = System.currentTimeMillis() / 1000;
        logger.info("Poll for emails - {} ", new Date()); // now);

        Folder inbox = Folder.bind(exchangeService, WellKnownFolderName.Inbox);
        FindItemsResults<Item> findResults = findItems(exchangeService);
        logger.info("          found - " + Integer.toString(findResults.getTotalCount()) + " Messages found in your Inbox");
    }

    private FindItemsResults<Item> findItems(ExchangeService service) throws Exception {
        ItemView view = new ItemView(10);
        view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);
        view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject, ItemSchema.DateTimeReceived));

        FindItemsResults<Item> findResults =
                service.findItems(WellKnownFolderName.Inbox,
//                        new SearchFilter.SearchFilterCollection(
//                                LogicalOperator.Or,
//                                new SearchFilter.ContainsSubstring(ItemSchema.Subject, "EWS"),
//                                new SearchFilter.ContainsSubstring(ItemSchema.Subject, "API")),
                        view);

        //MOOOOOOST IMPORTANT: load items properties, before
        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);

        return findResults;
    }

}
