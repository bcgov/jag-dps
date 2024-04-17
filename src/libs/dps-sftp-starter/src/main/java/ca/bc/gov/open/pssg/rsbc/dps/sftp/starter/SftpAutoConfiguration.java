package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties(SftpProperties.class)
public class SftpAutoConfiguration {

    public static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    public static final String NO = "no";
    private Logger logger = LoggerFactory.getLogger(SftpAutoConfiguration.class);

    private final SftpProperties sftpProperties;

    public SftpAutoConfiguration(SftpProperties sftpProperties) {
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
    public JSch sftpSession(SftpProperties sftpProperties) throws JSchException {

        JSch jsch = new JSch();

        if(StringUtils.isNotBlank(sftpProperties.getKnownHostsFileName())) {
            jsch.setKnownHosts(sftpProperties.getKnownHostsFileName());
        } else {
            logger.warn("YOU SHOULD SET THE KNOWN HOSTS VALUE IN PRODUCTION");
            jsch.setConfig(STRICT_HOST_KEY_CHECKING, NO);
        }

        if(StringUtils.isNotBlank(sftpProperties.getSshPrivateKey())) {
            if(StringUtils.isNotBlank(sftpProperties.getSshPrivatePassphrase())) {
                logger.debug("Adding private key and passphrase identity");
                jsch.addIdentity(sftpProperties.getSshPrivateKey(), sftpProperties.getSshPrivatePassphrase());
            } else {
                logger.debug("Adding private key identity");
                jsch.addIdentity(sftpProperties.getSshPrivateKey());
            }
        }

        return jsch;
    }

    /**
     * Returns the sftp service implementation
     * @param jschSessionProvider
     * @return
     */
    @Bean
    public SftpService sftpService(JschSessionProvider jschSessionProvider) {
        return new SftpServiceImpl(jschSessionProvider);
    }


}
