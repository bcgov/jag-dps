package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.search.FindItemsResults;
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

    private EmailServiceImpl sut;

    @Mock
    private ExchangeService exchangeServiceMock;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        sut = new EmailServiceImpl(exchangeServiceMock, 5);
    }

    @Test
    public void withEmailAttachmentReturnValidResponse() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");
        items.getItems().add(item);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(ItemView.class))).thenReturn(items);

        List<Item> result = sut.getDpsInboxEmails();

        Assertions.assertNotNull(result);
    }

    @Test
    public void withEmailAttachmentReturnError() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");
        items.getItems().add(item);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class), Mockito.any(ItemView.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            List<Item> result = sut.getDpsInboxEmails();
        });
    }

    @Test
    public void withEmailJunkReturnValidResponse() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");
        items.getItems().add(item);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class), Mockito.any(ItemView.class))).thenReturn(items);

        List<Item> result = sut.getDpsInboxJunkEmails();

        Assertions.assertNotNull(result);
    }

    @Test
    public void withEmailJunkReturnError() throws Exception {

        FindItemsResults<Item> items = new FindItemsResults();
        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");
        items.getItems().add(item);

        Mockito.when(exchangeServiceMock.findItems(Mockito.any(WellKnownFolderName.class), Mockito.any(SearchFilter.class) , Mockito.any(ItemView.class))).thenThrow(DpsEmailException.class);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            List<Item> result = sut.getDpsInboxJunkEmails();
        });
    }
}
