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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EmailServiceImpl implements EmailService {

    private final ExchangeService exchangeService;
    private final Integer maxMessagePerGet;

    public EmailServiceImpl(ExchangeService exchangeService, Integer maxMessagePerGet) {
        this.exchangeService = exchangeService;
        this.maxMessagePerGet = maxMessagePerGet;
    }

    @Override
    public FindItemsResults<Item> getDpsInboxEmails() {

        ItemView view = new ItemView(10);
        FindItemsResults<Item> findResults =
                null;

        try {
            view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Ascending);

            view.setPropertySet(new PropertySet(BasePropertySet.IdOnly, ItemSchema.Subject,
                    ItemSchema.DateTimeReceived));

            // TODO: RETURN ONLY EMAILS WITH ATTACHMENTS.
            // TODO: LIMIT THE AMOUNT OF MESSAGES PER GET
            findResults = exchangeService.findItems(WellKnownFolderName.Inbox,
//                        new SearchFilter.SearchFilterCollection(
//                                LogicalOperator.Or,
//                                new SearchFilter.ContainsSubstring(ItemSchema.Subject, "EWS"),
//                                new SearchFilter.ContainsSubstring(ItemSchema.Subject, "API")),
                    view);

            exchangeService.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);

        } catch (Exception e) {
            throw new DpsEmailException("Exception while getting emails from inbox", e.getCause());
        }

        return findResults;
    }

    @Override
    public FindItemsResults<Item> getDpsInboxJunkEmails() {
        // TODO: GET emails with no attachments.
        throw new NotImplementedException();
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
