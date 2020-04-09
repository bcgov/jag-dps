package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class AutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(AutoConfiguration.class);

    private final SftpProperties sftpProperties;

    public AutoConfiguration(SftpProperties sftpProperties) {
        this.sftpProperties = sftpProperties;
        logger.debug("SFTP Configuration: Host => [{}]", this.sftpProperties.getHost());
        logger.debug("SFTP Configuration: Port => [{}]", this.sftpProperties.getPort());
        logger.debug("SFTP Configuration: Username => [{}]", this.sftpProperties.getUsername());
        logger.debug("SFTP Configuration: Remote Directory => [{}]", this.sftpProperties.getRemoteLocation());
        logger.debug("SFTP Configuration: Known Host File => [{}]", this.sftpProperties.getKnownHostsFileName());
    }

    /**
     * Returns the Session object for connecting to the sftp server
     * @param sftpProperties
     * @return
     * @throws JSchException
     */
    @Bean
    public Session sftpSession(SftpProperties sftpProperties) throws JSchException {

        JSch jsch = new JSch();

        if(StringUtils.isNotBlank(sftpProperties.getKnownHostsFileName()))
            jsch.setKnownHosts(sftpProperties.getKnownHostsFileName());

        if(StringUtils.isNotBlank(sftpProperties.getSshPrivateKey())) {
            if(StringUtils.isNotBlank(sftpProperties.getSshPrivatePassphrase())) {
                jsch.addIdentity(sftpProperties.getSshPrivateKey(), sftpProperties.getSshPrivatePassphrase());
            } else {
                jsch.addIdentity(sftpProperties.getSshPrivateKey());
            }
        }

        Session jschSession = jsch.getSession(sftpProperties.getUsername(), sftpProperties.getHost());

        if(StringUtils.isBlank(sftpProperties.getSshPrivateKey()) && StringUtils.isNotBlank(sftpProperties.getPassword())) {
            jschSession.setPassword(sftpProperties.getPassword());
        }

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
