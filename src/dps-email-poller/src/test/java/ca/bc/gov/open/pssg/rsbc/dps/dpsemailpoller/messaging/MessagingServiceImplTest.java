package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessagingServiceImplTest {

    public static final String DPS_TENANT_SUCCESS = "1";
    public static final String DPS_TENANT_EXCEPTION = "2";

    private MessagingServiceImpl sut;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @Mock
    private Item itemMock;

    @BeforeAll
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        ItemId itemId = new ItemId();
        itemId.setUniqueId("abcd");
        Mockito.when(itemMock.getId()).thenReturn(itemId);

        Mockito.when(itemMock.getSubject()).thenReturn("I'm the subject");
    }

    @Test
    public void withEmailMessageRequestShouldReturnSuccess() {

        Mockito.doNothing().when(rabbitTemplateMock).
                convertAndSend(Mockito.eq(DPS_TENANT_SUCCESS), Mockito.any(Item.class));

        sut = new MessagingServiceImpl(rabbitTemplateMock, DPS_TENANT_SUCCESS);

        Assertions.assertDoesNotThrow(() -> {
            sut.sendMessage(itemMock);
        });
    }

//    @Test
//    public void withNoEmailMessageRequestShouldReturnError() {
//
//        Mockito.doThrow(DpsEmailException.class).when(rabbitTemplateMock).
//                convertAndSend(Mockito.eq(DPS_TENANT_EXCEPTION), Mockito.any(Item.class));
//
//        sut = new MessagingServiceImpl(rabbitTemplateMock, DPS_TENANT_EXCEPTION);
//
//        Assertions.assertThrows(DpsEmailException.class, () -> {
//            sut.sendMessage(itemMock);
//        });
//    }

    @Test
    public void withNullTenantShouldReturnError() {

        // Missing tenant
        sut = new MessagingServiceImpl(rabbitTemplateMock, null);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(itemMock);
        });
    }

    @Test
    public void withNullItemShouldReturnError() {

        sut = new MessagingServiceImpl(rabbitTemplateMock, DPS_TENANT_SUCCESS);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(null);
        });
    }
}
