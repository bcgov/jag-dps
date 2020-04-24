package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DpsEmailWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpsEmailWorkerApplication.class, args);
	}

}
