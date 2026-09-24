package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.Keys;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMSGraphException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.EmailProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMSGraphMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.MSGraphService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.models.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@DisplayName("email processing test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MSGraphPollerPollForEmailsMultiAttachmentTest {

    public static final String I_M_JUNK = "I'm junk";
    public static final String ErrorFolder = "errorFolder";
    public static final String ProcessedFolder = "ProcessedFolder";
    public static final String ProcessingFolder = "ProcessingFolder";
    public static final boolean CreateFolder = true;
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;
    @Mock
    private MSGraphService graphServiceMock;

    @Mock
    private MessagingService messagingServiceMock;

    @Mock
    private DpsMetadataMapper dpsMetadataMapperMock;
    @Mock
    private DpsMSGraphMetadataMapper dpsMSGraphMetadataMapperMock;

    @Mock
    private Message itemMock;

    @Mock
    private StorageService storageServiceMock;

    @Mock
    private EmailProperties emailProperties;


    @BeforeEach
    public void SetUp() throws Exception {

        MockitoAnnotations.openMocks(this);


        DpsFileInfo fakeFileInfo = new DpsFileInfo("id", "name", "contentType");

        Mockito.when(dpsMSGraphMetadataMapperMock.map(Mockito.any(Message.class), Mockito.any(DpsFileInfo.class),  Mockito.anyString()))
                .thenReturn(new DpsMetadata.Builder().withFileInfo(fakeFileInfo).withSubject("test").build());

        Mockito.when(itemMock.getHasAttachments())
                .thenReturn(true);

        Mockito.when(storageServiceMock.put(Mockito.any())).thenReturn("fileid");

        Mockito.when(itemMock.getId()).thenReturn("test");
        Mockito.when(itemMock.getSubject()).thenReturn(I_M_JUNK);

        Mockito.when(emailProperties.getErrorFolder()).thenReturn(ErrorFolder);
        Mockito.when(emailProperties.getProcessedFolder()).thenReturn(ProcessedFolder);
        Mockito.when(emailProperties.getProcessingFolder()).thenReturn(ProcessingFolder);

        sut = new EmailPoller(emailServiceMock, graphServiceMock,dpsMetadataMapperMock, dpsMSGraphMetadataMapperMock, messagingServiceMock, storageServiceMock,"tenant", emailProperties);

    }

    @Test
    @DisplayName("Success - 1 mail should be processed with multiple attachment")
    public void with1EmailShouldBeProcessed() throws Exception {

        FileAttachment attachment1 = new FileAttachment();
        attachment1.setOdataType("microsoft.graph.fileAttachment");
        attachment1.setName("name-value");
        attachment1.setContentType("garbage");
        attachment1.setIsInline(false);
        attachment1.setContentLocation("contentLocation-value");

        FileAttachment attachment2 = new FileAttachment();
        attachment2.setOdataType("microsoft.graph.fileAttachment");
        attachment2.setName("name-value");
        attachment2.setContentType(Keys.PDF_CONTENT_TYPE);
        attachment2.setIsInline(false);
        attachment2.setContentLocation("contentLocation-value");

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment1);
        attachments.add(attachment2);

        Mockito.when(graphServiceMock.getAttachments(Mockito.any(Message.class)))
                .thenReturn(attachments);

        List<Message> result = new ArrayList<>();

        result.add(itemMock);

        Mockito.when(graphServiceMock.GetMessages(Mockito.any())).thenReturn(result);
        Mockito.when(graphServiceMock.moveToFolder(Mockito.anyString(), Mockito.eq(ProcessingFolder), Mockito.eq(CreateFolder))).thenReturn(itemMock);
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForMSGraphEmails();

        Mockito.verify(graphServiceMock, Mockito.times(1))
                .moveToFolder(Mockito.anyString(),Mockito.eq(ProcessingFolder), Mockito.eq(CreateFolder));

        Mockito.verify(messagingServiceMock, Mockito.times(1))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }


    @Test
    @DisplayName("Exception - with DpsEmailException should log error")
    public void withExceptionEmailShouldBeRemoved() throws Exception {

        FileAttachment attachment1 = new FileAttachment();
        attachment1.setOdataType("microsoft.graph.fileAttachment");
        attachment1.setName("name-value");
        attachment1.setContentType("garbage");
        attachment1.setIsInline(false);
        attachment1.setContentLocation("contentLocation-value");

        FileAttachment attachment2 = new FileAttachment();
        attachment2.setOdataType("microsoft.graph.fileAttachment");
        attachment2.setName("name-value");
        attachment2.setContentType("garbage");
        attachment2.setIsInline(false);
        attachment2.setContentLocation("contentLocation-value");

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment1);
        attachments.add(attachment2);

        Mockito.when(graphServiceMock.getAttachments(Mockito.any(Message.class)))
                .thenReturn(attachments);

        List<Message> result = new ArrayList<>();

        result.add(itemMock);

        Mockito.when(graphServiceMock.GetMessages(Mockito.any())).thenReturn(result);
        Mockito.when(graphServiceMock.moveToFolder(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(itemMock);
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForMSGraphEmails();

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

    }

}
