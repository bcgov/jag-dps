package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.notification.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.FileServiceImpl;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfiguration {

    @Bean
    public FileService fileService(SftpService sftpService) {
        return new FileServiceImpl(sftpService);
    }

}
