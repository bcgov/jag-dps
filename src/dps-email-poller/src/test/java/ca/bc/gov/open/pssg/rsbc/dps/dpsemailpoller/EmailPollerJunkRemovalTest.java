package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.EmailService;
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

@DisplayName("Junk Removal test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailPollerJunkRemovalTest {

    public static final String I_M_JUNK = "I'm junk";
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private ExchangeService exchangeServiceMock;

    @BeforeEach
    public void SetUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        sut = new EmailPoller(emailServiceMock);
    }

    @Test
    @DisplayName("Success - 1 Junk mail should be removed")
    public void with1JunkEmailShouldBeRemoved() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Item item = new Item(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.getItems().add(item);

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToErrorFolder(Mockito.any(Item.class));

        sut.junkRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(1))
                .moveToErrorFolder(Mockito.any(Item.class));
    }

    @Test
    @DisplayName("Success - no Junk mail should be removed")
    public void with0JunkEmailShouldBeRemoved() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToErrorFolder(Mockito.any(Item.class));

        sut.junkRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToErrorFolder(Mockito.any(Item.class));
    }

    @Test
    @DisplayName("Success - 5 Junk mail should be removed")
    public void with5JunkEmailShouldBeRemoved() throws Exception {

        FindItemsResults<Item> result = new FindItemsResults<Item>();

        Item item = new Item(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);
        result.getItems().add(item);

        Mockito.when(emailServiceMock.getDpsInboxJunkEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToErrorFolder(Mockito.any(Item.class));

        sut.junkRemoval();

        Mockito
                .verify(emailServiceMock, Mockito.times(5))
                .moveToErrorFolder(Mockito.any(Item.class));
    }

}
