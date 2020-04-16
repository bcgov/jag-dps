package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JschSessionProviderTest {

    public static final String HOST = "host";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    @Mock
    public Session sessionMock;

    @Mock
    public JSch jSchMock;

    public JschSessionProvider sut;

    @BeforeEach
    public void setUp() throws JSchException {

        MockitoAnnotations.initMocks(this);

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setHost(HOST);
        sftpProperties.setUsername(USERNAME);
        sftpProperties.setPassword(PASSWORD);

        Mockito.doNothing().when(sessionMock).connect();
        Mockito.doNothing().when(sessionMock).disconnect();
        Mockito.when(sessionMock.isConnected()).thenReturn(true);

        Mockito.when(sessionMock.getHost()).thenReturn(HOST);
        Mockito.when(sessionMock.getUserName()).thenReturn(USERNAME);
        Mockito.when(jSchMock.getSession(Mockito.eq(USERNAME), Mockito.eq(HOST))).thenReturn(sessionMock);

        sut = new JschSessionProvider(jSchMock, sftpProperties);

    }

    @Test
    public void itShouldGetASession() throws JSchException {

        Session result = sut.getSession();

        Assertions.assertEquals("host", result.getHost());
        Assertions.assertEquals("username", result.getUserName());

    }

    @Test
    public void isShouldCloseASession() throws JSchException {

        Session result = sut.getSession();

        sut.closeSession(result);

        Mockito.verify(sessionMock, Mockito.times(1)).disconnect();

    }

    @Test
    public void withNullSessionItShouldDoNothing() throws JSchException {

        sut.closeSession(null);

        Mockito.verify(sessionMock, Mockito.times(0)).disconnect();

    }


}
