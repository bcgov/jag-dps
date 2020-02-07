package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.sftp.SftpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationConsumerTest {

    public static final String CASE_1 = "case1";
    private OutputNotificationConsumer sut;

    @Mock
    private SftpService sftpServiceMock;

    private ByteArrayInputStream fakeContent() {
        String content = "fake content";
        return new ByteArrayInputStream(content.getBytes());
    }

    @BeforeAll
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.when(sftpServiceMock.getContent(Mockito.eq(CASE_1))).thenReturn(fakeContent());

        sut = new OutputNotificationConsumer(sftpServiceMock);
    }

    @Test
    public void withAMessageShouldProcess() {

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, fileId);
            message.AddFile(CASE_1);
            sut.receiveMessage(message);
        });

    }


}
