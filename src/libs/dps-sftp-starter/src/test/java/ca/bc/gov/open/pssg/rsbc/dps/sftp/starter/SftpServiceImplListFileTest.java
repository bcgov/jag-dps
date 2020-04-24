package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Vector;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SftpServiceImplListFileTest {

    public static final String FILENAME_1 = "filename1";
    public static final String FILENAME_2 = "filename2";
    private static String CASE_1 = "case1";


    @Mock
    private JschSessionProvider jschSessionProviderMock;

    @Mock
    private Session sessionMock;

    @Mock
    private ChannelSftp channelSftpMock;

    @Mock
    private ChannelSftp.LsEntry lsEntry1Mock;

    @Mock
    private ChannelSftp.LsEntry lsEntry2Mock;

    private SftpServiceImpl sut;



    @BeforeEach
    public void setUp() throws JSchException, SftpException {

        MockitoAnnotations.initMocks(this);

        Mockito.when(sessionMock.openChannel(Mockito.eq("sftp"))).thenReturn(channelSftpMock);
        Mockito.when(jschSessionProviderMock.getSession()).thenReturn(sessionMock);

        Mockito.when(lsEntry1Mock.getFilename()).thenReturn(FILENAME_1);
        Mockito.when(lsEntry2Mock.getFilename()).thenReturn(FILENAME_2);

        Vector<ChannelSftp.LsEntry> fakeList = new Vector<>();
        fakeList.add(lsEntry1Mock);
        fakeList.add(lsEntry2Mock);

        Mockito.when(channelSftpMock.ls(CASE_1)).thenReturn(fakeList);
        Mockito.when(channelSftpMock.isConnected()).thenReturn(true);

        sut = new SftpServiceImpl(jschSessionProviderMock);
    }

    @Test
    @DisplayName("Success - Test with valid remote file name list")
    public void withReturnedListShouldReturnAListOfFileName() {

        List<String> actual =  sut.listFiles(CASE_1);

        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.contains(FILENAME_1));
        Assertions.assertTrue(actual.contains(FILENAME_2));

    }


}
