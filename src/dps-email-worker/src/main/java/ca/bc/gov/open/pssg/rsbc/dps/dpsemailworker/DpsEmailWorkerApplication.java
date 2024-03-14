package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker", "ca.bc.gov.open.pssg.rsbc.dps", "ca.bc.gov.open.pssg.rsbc.dps.sftp.starter"})
public class DpsEmailWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpsEmailWorkerApplication.class, args);
	}

}
