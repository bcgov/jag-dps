package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsEmailException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailContent;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsEmailMapperImplTest {

    public static final String VALIDHTML = "validhtml";
    public static final String TENANT = "tenant";
    public static final String JOB_ID = "jobId";
    public static final String PHONE_NUMBER = "phone_number";
    private static final String RECIPIENT = "recipient";
    public static final String RECIPIENT_EMAIL = RECIPIENT + "@text.com";
    public static final String FROM_EMAIL = "from@text.com";
    public static final String INBOUND = "inbound";
    public static final String SUBJECT = "subject";
    public static final String UNIQUE_ID = "uniqueId";
    private DpsMetadataMapperImpl sut;

    @Mock
    private DpsEmailParserImpl dpsEmailParserMock;

    @Mock
    private ExchangeService exchangeServiceMock;

    @Mock
    EmailMessage messageMock;

    @BeforeEach
    private void setUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(exchangeServiceMock.getRequestedServerVersion()).thenReturn(ExchangeVersion.Exchange2010_SP2);

        Mockito.when(dpsEmailParserMock.parseEmail(VALIDHTML))
                .thenReturn(new DpsEmailContent
                        .Builder()
                        .withPageCount(1)
                        .withDate(new Date())
                        .withJobId(JOB_ID)
                        .withPhoneNumber(PHONE_NUMBER)
                        .build());

        sut = new DpsMetadataMapperImpl(dpsEmailParserMock);

    }

    @Test
    public void withValidEmailShouldReturnMetadata() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);

        EmailAddress recipient = new EmailAddress();
        recipient.setAddress(RECIPIENT_EMAIL);

        EmailAddressCollection collection = new EmailAddressCollection();
        collection.add(recipient);
        Mockito.when(messageMock.getToRecipients()).thenReturn(collection);

        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);

        EmailAddress from = new EmailAddress();
        from.setAddress(FROM_EMAIL);
        Mockito.when(messageMock.getFrom()).thenReturn(from);

        MessageBody messageBody = new MessageBody();
        messageBody.setText(VALIDHTML);
        messageBody.setBodyType(BodyType.HTML);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getDateTimeReceived()).thenReturn(calReceive.getTime());
        Mockito.when(messageMock.getDateTimeSent()).thenReturn(calSent.getTime());

        ItemId itemId = new ItemId(UNIQUE_ID);

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        DpsMetadata result = sut.map(messageMock, TENANT);

        Assertions.assertEquals(TENANT, result.getApplicationID());
        Assertions.assertEquals(VALIDHTML, result.getBody());
        Assertions.assertEquals(PHONE_NUMBER, result.getOriginatingNumber());
        Assertions.assertEquals(INBOUND, result.getDirection());
        Assertions.assertEquals(JOB_ID, result.getFaxJobID());
        Assertions.assertEquals(FROM_EMAIL, result.getFrom());
        Assertions.assertEquals(RECIPIENT_EMAIL, result.getTo());
        Assertions.assertEquals(RECIPIENT, result.getInboundChannelID());
        Assertions.assertEquals("FAX", result.getInboundChannelType());
        Assertions.assertNull(result.getDestinationNumber());
        Assertions.assertEquals(SUBJECT, result.getSubject());
        Assertions.assertEquals(1, result.getNumberOfPages());
        Assertions.assertEquals(calReceive.getTime(), result.getReceivedDate());
        Assertions.assertEquals(calSent.getTime(), result.getSentDate());

    }

    @Test
    public void withExceptionShouldThrowDpsException() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);

        EmailAddress recipient = new EmailAddress();
        recipient.setAddress(RECIPIENT_EMAIL);

        EmailAddressCollection collection = new EmailAddressCollection();
        collection.add(recipient);
        Mockito.when(messageMock.getToRecipients()).thenReturn(collection);

        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);

        EmailAddress from = new EmailAddress();
        from.setAddress(FROM_EMAIL);
        Mockito.when(messageMock.getFrom()).thenReturn(from);

        MessageBody messageBody = new MessageBody();
        messageBody.setText(VALIDHTML);
        messageBody.setBodyType(BodyType.HTML);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getDateTimeReceived()).thenReturn(calReceive.getTime());
        Mockito.when(messageMock.getDateTimeSent()).thenReturn(calSent.getTime());

        ItemId itemId = new ItemId(UNIQUE_ID);

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        Mockito.when(messageMock.getFrom()).thenThrow(ServiceLocalException.class);

        Assertions.assertThrows(DpsEmailException.class, () ->{
            DpsMetadata result = sut.map(messageMock, TENANT);
        });

    }


    @Test
    public void withValidTextEmailShouldReturnMetadata() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);

        EmailAddress recipient = new EmailAddress();
        recipient.setAddress(RECIPIENT_EMAIL);

        EmailAddressCollection collection = new EmailAddressCollection();
        collection.add(recipient);
        Mockito.when(messageMock.getToRecipients()).thenReturn(collection);

        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);

        EmailAddress from = new EmailAddress();
        from.setAddress(FROM_EMAIL);
        Mockito.when(messageMock.getFrom()).thenReturn(from);

        MessageBody messageBody = new MessageBody();
        messageBody.setText(VALIDHTML);
        messageBody.setBodyType(BodyType.Text);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getDateTimeReceived()).thenReturn(calReceive.getTime());
        Mockito.when(messageMock.getDateTimeSent()).thenReturn(calSent.getTime());

        ItemId itemId = new ItemId(UNIQUE_ID);

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        DpsMetadata result = sut.map(messageMock, TENANT);

        Assertions.assertEquals(TENANT, result.getApplicationID());
        Assertions.assertEquals(VALIDHTML, result.getBody());
        Assertions.assertEquals(PHONE_NUMBER, result.getOriginatingNumber());
        Assertions.assertEquals(INBOUND, result.getDirection());
        Assertions.assertEquals(JOB_ID, result.getFaxJobID());
        Assertions.assertEquals(FROM_EMAIL, result.getFrom());
        Assertions.assertEquals(RECIPIENT_EMAIL, result.getTo());
        Assertions.assertEquals(RECIPIENT, result.getInboundChannelID());
        Assertions.assertEquals("FAX", result.getInboundChannelType());
        Assertions.assertNull(result.getDestinationNumber());
        Assertions.assertEquals(SUBJECT, result.getSubject());
        Assertions.assertEquals(1, result.getNumberOfPages());
        Assertions.assertEquals(calReceive.getTime(), result.getReceivedDate());
        Assertions.assertEquals(calSent.getTime(), result.getSentDate());

    }

}



