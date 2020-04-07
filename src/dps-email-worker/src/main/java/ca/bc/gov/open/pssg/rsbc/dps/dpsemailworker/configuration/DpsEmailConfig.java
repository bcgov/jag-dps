package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import ca.bc.gov.open.dps.email.client.api.DpsEmailProcessingApi;
import ca.bc.gov.open.dps.email.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DpsEmailProperties.class)
public class DpsEmailConfig {

    private final DpsEmailProperties dpsEmailProperties;

    public DpsEmailConfig(DpsEmailProperties dpsEmailProperties) {
        this.dpsEmailProperties = dpsEmailProperties;
    }

    @Bean
    public ApiClient apiClient() {

        ApiClient apiClient = new ApiClient();

        apiClient.setBasePath(dpsEmailProperties.getBasePath());

        return apiClient;
    }

    @Bean
    public DpsEmailProcessingApi dpsEmailProcessingApi(ApiClient apiClient) { return new DpsEmailProcessingApi(apiClient); }

    @Bean
    public DpsEmailService dpsEmailService(DpsEmailProcessingApi dpsEmailProcessingApi) {
        return new DpsEmailServiceImpl(dpsEmailProcessingApi);
    }

}
