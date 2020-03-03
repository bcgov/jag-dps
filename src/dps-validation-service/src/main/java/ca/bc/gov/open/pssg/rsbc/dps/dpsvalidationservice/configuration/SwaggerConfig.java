package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${dpsvalidation.service.api.version}")
    private String version;

    @Value("${dpsvalidation.service.swagger.enabled:false}")
    private Boolean enabled;

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DPS Validation service API")
                .description("A DPS Validation Service API")
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version(version)
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice"))
                .paths(PathSelectors.any())
                .build();
    }

}
