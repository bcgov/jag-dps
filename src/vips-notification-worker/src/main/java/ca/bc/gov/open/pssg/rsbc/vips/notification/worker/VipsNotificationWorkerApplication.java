package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ca.bc.gov.open.pssg.rsbc.vips.notification.worker", "ca.bc.gov.open.jagvipsclient", "ca.bc.gov.open.pssg.rsbc.dps.sftp.starter"})
public class VipsNotificationWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VipsNotificationWorkerApplication.class, args);
	}

}
