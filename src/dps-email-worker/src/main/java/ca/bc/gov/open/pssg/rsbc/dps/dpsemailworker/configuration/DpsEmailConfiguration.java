package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import ca.bc.gov.open.dps.email.client.api.DpsEmailProcessingApi;
import ca.bc.gov.open.dps.email.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DpsEmailConfiguration {

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();

        apiClient.setBasePath("http://localhost:12345/dpsemailpoller");

        return apiClient;
    }

    @Bean
    public DpsEmailProcessingApi dpsEmailProcessingApi(ApiClient apiClient) { return new DpsEmailProcessingApi(apiClient); }

    @Bean
    public DpsEmailService dpsEmailService(DpsEmailProcessingApi dpsEmailProcessingApi) {
        return new DpsEmailServiceImpl(dpsEmailProcessingApi);
    }

}
