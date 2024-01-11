package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
@Configuration
public class SwaggerConfig {
    @Value("${dpsvalidation.service.api.version}")
    private String version;

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("DPS Validation service API")
                        .description("A DPS Validation Service API")
                        .version(version));
    }
}
