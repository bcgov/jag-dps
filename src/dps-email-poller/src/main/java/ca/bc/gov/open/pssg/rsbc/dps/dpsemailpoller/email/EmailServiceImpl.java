package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExchangeService exchangeService;
    private final Integer maxMessagePerGet;

    public EmailServiceImpl(ExchangeService exchangeService, Integer maxMessagePerGet) {
        this.exchangeService = exchangeService;
        this.maxMessagePerGet = maxMessagePerGet;
    }

    @Override
    public List<Item> getDpsInboxEmails() {

        ItemView view = maxMessagePerGet == 0 ? new ItemView(Integer.MAX_VALUE) : new ItemView(maxMessagePerGet);
        FindItemsResults<Item> findResults = null;

        try {
            view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);

            view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject,
                    ItemSchema.DateTimeReceived));

            findResults = exchangeService.findItems(WellKnownFolderName.Inbox,
                    new SearchFilter.IsEqualTo(ItemSchema.HasAttachments, true),
                    view);

            if (!findResults.getItems().isEmpty()) {
                exchangeService.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
            }

        } catch (Exception e) {
            throw new DpsEmailException("Exception while getting dps emails from inbox", e.getCause());
        }

        return findResults.getItems();
    }

    @Override
    public List<Item> getDpsInboxJunkEmails() {

        ItemView view = new ItemView(maxMessagePerGet);
        FindItemsResults<Item> findResults = null;

        try {
            view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject,
                    ItemSchema.DateTimeReceived));

            findResults = exchangeService.findItems(WellKnownFolderName.Inbox,
                    new SearchFilter.IsEqualTo(ItemSchema.HasAttachments, false),
                    view);

            if (!findResults.getItems().isEmpty()) {
                exchangeService.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
            }

        } catch (Exception e) {
            throw new DpsEmailException("Exception while getting junk emails from inbox", e.getCause());
        }

        return findResults.getItems();
    }

    @Override
    public void moveToErrorFolder(Item item) {
        // TODO: MOVE MESSAGES TO THE ErrorHold FOLDER
        throw new NotImplementedException();
    }

    @Override
    public void moveToProcessingFolder(Item item) {
        // TODO: MOVE MESSAGES TO THE Processing FOLDER
        throw new NotImplementedException();
    }


}
