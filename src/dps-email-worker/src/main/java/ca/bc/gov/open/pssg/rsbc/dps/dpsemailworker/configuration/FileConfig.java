package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;


import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileServiceImpl;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    @Bean
    public FileService fileService(SftpService sftpService) {
        return new FileServiceImpl(sftpService);
    }

}