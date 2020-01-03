package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author smillar
 *
 */
@Configuration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SwaggerConfig {
	
	@Value("${payment.service.api.version}")
	private String version;
	
	@Value("${payment.service.environment}")
	private String environment;
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("DPS Payment service API")
            .description("A Payment Service API")
            .license("")
            .licenseUrl("")
            .termsOfServiceUrl("")
            .version(version)
            .build();
    }
  
  @Bean
  public Docket customImplementation(){
	  
	  // Note: Enable of service based on environment name. 
	  
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
                  .apis(RequestHandlerSelectors.basePackage("ca.bc.gov.open.pssg.rsbc.dps.paymentservice"))
                  .paths(PathSelectors.any())
                  .build()
              .apiInfo(apiInfo())
              .enable(!environment.toUpperCase().contains("PROD"));
  }
}
