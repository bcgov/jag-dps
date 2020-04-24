package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

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

}
