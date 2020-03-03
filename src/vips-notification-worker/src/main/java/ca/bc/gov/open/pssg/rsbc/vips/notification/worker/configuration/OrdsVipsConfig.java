package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.configuration;

import ca.bc.gov.open.ords.vips.client.api.DocumentApi;
import ca.bc.gov.open.ords.vips.client.api.HealthApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.document.DocumentServiceImpl;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.health.HealthService;
import ca.bc.gov.open.pssg.rsbc.vips.ords.client.health.HealthServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Ords Vips Configuration
 *
 *
 */
@Configuration
@EnableConfigurationProperties(OrdsVipsProperties.class)
public class OrdsVipsConfig {

    private final OrdsVipsProperties ordsVipsProperties;

    public OrdsVipsConfig(OrdsVipsProperties ordsVipsProperties) {
        this.ordsVipsProperties = ordsVipsProperties;
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        
        apiClient.setBasePath(ordsVipsProperties.getBasepath());

        if(StringUtils.isNotBlank(ordsVipsProperties.getUsername()))
            apiClient.setUsername(ordsVipsProperties.getUsername());

        if(StringUtils.isNotBlank(ordsVipsProperties.getPassword()))
            apiClient.setPassword(ordsVipsProperties.getPassword());

        return apiClient;
    }

    @Bean
    public DocumentApi documentApi(ApiClient apiClient) { return new DocumentApi(apiClient); }

    @Bean
    public DocumentService documentService(DocumentApi documentApi) {
        return new DocumentServiceImpl(documentApi);
    }

    @Bean
    public HealthApi healthApi(ApiClient apiClient) { return new HealthApi(apiClient); }

    @Bean
    public HealthService healthService(HealthApi healthApi) { return new HealthServiceImpl(healthApi); }
}
