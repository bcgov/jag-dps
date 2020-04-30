package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.Batch;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration.RegistrationService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailProcessedResponse;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.text.MessageFormat;

@DisplayName("DpsEmailConsumer test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsEmailConsumerTest {

    private static final String CASE_1 = "case1";
    private static final String CASE_2 = "case2";
    private static final String CASE_3 = "case3";
    private static final String EMAIL_EXCEPTION = "email exception";
    private static final String CORRELATION = "correlation";
    private static final String FAKE_CONTENT = "fake content";
    private static final String FILE_NAME = "test.txt";
    private static final String REMOTE_LOCATION = "anyfolder";
    public static final String EMAIL_ID = "123456";

    private DpsEmailConsumer sut;

    @Mock
    private DpsEmailService dpsEmailServiceMock;

    @Mock
    private StorageService storageServiceMock;

    @Mock
    DpsMetadata dpsMetadataMock;

    @Mock
    DpsFileInfo dpsFileInfoMock;

    @Mock
    FileService fileServiceMock;

    @Mock
    ImportSessionService importSessionService;

    @Mock
    private DpsEmailProcessedResponse dpsEmailProcessedResponseMock;

    @Mock
    private DpsEmailProcessedResponse dpsEmailFailedResponseMock;

    @Mock
    private RegistrationService registrationServiceMock;

    @BeforeAll
    public void setUp() throws Exception {

        ImportSession fakeSession = new ImportSession("user", "password", "", "");

        Batch fakeBatch =
                new Batch.Builder()
                        .withBatchClassName("test")
                        .withEnableAutomaticSeparationAndFormID("1")
                        .withInputChannel("FAX")
                        .withRelativeImageFilePath(".").build();

        fakeSession.getBatches().addBatch(fakeBatch);

        MockitoAnnotations.initMocks(this);

        Mockito.when(storageServiceMock.get(Mockito.eq(CASE_1))).thenReturn(FAKE_CONTENT.getBytes());
        Mockito.doNothing().when(storageServiceMock).delete(Mockito.eq(CASE_1));

        Mockito.when(storageServiceMock.get(Mockito.eq(CASE_3))).thenReturn(FAKE_CONTENT.getBytes());
        Mockito.doNothing().when(storageServiceMock).delete(Mockito.eq(CASE_3));

        Mockito.when(dpsMetadataMock.getFileInfo()).thenReturn(dpsFileInfoMock);
        Mockito.when(dpsFileInfoMock.getId()).thenReturn("id");

        Mockito.when(dpsEmailServiceMock.dpsEmailProcessed(Mockito.eq(CASE_1), Mockito.anyString())).thenReturn(dpsEmailProcessedResponseMock);
        Mockito.when(dpsEmailServiceMock.dpsEmailFailed(Mockito.anyString(), Mockito.anyString())).thenReturn(dpsEmailFailedResponseMock);
        Mockito.when(dpsEmailFailedResponseMock.isAcknowledge()).thenReturn(true).thenReturn(false);

        Mockito.when(importSessionService.generateImportSession(Mockito.any(DpsMetadata.class))).thenReturn(fakeSession);
        Mockito.when(importSessionService.convertToXmlBytes(Mockito.any(ImportSession.class))).thenReturn(("<test" +
                "></test>").getBytes());

        DpsEmailProcessedResponse response = DpsEmailProcessedResponse.successResponse(true, "test");
        Mockito.when(dpsEmailServiceMock.dpsEmailProcessed(Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        sut = new DpsEmailConsumer(dpsEmailServiceMock, storageServiceMock, fileServiceMock, sftpProperties,
                importSessionService, registrationServiceMock);
    }

    @DisplayName("success - with email processed should return acknowledge")
    @Test
    public void withEmailProcessedShouldReturnSuccess() {

        DpsMetadata input = new DpsMetadata
                .Builder()
                .withApplicationID(CASE_1)
                .withFileInfo(new DpsFileInfo(CASE_1, FILE_NAME, "String"))
                .withEmailId(EMAIL_ID).build();


        Assertions.assertDoesNotThrow(() -> sut.receiveMessage(input));

        String expectedRemoteFileName = MessageFormat.format("{0}/{1}", REMOTE_LOCATION, FILE_NAME);

        Mockito.verify(fileServiceMock, Mockito.times(1))
                .uploadFile(Mockito.any(InputStream.class), Mockito.eq(expectedRemoteFileName));

        Mockito.verify(fileServiceMock, Mockito.times(2))
                .uploadFile(Mockito.any(InputStream.class), ArgumentMatchers.endsWith(".xml"));

        Mockito.verify(storageServiceMock, Mockito.times(1))
                .delete(Mockito.eq(CASE_1));

        Mockito.verify(dpsEmailServiceMock, Mockito.times(1))
                .dpsEmailProcessed(Mockito.eq(input.getBase64EmailId()), Mockito.eq(input.getTransactionId().toString()));
    }

    @DisplayName("error - with missing batch name should return error")
    @Test
    public void withMissingBatchNameShouldThrowError() {

        ImportSession fakeSession = new ImportSession("fake", "session", "", "");

        Mockito.when(importSessionService.generateImportSession(Mockito.any(DpsMetadata.class))).thenReturn(fakeSession);

        Assertions.assertThrows(DpsEmailWorkerException.class, () -> {
            sut.receiveMessage(new DpsMetadata.Builder().withApplicationID(CASE_1).withFileInfo(new DpsFileInfo(CASE_1, FILE_NAME, "String")).withEmailId("a@a.com").build());
        });

    }

    @DisplayName("success - receiveParkedMessage should not throw")
    @Test
    public void withEmailFailedShouldReturnSuccess() {

        Assertions.assertDoesNotThrow(() -> {
            sut.receiveParkedMessage(new DpsMetadata.Builder().withApplicationID(CASE_3).withFileInfo(new DpsFileInfo(CASE_3, FILE_NAME, "String")).withEmailId("a@a.com").build());
        });

        Mockito.verify(storageServiceMock, Mockito.times(1))
                .delete(Mockito.eq(CASE_3));

        // Second call to receiveParkedMessage should not have the isAcknowledge() == true, so should not delete
        Assertions.assertDoesNotThrow(() -> {
            sut.receiveParkedMessage(new DpsMetadata.Builder().withApplicationID(CASE_3).withFileInfo(new DpsFileInfo(CASE_3, FILE_NAME, "String")).withEmailId("a@a.com").build());
        });

        Mockito.verify(storageServiceMock, Mockito.times(1))
                .delete(Mockito.eq(CASE_3));
    }
}

