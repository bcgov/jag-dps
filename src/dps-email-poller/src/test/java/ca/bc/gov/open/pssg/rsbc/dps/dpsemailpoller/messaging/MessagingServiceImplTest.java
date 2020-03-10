package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessagingServiceImplTest {

    public static final String AMQP_EXCEPTION = "amqp exception";
    public static final String DPS_TENANT_SUCCESS = "1";
    public static final String DPS_TENANT_EXCEPTION = "2";

    @Value("${dps.tenant}")
    private String dpsTenant;

    private MessagingServiceImpl sut;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        Mockito.doNothing().when(rabbitTemplateMock).convertAndSend(Mockito.eq(DPS_TENANT_SUCCESS), Mockito.any(Item.class));
        Mockito.doThrow(new AmqpException(AMQP_EXCEPTION)).when(rabbitTemplateMock).convertAndSend(Mockito.eq(
                DPS_TENANT_EXCEPTION), Mockito.any(Item.class));

        sut = new MessagingServiceImpl(rabbitTemplateMock);
    }

    @Test
    public void withEmailMessageRequestShouldReturnSuccess() throws Exception {

        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");

        Mockito.doNothing().when(rabbitTemplateMock).convertAndSend(Mockito.eq(DPS_TENANT_SUCCESS), Mockito.any(Item.class));

        // Good tenant
        dpsTenant = DPS_TENANT_SUCCESS;

        Assertions.assertDoesNotThrow(() -> {
            sut.sendMessage(item);
        });
    }

    @Test
    public void withNoEmailMessageRequestShouldReturnError() throws Exception {

        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");

        Mockito.doThrow(new AmqpException(AMQP_EXCEPTION)).when(rabbitTemplateMock).convertAndSend(Mockito.eq(
                DPS_TENANT_EXCEPTION), Mockito.any(Item.class));

        // Bad tenant
        dpsTenant = DPS_TENANT_EXCEPTION;

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(item);
        });
    }

    @Test
    public void withNullTenantShouldReturnError() throws Exception {

        Item item = new Item(exchangeServiceMock);
        item.setSubject("I'm the subject");

        // Missing tenant
        dpsTenant = null;
        
        sut.sendMessage(item);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(item);
        });
    }
}
