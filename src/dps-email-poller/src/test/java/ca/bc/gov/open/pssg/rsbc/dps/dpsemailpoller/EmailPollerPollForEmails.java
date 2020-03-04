package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler.EmailPoller;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@DisplayName("email processing test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailPollerPollForEmails {

    public static final String I_M_JUNK = "I'm junk";
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private MessagingService messagingServiceMock;

    @Mock
    private ExchangeService exchangeServiceMock;

    @BeforeEach
    public void SetUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        sut = new EmailPoller(emailServiceMock, messagingServiceMock);
    }

    @Test
    @DisplayName("Success - 1 mail should be processed")
    public void with1EmailShouldBeProcessed() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Item item = new Item(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.getItems().add(item);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result.getItems());
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(Item.class));

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(1))
                .moveToProcessingFolder(Mockito.any(Item.class));

        Mockito
                .verify(messagingServiceMock, Mockito.times(1))
                .sendMessage(Mockito.any(Item.class));
    }

    @Test
    @DisplayName("Success - no mail should be processed")
    public void with0EmailShouldNotBeProcessed() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result.getItems());
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(Item.class));

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToProcessingFolder(Mockito.any(Item.class));

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(Item.class));
    }

    @Test
    @DisplayName("Success - 5 mail should be processed")
    public void with5EmailShouldNotBeProcessed() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Item item = new Item(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result.getItems());
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(Item.class));

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(5))
                .moveToProcessingFolder(Mockito.any(Item.class));

        Mockito
                .verify(messagingServiceMock, Mockito.times(5))
                .sendMessage(Mockito.any(Item.class));
    }

    @Test
    @DisplayName("Exception - with DpsEmailException should log error")
    public void withExceptionEmailShouldBeRemoved() throws Exception {


        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenThrow(new DpsEmailException("error"));
        sut.pollForEmails();

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(Item.class));
    }

}
