import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpServiceImpl;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SftpServiceImplTest {

    public static final String CASE_1 = "case1";
    public static final String FAKE_INPUT_STREAM = "fake input stream";
    public static final String CASE_2 = "case2";
    public static final String CASE_3 = "case3";
    public static final String CASE_4 = "case4";

    public static final String FILE_1 = "file1";
    public static final String FILE_2 = "file2";

    @Mock
    private Session sessionMock;

    @Mock
    private ChannelSftp channelSftpMock;

    private SftpServiceImpl sut;

    private InputStream fakeInputStream() {

        String result = FAKE_INPUT_STREAM;

        return new ByteArrayInputStream(result.getBytes());
    }

    @BeforeEach
    public void setUp() throws JSchException, SftpException {

        MockitoAnnotations.initMocks(this);

        Mockito.when(sessionMock.openChannel(Mockito.eq("sftp"))).thenReturn(channelSftpMock);

        Mockito.when(channelSftpMock.get(CASE_1)).thenReturn(fakeInputStream());
        Mockito.when(channelSftpMock.get(CASE_3)).thenThrow(SftpException.class);
        Mockito.when(channelSftpMock.get(CASE_4)).thenReturn(null);

        Mockito.when(channelSftpMock.get(FILE_1)).thenReturn(fakeInputStream());
        Mockito.when(channelSftpMock.get(FILE_2)).thenReturn(fakeInputStream());

        sut = new SftpServiceImpl(sessionMock);
    }

    @Test
    public void withValidContentShouldReturnContent() {

        ByteArrayInputStream result = sut.getContent(CASE_1);

        int n = result.available();
        byte[] bytes = new byte[n];
        result.read(bytes, 0, n);

        Assertions.assertEquals(FAKE_INPUT_STREAM, new String(bytes));
    }

    @Test
    public void withJSchExceptionShouldThrowDpsSftpException() throws JSchException {
        Mockito.when(sessionMock.openChannel(Mockito.eq("sftp"))).thenThrow(JSchException.class);
        Assertions.assertThrows(DpsSftpException.class, () -> {
            ByteArrayInputStream result = sut.getContent(CASE_2);
        });
    }

    @Test
    public void withSftpExceptionShouldThrowDpsSftpException() {
        Assertions.assertThrows(DpsSftpException.class, () -> {
            ByteArrayInputStream result = sut.getContent(CASE_3);
        });
    }

    @Test
    public void withValidFileShouldMove() {
        sut.moveFile(FILE_1, FILE_2);
    }

}
