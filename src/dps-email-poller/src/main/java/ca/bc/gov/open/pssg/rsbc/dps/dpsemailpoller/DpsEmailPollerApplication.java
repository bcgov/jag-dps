package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller", "ca.bc.gov.open.pssg.rsbc.dps.cache"})
@EnableScheduling
public class DpsEmailPollerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpsEmailPollerApplication.class, args);
    }

}
