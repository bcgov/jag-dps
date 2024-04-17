package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The DpsnotificationserviceApplication is a jag microservice that dispatch output notification to appropriate workers.
 *
 * @author alexjoybc@github
 *
 */
@SpringBootApplication(scanBasePackages = {"ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice", "ca.bc.gov.open.pssg.rsbc.dps.messaging"})
public class DpsnotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpsnotificationserviceApplication.class, args);
	}

}
