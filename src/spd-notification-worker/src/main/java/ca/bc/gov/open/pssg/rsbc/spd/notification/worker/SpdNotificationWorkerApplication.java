package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ca.bc.gov.open.pssg.rsbc.spd.notification.worker", "ca.bc.gov.open.pssg.rsbc.dps.sftp.starter"})
public class SpdNotificationWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpdNotificationWorkerApplication.class, args);
	}

}
