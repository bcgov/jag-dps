package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class AutoConfiguration {


    /**
     * Returns the Session object for connecting to the sftp server
     * @param sftpProperties
     * @return
     * @throws JSchException
     */
    @Bean
    public Session sftpSession(SftpProperties sftpProperties) throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(sftpProperties.getKnownHosts());
        Session jschSession = jsch.getSession(sftpProperties.getUsername(), sftpProperties.getHost());
        jschSession.setPassword(sftpProperties.getPassword());
        jschSession.connect();
        return jschSession;
    }

    /**
     * Returns the sftp service implementation
     * @param sftpSession
     * @return
     */
    @Bean
    public SftpService sftpService(Session sftpSession) {
        return new SftpServiceImpl(sftpSession);
    }


}
