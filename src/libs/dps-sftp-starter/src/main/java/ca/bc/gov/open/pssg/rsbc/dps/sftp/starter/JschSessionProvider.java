package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class JschSessionProvider {


    private final JSch jSch;
    private final SftpProperties sftpProperties;

    public JschSessionProvider(JSch jSch, SftpProperties sftpProperties) {
        this.jSch = jSch;
        this.sftpProperties = sftpProperties;
    }


    public Session getSession() throws JSchException {
        return doGetSession();
    }


    private Session doGetSession() throws JSchException {

        Session session = jSch.getSession(sftpProperties.getUsername(), sftpProperties.getHost());

        if(StringUtils.isBlank(sftpProperties.getSshPrivateKey()) && StringUtils.isNotBlank(sftpProperties.getPassword())) {
            session.setPassword(sftpProperties.getPassword());
        }

        session.connect();

        return session;
    }

    public void closeSession(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

}
