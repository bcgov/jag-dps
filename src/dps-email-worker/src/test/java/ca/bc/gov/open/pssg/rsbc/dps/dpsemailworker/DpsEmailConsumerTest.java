package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailProcessedResponse;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import com.sun.jndi.toolkit.dir.SearchFilter;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@DisplayName("DpsEmailConsumer test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DpsEmailConsumerTest {

    public static final String CASE_1 = "case1";
    public static final String CASE_2 = "case2";
    public static final String EMAIL_EXCEPTION = "email exception";
    public static final String CORRELATION = "correlation";

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
    private DpsEmailProcessedResponse dpsEmailProcessedResponseMock;

    @BeforeAll
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

//        DpsFileInfo dpsFileInfoMock = new DpsFileInfo("id", "name", "String");

        Mockito.when(dpsMetadataMock.getFileInfo()).thenReturn(dpsFileInfoMock);
        Mockito.when(dpsFileInfoMock.getId()).thenReturn("id");

        Mockito.when(dpsEmailServiceMock.dpsEmailProcessed(Mockito.eq(CASE_1), Mockito.anyString())).thenReturn(dpsEmailProcessedResponseMock);

        sut = new DpsEmailConsumer(dpsEmailServiceMock, storageServiceMock);
    }

    @DisplayName("success - with email processed should return acknowledge")
    @Test
    public void withEmailProcessedShouldReturnSuccess() {

        Assertions.assertDoesNotThrow(() -> {
            sut.receiveMessage(new DpsMetadata.Builder().withApplicationID(CASE_1).build());
        });
    }

}

