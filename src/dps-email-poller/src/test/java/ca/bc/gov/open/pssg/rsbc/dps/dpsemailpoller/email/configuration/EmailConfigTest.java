package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration.ExchangeServiceFactory;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmailConfigTest {


    public static final String CRON = "*/5 * * * * ?";
    private EmailConfig sut;

    @Mock
    private ExchangeServiceFactory exchangeServiceFactoryMock;

    @Mock
    private DpsEmailParser dpsEmailParserMock;

    @BeforeAll
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        EmailProperties emailProperties = new EmailProperties();

        emailProperties.setCron(CRON);
        emailProperties.setEmailsPerBatch(0);
        emailProperties.setErrorFolder("errorFolder");
        emailProperties.setProcessedFolder("processedFolder");
        emailProperties.setProcessingFolder("processingFolder");

        Assertions.assertEquals(CRON, emailProperties.getCron());

        sut = new EmailConfig(emailProperties);

    }

    @Test
    public void shoulReturnAnEmailServiceImpl() {
        EmailService service = sut.emailService(exchangeServiceFactoryMock);
        Assertions.assertEquals(EmailServiceImpl.class, service.getClass());
    }

    @Test
    public void shoulReturnAnEmailParserImpl() {
        DpsEmailParser service = sut.dpsEmailParser();
        Assertions.assertEquals(DpsEmailParserImpl.class, service.getClass());
    }

    @Test
    public void shouldReturnAnEmailMapperImpl() {
        DpsMetadataMapper service = sut.dpsMetadataMapper(dpsEmailParserMock);
        Assertions.assertEquals(DpsMetadataMapperImpl.class, service.getClass());
    }


}
