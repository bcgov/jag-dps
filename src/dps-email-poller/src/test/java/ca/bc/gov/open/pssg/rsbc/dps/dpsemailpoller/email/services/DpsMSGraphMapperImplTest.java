package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.DpsMSGraphException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.models.DpsEmailContent;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import com.microsoft.graph.models.*;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsMSGraphMapperImplTest {

    private static final String VALIDHTML = "validhtml";
    private static final String TENANT = "tenant";
    private static final String JOB_ID = "jobId";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String RECIPIENT = "recipient";
    private static final String RECIPIENT_EMAIL = RECIPIENT + "@text.com";
    private static final String FROM_EMAIL = "from@text.com";
    private static final String INBOUND = "inbound";
    private static final String SUBJECT = "subject";
    private static final String UNIQUE_ID = "uniqueId";
    private static final String ID = "test";
    private static final String NAME = "test";
    private static final String CONTENT_TYPE = "application/xml";

    private DpsMSGraphMetadataMapperImpl sut;

    @Mock
    private DpsEmailParserImpl dpsEmailParserMock;


    @Mock
    Message messageMock;

    @BeforeEach
    private void setUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(dpsEmailParserMock.parseEmail(VALIDHTML))
                .thenReturn(new DpsEmailContent
                        .Builder()
                        .withPageCount(1)
                        .withDate(new Date())
                        .withJobId(JOB_ID)
                        .withPhoneNumber(PHONE_NUMBER)
                        .build());

        sut = new DpsMSGraphMetadataMapperImpl(dpsEmailParserMock);

    }

    @Test
    public void withValidEmailShouldReturnMetadata() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);


        EmailAddress toMail = new EmailAddress();
        toMail.setAddress(RECIPIENT_EMAIL);
        Recipient toRecipient = new Recipient();
        toRecipient.setEmailAddress(toMail);
        List<Recipient> toRecipients = new ArrayList<>();
        toRecipients.add(toRecipient);
        Mockito.when(messageMock.getToRecipients()).thenReturn(toRecipients);

        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);

        EmailAddress fromMail = new EmailAddress();
        fromMail.setAddress(FROM_EMAIL);
        Recipient fromRecipient = new Recipient();
        fromRecipient.setEmailAddress(fromMail);
        List<Recipient> fromRecipients = new ArrayList<>();
        fromRecipients.add(fromRecipient);
        Mockito.when(messageMock.getFrom()).thenReturn(fromRecipient);

        ItemBody messageBody = new ItemBody();
        messageBody.setContent(VALIDHTML);
        messageBody.setContentType(BodyType.Html);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getReceivedDateTime()).thenReturn(calReceive.getTime().toInstant().atOffset(ZoneOffset.UTC));
        Mockito.when(messageMock.getSentDateTime()).thenReturn(calSent.getTime().toInstant().atOffset(ZoneOffset.UTC));

        String itemId = new ItemId(UNIQUE_ID).toString();

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        DpsMetadata result = sut.map(messageMock, new DpsFileInfo(ID, NAME, CONTENT_TYPE), TENANT);

        Assertions.assertEquals(TENANT, result.getApplicationId());
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

        Assertions.assertEquals(ID, result.getFileInfo().getId());
        Assertions.assertEquals(NAME, result.getFileInfo().getName());
        Assertions.assertEquals(CONTENT_TYPE, result.getFileInfo().getContentType());


    }

    @Test
    public void withExceptionShouldThrowDpsException() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);

        EmailAddress fromMail = new EmailAddress();
        fromMail.setAddress(FROM_EMAIL);
        Recipient fromRecipient = new Recipient();
        fromRecipient.setEmailAddress(fromMail);
        List<Recipient> fromRecipients = new ArrayList<>();
        fromRecipients.add(fromRecipient);
        Mockito.when(messageMock.getFrom()).thenReturn(fromRecipient);
        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);
        ItemBody messageBody = new ItemBody();
        messageBody.setContent(VALIDHTML);
        messageBody.setContentType(BodyType.Html);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getReceivedDateTime()).thenReturn(calReceive.getTime().toInstant().atOffset(ZoneOffset.UTC));
        Mockito.when(messageMock.getSentDateTime()).thenReturn(calSent.getTime().toInstant().atOffset(ZoneOffset.UTC));

        String itemId = new ItemId(UNIQUE_ID).toString();

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        Mockito.when(messageMock.getFrom()).thenThrow(DpsMSGraphException.class);

        Assertions.assertThrows(DpsMSGraphException.class, () ->{
            DpsMetadata result = sut.map(messageMock, new DpsFileInfo(ID, NAME, CONTENT_TYPE), TENANT);
        });

    }

    @Test
    public void withValidTextEmailShouldReturnMetadata() throws Exception {

        Calendar calSent = Calendar.getInstance();
        calSent.set(2020,1,1);

        Calendar calReceive = Calendar.getInstance();
        calReceive.set(2020,2,2);

        EmailAddress toMail = new EmailAddress();
        toMail.setAddress(RECIPIENT_EMAIL);
        Recipient toRecipient = new Recipient();
        toRecipient.setEmailAddress(toMail);
        List<Recipient> toRecipients = new ArrayList<>();
        toRecipients.add(toRecipient);
        Mockito.when(messageMock.getToRecipients()).thenReturn(toRecipients);

        Mockito.when(messageMock.getSubject()).thenReturn(SUBJECT);

        EmailAddress fromMail = new EmailAddress();
        fromMail.setAddress(FROM_EMAIL);
        Recipient fromRecipient = new Recipient();
        fromRecipient.setEmailAddress(fromMail);
        List<Recipient> fromRecipients = new ArrayList<>();
        fromRecipients.add(fromRecipient);
        Mockito.when(messageMock.getFrom()).thenReturn(fromRecipient);

        ItemBody messageBody = new ItemBody();
        messageBody.setContent(VALIDHTML);
        messageBody.setContentType(BodyType.Html);
        Mockito.when(messageMock.getBody()).thenReturn(messageBody);

        Mockito.when(messageMock.getReceivedDateTime()).thenReturn(calReceive.getTime().toInstant().atOffset(ZoneOffset.UTC));
        Mockito.when(messageMock.getSentDateTime()).thenReturn(calSent.getTime().toInstant().atOffset(ZoneOffset.UTC));

        String itemId = new ItemId(UNIQUE_ID).toString();

        Mockito.when(messageMock.getId()).thenReturn(itemId);

        DpsMetadata result = sut.map(messageMock, new DpsFileInfo(ID, NAME, CONTENT_TYPE) ,TENANT);

        Assertions.assertEquals(TENANT, result.getApplicationId());
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



