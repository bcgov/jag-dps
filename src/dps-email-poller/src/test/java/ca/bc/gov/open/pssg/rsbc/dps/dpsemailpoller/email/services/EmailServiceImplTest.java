package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.*;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    public static final String ATTACHMENT_CONTENT = "content";
    public static final String ATTACHEMENT_NAME = "attachement1";

    private EmailServiceImpl sut;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Mock
    private EmailMessage itemMock;

    @Mock
    private EmailMessage emailAttachmentMessageMock;

    @Mock
    private EmailMessage emailMessageLoadFailMock;

    @Mock
    private Item randomItemMock;

    @Mock
    private EmailMessage emailMessageGetAttachmentException;

    @BeforeAll
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);


        ItemId itemId = new ItemId("test");
        Mockito.when(itemMock.getId()).thenReturn(itemId);
        Mockito.when(itemMock.getSubject()).thenReturn("subject");

        Mockito.when(emailMessageGetAttachmentException.getHasAttachments()).thenThrow(ServiceLocalException.class);

        AttachmentCollection attachmentCollection = new AttachmentCollection();
        attachmentCollection.setOwner(randomItemMock);
        attachmentCollection.addFileAttachment(ATTACHEMENT_NAME, ATTACHMENT_CONTENT.getBytes());

        Mockito.when(emailAttachmentMessageMock.getHasAttachments()).thenReturn(true);
        Mockito.when(emailAttachmentMessageMock.getAttachments()).thenReturn(attachmentCollection);

        AttachmentCollection attachmentCollectionNoService = new AttachmentCollection();
        Mockito.when(emailMessageLoadFailMock.getHasAttachments()).thenReturn(true);
        Mockito.when(emailMessageLoadFailMock.getAttachments()).thenReturn(attachmentCollectionNoService);


        sut = new EmailServiceImpl(exchangeServiceMock, 5, "ErrorHold", "Processing", "processed");
    }

    @Test
    public void withEmailAttachmentReturnValidResponse() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        items.getItems().add(itemMock);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(ItemView.class))).thenReturn(items);

        List<EmailMessage> result = sut.getDpsInboxEmails();

        Assertions.assertNotNull(result);
    }

    @Test
    public void withEmailAttachmentReturnError() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        items.getItems().add(itemMock);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class), Mockito.any(ItemView.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            List<EmailMessage> result = sut.getDpsInboxEmails();
        });
    }

    @Test
    public void withEmailJunkReturnValidResponse() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        items.getItems().add(itemMock);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class), Mockito.any(ItemView.class))).thenReturn(items);

        List<EmailMessage> result = sut.getDpsInboxJunkEmails();

        Assertions.assertNotNull(result);
    }

    @Test
    public void withEmailJunkReturnError() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        items.getItems().add(itemMock);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(ItemView.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            List<EmailMessage> result = sut.getDpsInboxJunkEmails();
        });
    }

    @Test
    public void withMoveEmailJunkReturnValidResponse() throws Exception {


        FindFoldersResults folders = new FindFoldersResults();
        Folder folder = new Folder(exchangeServiceMock);
        folder.setDisplayName("I'm the display name");
        folders.getFolders().add(folder);

        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenReturn(folders);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenReturn(itemMock);

        Assertions.assertDoesNotThrow(() -> {
            sut.moveToErrorFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailJunkReturnNoFolder() throws Exception {


        FindFoldersResults folders = new FindFoldersResults();

        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenReturn(folders);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenReturn(itemMock);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToErrorFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailJunkReturnError() throws Exception {


        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenThrow(DpsEmailException.class);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToErrorFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailReturnValidResponse() throws Exception {

        FindFoldersResults folders = new FindFoldersResults();
        Folder folder = new Folder(exchangeServiceMock);
        folder.setDisplayName("I'm the display name");
        folders.getFolders().add(folder);

        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenReturn(folders);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenReturn(itemMock);

        Assertions.assertDoesNotThrow(() -> {
            sut.moveToProcessingFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailReturnNoFolder() throws Exception {

        FindFoldersResults folders = new FindFoldersResults();

        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenReturn(folders);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenReturn(itemMock);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToProcessingFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailReturnError() throws Exception {


        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenThrow(DpsEmailException.class);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToProcessingFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailToProcessedReturnNoFolder() throws Exception {

        FindFoldersResults folders = new FindFoldersResults();

        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenReturn(folders);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenReturn(itemMock);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToProcessedFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withMoveEmailToProcessedReturnError() throws Exception {


        Mockito.when(exchangeServiceMock.findFolders(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(FolderView.class))).thenThrow(DpsEmailException.class);

        Mockito.when(exchangeServiceMock.moveItem(Mockito.any(ItemId.class), Mockito.any(FolderId.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.moveToProcessedFolder(itemMock.getId().getUniqueId());
        });
    }

    @Test
    public void withFileAttachmentShouldReturnAListOfAttachments () throws Exception {

        Mockito.when(randomItemMock.getService()).thenReturn(exchangeServiceMock);
        Mockito.doNothing().when(exchangeServiceMock)
                .getAttachment(
                        Mockito.any(Attachment.class),
                        Mockito.any(BodyType.class),
                        Mockito.any(Iterable.class));

        List<FileAttachment> attachments = sut.getFileAttachments(emailAttachmentMessageMock);
        Assertions.assertEquals(1, attachments.size());
        Assertions.assertEquals(ATTACHMENT_CONTENT, new String(attachments.stream().findFirst().get().getContent()));
        Assertions.assertEquals(ATTACHEMENT_NAME, attachments.stream().findFirst().get().getName());

    }

    @Test
    public void withFileAttachmentLoadFailShouldReturnAnEmptyList () {


        List<FileAttachment> attachments = sut.getFileAttachments(emailMessageLoadFailMock);
        Assertions.assertEquals(0, attachments.size());

    }

    @Test
    public void withNoAttachmentLoadFailShouldReturnAnEmptyList () {

        List<FileAttachment> attachments = sut.getFileAttachments(itemMock);
        Assertions.assertEquals(0, attachments.size());

    }


    @Test
    public void withAttachmentThrowsExceptionShouldThrowDpsException () {

        Assertions.assertThrows(DpsEmailException.class, () -> {
            List<FileAttachment> attachments = sut.getFileAttachments(emailMessageGetAttachmentException);
        });
    }

}
