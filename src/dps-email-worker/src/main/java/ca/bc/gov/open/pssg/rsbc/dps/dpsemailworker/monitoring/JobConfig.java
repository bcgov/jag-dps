package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class JobConfig {

    @Bean
    public ExecutorService jobExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public MonitoringJob errorMonitoringJob(FileService fileService, ImportSessionService importSessionService, SftpProperties sftpProperties, KofaxProperties kofaxProperties, TenantProperties tenantProperties) {
        return new ErrorMonitoringJob(fileService, importSessionService, kofaxProperties, sftpProperties,
                tenantProperties);
    }

}
