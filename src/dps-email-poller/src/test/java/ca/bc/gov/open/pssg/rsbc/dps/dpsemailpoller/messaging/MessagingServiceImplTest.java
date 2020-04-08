package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import microsoft.exchange.webservices.data.core.service.item.Item;
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
    public static final String TENANT = "tenant";

    private MessagingServiceImpl sut;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @BeforeAll
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void withEmailMessageRequestShouldReturnSuccess() {

        Mockito.doNothing().when(rabbitTemplateMock).
                convertAndSend(Mockito.eq(DPS_TENANT_SUCCESS), Mockito.any(Item.class));

        sut = new MessagingServiceImpl(rabbitTemplateMock);

        DpsMetadata dpsMetadata = new DpsMetadata.Builder().withApplicationID("test").build();

        Assertions.assertDoesNotThrow(() -> {
            sut.sendMessage(dpsMetadata, DPS_TENANT_SUCCESS);
        });
    }

    @Test
    public void withNullTenantShouldReturnError() {

        // Missing tenant
        sut = new MessagingServiceImpl(rabbitTemplateMock);

        DpsMetadata dpsMetadata = new DpsMetadata.Builder().withApplicationID("test").build();

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(dpsMetadata, null);
        });
    }

    @Test
    public void withNullItemShouldReturnError() {

        sut = new MessagingServiceImpl(rabbitTemplateMock);

        Assertions.assertThrows(DpsEmailException.class, () -> {
            sut.sendMessage(null, DPS_TENANT_SUCCESS);
        });
    }
}
