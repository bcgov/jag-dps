package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.EmailProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.MSGraphDpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.MSGraphService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.AttachmentCollection;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("email processing test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailPollerPollForEmailsTest {

    public static final String I_M_JUNK = "I'm junk";
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private MSGraphService grahphServiceMock;

    @Mock
    private MessagingService messagingServiceMock;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Mock
    private DpsMetadataMapper dpsMetadataMapperMock;

    @Mock
    private MSGraphDpsMetadataMapper dpsMSGraphMetadataMapperMock;

    @Mock
    private EmailMessage itemMock;

    @Mock
    private StorageService storageServiceMock;

    @Mock
    private EmailProperties emailProperties;


    @BeforeEach
    public void SetUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        DpsFileInfo fakeFileInfo = new DpsFileInfo("id", "name", "contentType");

        Mockito
                .when(dpsMetadataMapperMock
                        .map(Mockito.any(EmailMessage.class), Mockito.any(DpsFileInfo.class),  Mockito.anyString()))
                .thenReturn(new DpsMetadata.Builder().withFileInfo(fakeFileInfo).withSubject("test").build());

        Mockito
                .when(itemMock.getHasAttachments())
                .thenReturn(true);

        Mockito
                .when(storageServiceMock.put(Mockito.any())).thenReturn("fileid");


        AttachmentCollection attachmentCollection = new AttachmentCollection();
        attachmentCollection.addFileAttachment("test", "test".getBytes());
        attachmentCollection.setOwner(itemMock);

        Mockito
                .when(emailServiceMock.getFileAttachments(Mockito.eq("test")))
                .thenReturn(attachmentCollection.getItems().stream().map(item -> (FileAttachment)item).collect(Collectors.toList()));



        ItemId itemId = new ItemId("test");
        Mockito.when(itemMock.getId()).thenReturn(itemId);
        Mockito.when(itemMock.getSubject()).thenReturn(I_M_JUNK);

        sut = new EmailPoller(emailServiceMock, grahphServiceMock,dpsMetadataMapperMock, dpsMSGraphMetadataMapperMock, messagingServiceMock, storageServiceMock,"tenant", emailProperties);

    }

    @Test
    @DisplayName("Success - 1 mail should be processed")
    public void with1EmailShouldBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();


        result.add(itemMock);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.when(emailServiceMock.moveToProcessingFolder(Mockito.anyString())).thenReturn(itemMock);
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForEwsEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(1))
                .moveToProcessingFolder(Mockito.anyString());

        Mockito
                .verify(messagingServiceMock, Mockito.times(1))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Success - no mail should be processed")
    public void with0EmailShouldNotBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);

        sut.pollForEwsEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToProcessingFolder(Mockito.anyString());

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Success - 5 mail should be processed")
    public void with5EmailShouldNotBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            result.add(itemMock);
        }

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.when(emailServiceMock.moveToProcessingFolder(Mockito.anyString())).thenReturn(itemMock);
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForEwsEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(5))
                .moveToProcessingFolder(Mockito.anyString());

        Mockito
                .verify(messagingServiceMock, Mockito.times(5))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Exception - with DpsEmailException should log error")
    public void withExceptionEmailShouldBeRemoved() throws Exception {

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenThrow(new DpsEmailException("error"));
        sut.pollForEmails();


        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Exception - with error should log error")
    public void withServiceExceptionShouldMoveToError() throws ServiceLocalException {

        List<EmailMessage> result = new ArrayList<>();
        result.add(itemMock);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.when(itemMock.getId()).thenThrow(ServiceLocalException.class);
        sut.pollForEmails();

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());


    }

}
