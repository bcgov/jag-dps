package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeServiceFactory;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.property.complex.AttachmentCollection;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmailServiceImpl implements EmailService {

    private final ExchangeServiceFactory exchangeServiceFactory;
    private final Integer maxMessagePerGet;
    private final String mailboxErrorFolder;
    private final String mailboxProcessingFolder;
    private final String mailboxProcessedFolder;


    public EmailServiceImpl(ExchangeServiceFactory exchangeServiceFactory, Integer maxMessagePerGet, String mailboxErrorFolder,
                            String mailboxProcessingFolder, String mailboxProcessedFolder) {
        this.exchangeServiceFactory = exchangeServiceFactory;
        this.maxMessagePerGet = maxMessagePerGet;
        this.mailboxErrorFolder = mailboxErrorFolder;
        this.mailboxProcessingFolder = mailboxProcessingFolder;
        this.mailboxProcessedFolder = mailboxProcessedFolder;
    }

    @Override
    public List<EmailMessage> getDpsInboxEmails() {

        ItemView view = maxMessagePerGet == 0 ? new ItemView(Integer.MAX_VALUE) : new ItemView(maxMessagePerGet);
        FindItemsResults<Item> findResults = null;

        try (ExchangeService exchangeService = exchangeServiceFactory.createService()) {
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

        return findResults.getItems().stream().map(item -> (EmailMessage) item).collect(Collectors.toList());
    }

    @Override
    public List<EmailMessage> getDpsInboxJunkEmails() {

        ItemView view = maxMessagePerGet == 0 ? new ItemView(Integer.MAX_VALUE) : new ItemView(maxMessagePerGet);
        FindItemsResults<Item> findResults = null;

        try (ExchangeService exchangeService = exchangeServiceFactory.createService()) {
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

        return findResults.getItems().stream().map(item -> (EmailMessage) item).collect(Collectors.toList());
    }

    private FolderId getFolderIdByDisplayName(String displayName) throws Exception {

        try (ExchangeService exchangeService = exchangeServiceFactory.createService()) {
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
        } catch (Exception e) {
            throw new DpsEmailException("Exception while getting junk emails from inbox", e.getCause());
        }

    }

    @Override
    public EmailMessage moveToErrorFolder(String id) {
        return moveToFolder(id, this.mailboxErrorFolder);
    }

    @Override
    public EmailMessage moveToProcessingFolder(String id) {
        return moveToFolder(id, this.mailboxProcessingFolder);
    }

    @Override
    public EmailMessage moveToProcessedFolder(String id) {
        return moveToFolder(id, this.mailboxProcessedFolder);
    }


    private EmailMessage moveToFolder(String id, String folderName) {
        try (ExchangeService exchangeService = exchangeServiceFactory.createService()) {
            ItemId itemId = new ItemId(id);
            FolderId processingFolderId = getFolderIdByDisplayName(folderName);
            return (EmailMessage) exchangeService.moveItem(itemId, processingFolderId);

        } catch (Exception e) {
            throw new DpsEmailException("Exception while moving email to " + folderName, e.getCause());
        }
    }

    @Override
    public List<FileAttachment> getFileAttachments(EmailMessage emailMessage) {


        List<FileAttachment> result = new ArrayList<>();

        try {
            if (emailMessage.getHasAttachments()) {

                AttachmentCollection attachmentCollection = emailMessage.getAttachments();

                attachmentCollection.forEach(attachment -> {

                    if (attachment instanceof FileAttachment) {

                        try {
                            attachment.load();
                            result.add((FileAttachment) attachment);
                        } catch (Exception e) {
                            // do nothing for now, if the attachment cannot be loaded, it is ignored.
                        }
                    }

                });
            }

        } catch (ServiceLocalException e) {
            throw new DpsEmailException("Exception while reading email attachments", e.getCause());
        }

        return result;
    }


}
