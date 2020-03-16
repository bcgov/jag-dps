package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMetadataMapper;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.EmailService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging.MessagingService;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@DisplayName("email processing test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailPollerPollForEmailsTest {

    public static final String I_M_JUNK = "I'm junk";
    private EmailPoller sut;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private MessagingService messagingServiceMock;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Mock
    private DpsMetadataMapper dpsMetadataMapperMock;

    @BeforeEach
    public void SetUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        Mockito.when(dpsMetadataMapperMock.map(Mockito.any(EmailMessage.class), Mockito.anyString())).thenReturn(new DpsMetadata.Builder().withSubject("test").build());

        sut = new EmailPoller(emailServiceMock, dpsMetadataMapperMock, messagingServiceMock, "tenant");

    }

    @Test
    @DisplayName("Success - 1 mail should be processed")
    public void with1EmailShouldBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        EmailMessage item = new EmailMessage(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.add(item);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(1))
                .moveToProcessingFolder(Mockito.any(Item.class));

        Mockito
                .verify(messagingServiceMock, Mockito.times(1))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Success - no mail should be processed")
    public void with0EmailShouldNotBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(0))
                .moveToProcessingFolder(Mockito.any(Item.class));

        Mockito
                .verify(messagingServiceMock, Mockito.times(0))
                .sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());
    }

    @Test
    @DisplayName("Success - 5 mail should be processed")
    public void with5EmailShouldNotBeProcessed() throws Exception {

        List<EmailMessage> result = new ArrayList<>();

        EmailMessage item = new EmailMessage(exchangeServiceMock);
        item.setSubject(I_M_JUNK);

        result.add(item);
        result.add(item);
        result.add(item);
        result.add(item);
        result.add(item);

        Mockito.when(emailServiceMock.getDpsInboxEmails()).thenReturn(result);
        Mockito.doNothing().when(emailServiceMock).moveToProcessingFolder(Mockito.any(Item.class));
        Mockito.doNothing().when(messagingServiceMock).sendMessage(Mockito.any(DpsMetadata.class), Mockito.anyString());

        sut.pollForEmails();

        Mockito
                .verify(emailServiceMock, Mockito.times(5))
                .moveToProcessingFolder(Mockito.any(Item.class));

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

}
