package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${dpsemailpoller.api.version}")
    private String version;

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Email service API")
                        .description("Email Service API")
                        .version(version));
    }
}
