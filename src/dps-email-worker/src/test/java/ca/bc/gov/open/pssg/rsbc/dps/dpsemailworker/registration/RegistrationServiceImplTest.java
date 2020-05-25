package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.Keys;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceImplTest {

    // case are defined by the originating number
    private static final String CASE_1 = "case1";
    private static final String CASE_2 = "case2";
    private static final String CASE_3 = "case3";
    private static final String CASE_4 = "case4";

    private static final String APPLICATION_ID = "applicationId";
    private static final String EMAIL_ID = "emailId";
    private static final String CHANNEL_TYPE = "channelType";
    private static final String DESTINATION_NUMBER = "destinationNumber";
    private static final String TO = "to";
    private static final int NUMBER_OF_PAGES = 10;
    private static final String CHANNEL_ID = "channel Id";
    private static final String FROM = "from";
    private static final String JOB_ID = "jobId";
    private static final String INBOUND = "inbound";
    private static final String BODY = "body";
    private static final String SUBJECT = "subject";
    private static final String FILE_ID = "id";
    private static final String FILE_NAME = "name.txt";
    private static final String EMPTY_STRING = "";
    private static final String SUCCESS_STATUS = "0";
    private static final String API_EXCEPTION = "api exception";
    private static final String ERROR_MESSAGE = "error message";
    private static final String ERROR_STATUS = "-1";

    private String FILE_CONTENT_TYPE = "contenttype";


    private RegistrationServiceImpl sut;

    @Mock
    private OtssoaService otssoaService;

    @BeforeAll
    public void setUp() throws ApiException {

        DefaultResponse success = new DefaultResponse();
        success.setRegState(SUCCESS_STATUS);
        success.setErrorMessage(EMPTY_STRING);

        DefaultResponse error = new DefaultResponse();
        error.setRegState(ERROR_STATUS);
        error.setErrorMessage(ERROR_MESSAGE);

        DefaultResponse emptyresponse = new DefaultResponse();

        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(success)
                .when(otssoaService)
                .createPackage(ArgumentMatchers.argThat(x -> x.getSource().equals(CASE_1)));

        Mockito.doThrow(new ApiException(API_EXCEPTION))
                .when(otssoaService)
                .createPackage(ArgumentMatchers.argThat(x -> x.getSource().equals(CASE_2)));

        Mockito.doReturn(error)
                .when(otssoaService)
                .createPackage(ArgumentMatchers.argThat(x -> x.getSource().equals(CASE_3)));

        Mockito.doReturn(emptyresponse)
                .when(otssoaService)
                .createPackage(ArgumentMatchers.argThat(x -> x.getSource().equals(CASE_4)));


        sut = new RegistrationServiceImpl(otssoaService);
    }


    @Test
    public void withValidDpsMetadataShouldCreateAPackage() throws ApiException {

        FILE_CONTENT_TYPE = "txt";
        DpsMetadata dpsMetadata = new DpsMetadata
                .Builder()
                .withApplicationID(APPLICATION_ID)
                .withEmailId(EMAIL_ID)
                .withFileInfo(new DpsFileInfo(FILE_ID, FILE_NAME, FILE_CONTENT_TYPE))
                .withInboundChannelType(CHANNEL_TYPE)
                .withRecvdate(new Date())
                .withOriginatingNumber(CASE_1)
                .withSubject(SUBJECT)
                .withBody(BODY)
                .withDirection(INBOUND)
                .withFaxJobID(JOB_ID)
                .withFrom(FROM)
                .withInboundChannelID(CHANNEL_ID)
                .withNumberOfPages(NUMBER_OF_PAGES)
                .withSentdate(new Date())
                .withTo(TO)
                .withDestinationNumber(DESTINATION_NUMBER)
                .build();

        Assertions.assertDoesNotThrow(() -> sut.registerPackage(dpsMetadata));

        Mockito.verify(otssoaService, Mockito.times(1))
                .createPackage(
                        ArgumentMatchers.argThat(x ->
                                x.getSource().equals(CASE_1) &&
                                        x.getBusinessArea().equals(Keys.REGISTRATION_BUSINESS_AREA) &&
                                        x.getFilename().equals(FILE_NAME) &&
                                        x.getFormatType().equals(Keys.REGISTRATION_DEFAULT_FORMAT) &&
                                        x.getImportGuid().equals(dpsMetadata.getTransactionId().toString()) &&
                                        x.getPageCount() == NUMBER_OF_PAGES &&
                                        StringUtils.isBlank(x.getProgramType()) &&
                                        x.getReceivedDate().equals(dpsMetadata.getReceivedDate()) &&
                                        x.getRecipient().equals(dpsMetadata.getTo()) &&
                                        x.getSource().equals(dpsMetadata.getOriginatingNumber())
                        ));

    }

    @Test
    public void withApiExceptionShouldDoNothingButLogAnError() {

        FILE_CONTENT_TYPE = "txt";
        DpsMetadata dpsMetadata = new DpsMetadata
                .Builder()
                .withApplicationID(APPLICATION_ID)
                .withEmailId(EMAIL_ID)
                .withFileInfo(new DpsFileInfo(FILE_ID, FILE_NAME, FILE_CONTENT_TYPE))
                .withInboundChannelType(CHANNEL_TYPE)
                .withRecvdate(new Date())
                .withOriginatingNumber(CASE_2)
                .withSubject(SUBJECT)
                .withBody(BODY)
                .withDirection(INBOUND)
                .withFaxJobID(JOB_ID)
                .withFrom(FROM)
                .withInboundChannelID(CHANNEL_ID)
                .withNumberOfPages(NUMBER_OF_PAGES)
                .withSentdate(new Date())
                .withTo(TO)
                .withDestinationNumber(DESTINATION_NUMBER)
                .build();

        Assertions.assertDoesNotThrow(() -> sut.registerPackage(dpsMetadata));

    }

    @Test
    public void withApiErrorShouldDoNothingButLogAnError() {

        FILE_CONTENT_TYPE = "txt";
        DpsMetadata dpsMetadata = new DpsMetadata
                .Builder()
                .withApplicationID(APPLICATION_ID)
                .withEmailId(EMAIL_ID)
                .withFileInfo(new DpsFileInfo(FILE_ID, FILE_NAME, FILE_CONTENT_TYPE))
                .withInboundChannelType(CHANNEL_TYPE)
                .withRecvdate(new Date())
                .withOriginatingNumber(CASE_3)
                .withSubject(SUBJECT)
                .withBody(BODY)
                .withDirection(INBOUND)
                .withFaxJobID(JOB_ID)
                .withFrom(FROM)
                .withInboundChannelID(CHANNEL_ID)
                .withNumberOfPages(NUMBER_OF_PAGES)
                .withSentdate(new Date())
                .withTo(TO)
                .withDestinationNumber(DESTINATION_NUMBER)
                .build();

        Assertions.assertDoesNotThrow(() -> sut.registerPackage(dpsMetadata));

    }

    @Test
    public void withEmptyResponseShouldDoNothingButLogAnError() {

        FILE_CONTENT_TYPE = "txt";
        DpsMetadata dpsMetadata = new DpsMetadata
                .Builder()
                .withApplicationID(APPLICATION_ID)
                .withEmailId(EMAIL_ID)
                .withFileInfo(new DpsFileInfo(FILE_ID, FILE_NAME, FILE_CONTENT_TYPE))
                .withInboundChannelType(CHANNEL_TYPE)
                .withRecvdate(new Date())
                .withOriginatingNumber(CASE_4)
                .withSubject(SUBJECT)
                .withBody(BODY)
                .withDirection(INBOUND)
                .withFaxJobID(JOB_ID)
                .withFrom(FROM)
                .withInboundChannelID(CHANNEL_ID)
                .withNumberOfPages(NUMBER_OF_PAGES)
                .withSentdate(new Date())
                .withTo(TO)
                .withDestinationNumber(DESTINATION_NUMBER)
                .build();

        Assertions.assertDoesNotThrow(() -> sut.registerPackage(dpsMetadata));

    }


    @Test
    public void serviceShouldBeActive() {
        Assertions.assertTrue(sut.isActive());
    }

}
