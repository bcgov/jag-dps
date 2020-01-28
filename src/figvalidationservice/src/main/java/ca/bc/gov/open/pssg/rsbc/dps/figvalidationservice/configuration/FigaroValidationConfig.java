package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidation;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FigaroValidationConfig {

    @Bean
    public FigaroValidation figaroValidation() {
        return new FigaroValidationImpl();
    }


}
