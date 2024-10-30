package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMSGraphException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.EmailProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMSGraphMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.MSGraphService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.Message;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
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

@DisplayName("Junk Removal test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MSGraphPollerJunkRemovalTest {

    public static final String I_M_JUNK = "I'm junk";
    public static final String ErrorFolder = "errorFolder";
    public static final String ProcessedFolder = "ProcessedFolder";
    public static final String ProcessingFolder = "ProcessingFolder";
    public static final boolean CreateFolder = true;
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;
    @Mock
    private MSGraphService grahphServiceMock;

    @Mock
    private ExchangeService exchangeServiceMock;

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

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        ItemId itemId = new ItemId("test");
        Mockito.when(itemMock.getId()).thenReturn("test");
        Mockito.when(itemMock.getSubject()).thenReturn(I_M_JUNK);

        Mockito.when(emailProperties.getErrorFolder()).thenReturn(ErrorFolder);
        Mockito.when(emailProperties.getProcessedFolder()).thenReturn(ProcessedFolder);

        sut = new EmailPoller(emailServiceMock, grahphServiceMock,dpsMetadataMapperMock, dpsMSGraphMetadataMapperMock, messagingServiceMock, storageServiceMock,"tenant", emailProperties);
    }

    @Test
    @DisplayName("Success - 1 Junk mail should be removed")
    public void with1JunkEmailShouldBeRemoved() throws Exception {

        List<Message> result = new ArrayList<>();
        result.add(itemMock);

        Mockito.when(grahphServiceMock.GetMessages(Mockito.any())).thenReturn(result);
        Mockito.when(grahphServiceMock.moveToFolder(Mockito.anyString(), Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder))).thenReturn(itemMock);

        sut.pollForMSGraphEmails();

        Mockito.verify(grahphServiceMock, Mockito.times(1))
                .moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder));
    }

    @Test
    @DisplayName("Success - no Junk mail should be removed")
    public void with0JunkEmailShouldBeRemoved() throws Exception {
        List<Message> result = new ArrayList<>();

        Mockito.when(grahphServiceMock.GetMessages(Mockito.any())).thenReturn(result);

        sut.pollForMSGraphEmails();

        Mockito
                .verify(grahphServiceMock, Mockito.times(0))
                .moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder));
    }

    @Test
    @DisplayName("Success - 5 Junk mail should be removed")
    public void with5JunkEmailShouldBeRemoved() throws Exception {

        List<Message> result = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            result.add(itemMock);
        }

        Mockito
                .when(grahphServiceMock.GetMessages(Mockito.any()))
                .thenReturn(result);

        Mockito
                .when(grahphServiceMock.moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder)))
                .thenReturn(itemMock);

        sut.pollForMSGraphEmails();

        Mockito
                .verify(grahphServiceMock, Mockito.times(5))
                .moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder));
    }

    @Test
    @DisplayName("Exception - with exception should log error")
    public void withExceptionJunkEmailShouldLogError() throws Exception {

         Mockito.when(grahphServiceMock.moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.anyBoolean())).thenThrow(new DpsMSGraphException("error"));
        sut.pollForMSGraphEmails();

        Mockito
                .verify(grahphServiceMock, Mockito.times(0))
                .moveToFolder(Mockito.anyString(),Mockito.eq(ErrorFolder), Mockito.eq(CreateFolder));
    }


    @Test
    @DisplayName("Exception - with error should log error")
    public void withServiceExceptionShouldMoveToError() throws ServiceLocalException {

        List<Message> result = new ArrayList<>();
        result.add(itemMock);

        Mockito.when(grahphServiceMock.GetMessages(Mockito.any())).thenReturn(result);
        Mockito.when(itemMock.getId()).thenThrow(DpsMSGraphException.class);
        sut.pollForMSGraphEmails();

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());


    }

}
