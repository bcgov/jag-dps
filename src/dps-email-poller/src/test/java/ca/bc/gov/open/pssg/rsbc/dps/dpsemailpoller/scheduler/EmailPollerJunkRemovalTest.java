package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration.EmailProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMSGraphMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.MSGraphService;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.search.FindItemsResults;
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
class EmailPollerJunkRemovalTest {

    public static final String I_M_JUNK = "I'm junk";
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
    private EmailMessage itemMock;

    @Mock
    private StorageService storageServiceMock;

    @Mock
    private EmailProperties emailProperties;

    @BeforeEach
    public void SetUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        ItemId itemId = new ItemId("test");
        Mockito.when(itemMock.getId()).thenReturn(itemId);
        Mockito.when(itemMock.getSubject()).thenReturn(I_M_JUNK);

        sut = new EmailPoller(emailServiceMock, grahphServiceMock,dpsMetadataMapperMock, dpsMSGraphMetadataMapperMock, messagingServiceMock, storageServiceMock,"tenant", emailProperties);
    }

    @Test
    @DisplayName("Success - 1 Junk mail should be removed")
    public void with1JunkEmailShouldBeRemoved() throws Exception {

        List<EmailMessage> result = new ArrayList<>();
        result.add(itemMock);

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);
        Mockito.when(emailServiceMock.moveToErrorFolder(Mockito.anyString())).thenReturn(itemMock);

        sut.junkEwsRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(1))
                .moveToErrorFolder(Mockito.anyString());
    }

    @Test
    @DisplayName("Success - no Junk mail should be removed")
    public void with0JunkEmailShouldBeRemoved() throws Exception {
        List<EmailMessage> result = new ArrayList<>();

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);

        sut.junkEwsRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToErrorFolder(Mockito.anyString());
    }

    @Test
    @DisplayName("Success - 5 Junk mail should be removed")
    public void with5JunkEmailShouldBeRemoved() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            result.add(itemMock);
        }

        Mockito
                .when(emailServiceMock.getDpsInboxJunkEmails())
                .thenReturn(result);

        Mockito
                .when(emailServiceMock.moveToErrorFolder(Mockito.anyString()))
                .thenReturn(itemMock);

        sut.junkEwsRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(5))
                .moveToErrorFolder(Mockito.anyString());
    }

    @Test
    @DisplayName("Exception - with exception should log error")
    public void withExceptionJunkEmailShouldLogError() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Item item = new Item(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenThrow(new DpsEmailException("error"));
        sut.junkEwsRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToErrorFolder(Mockito.anyString());
    }


    @Test
    @DisplayName("Exception - with error should log error")
    public void withServiceExceptionShouldMoveToError() throws ServiceLocalException {

        List<EmailMessage> result = new ArrayList<>();
        result.add(itemMock);

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);
        Mockito.when(itemMock.getId()).thenThrow(ServiceLocalException.class);
        sut.junkEwsRemoval();

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());


    }

}
