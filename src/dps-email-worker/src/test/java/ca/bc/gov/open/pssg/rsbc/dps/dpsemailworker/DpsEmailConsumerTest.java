package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailProcessedResponse;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.junit.jupiter.api.*;
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
    private static final String EMAIL_EXCEPTION = "email exception";
    private static final String CORRELATION = "correlation";
    private static final String FAKE_CONTENT = "fake content";
    private static final String FILE_NAME = "test.txt";
    private static final String REMOTE_LOCATION = "anyfolder";

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
    private DpsEmailProcessedResponse dpsEmailProcessedResponseMock;

    @BeforeAll
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Mockito.when(storageServiceMock.get(CASE_1)).thenReturn(FAKE_CONTENT.getBytes());

        Mockito.when(dpsMetadataMock.getFileInfo()).thenReturn(dpsFileInfoMock);
        Mockito.when(dpsFileInfoMock.getId()).thenReturn("id");

        Mockito.when(dpsEmailServiceMock.dpsEmailProcessed(Mockito.eq(CASE_1), Mockito.anyString())).thenReturn(dpsEmailProcessedResponseMock);

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        sut = new DpsEmailConsumer(dpsEmailServiceMock, storageServiceMock, fileServiceMock, sftpProperties);
    }

    @DisplayName("success - with email processed should return acknowledge")
    @Test
    public void withEmailProcessedShouldReturnSuccess() {

        Assertions.assertDoesNotThrow(() -> {
            sut.receiveMessage(new DpsMetadata.Builder().withApplicationID(CASE_1).withFileInfo(new DpsFileInfo(CASE_1, FILE_NAME, "String")).withEmailId("a@a.com").build());
        });

        String expectedRemoteFileName = MessageFormat.format("{0}/{1}", REMOTE_LOCATION, FILE_NAME);

        Mockito.verify(fileServiceMock, Mockito.times(1))
                .uploadFile(Mockito.any(InputStream.class), Mockito.eq(expectedRemoteFileName));



    }

}

