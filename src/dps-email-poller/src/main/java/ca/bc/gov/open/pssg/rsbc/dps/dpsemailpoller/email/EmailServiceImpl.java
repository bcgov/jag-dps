package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String ERROR_HOLD_FOLDER = "ErrorHold";
    private final String PROCESSING_FOLDER = "Processing";

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

    private FolderId getFolderIdByDisplayName(String displayName) throws Exception {

        FolderView view = new FolderView(1);

        FindFoldersResults findFolderResults = exchangeService.findFolders(WellKnownFolderName.MsgFolderRoot,
                new SearchFilter.IsEqualTo(FolderSchema.DisplayName, displayName),
                view);

        if (!findFolderResults.getFolders().isEmpty()) {
            Folder folder = findFolderResults.getFolders().get(0);
            exchangeService.loadPropertiesForFolder(folder, PropertySet.FirstClassProperties);

            return folder.getId();
        } else {
            throw new DpsEmailException("Exception - unable to find " + displayName + " email folder ");
        }
    }

    @Override
    public void moveToErrorFolder(Item item) {

        try {
            FolderId errorHoldFolderId = getFolderIdByDisplayName(ERROR_HOLD_FOLDER);
            exchangeService.moveItem(item.getId(), errorHoldFolderId);

        } catch (Exception e) {
            throw new DpsEmailException("Exception while moving email to " + ERROR_HOLD_FOLDER, e.getCause());
        }
    }

    @Override
    public void moveToProcessingFolder(Item item) {

        try {
            FolderId processingFolderId = getFolderIdByDisplayName(PROCESSING_FOLDER);
            exchangeService.moveItem(item.getId(), processingFolderId);

        } catch (Exception e) {
            throw new DpsEmailException("Exception while moving email to " + PROCESSING_FOLDER, e.getCause());
        }
    }

}
