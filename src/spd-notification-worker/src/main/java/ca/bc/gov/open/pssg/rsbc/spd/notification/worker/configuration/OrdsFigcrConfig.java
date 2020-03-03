package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.configuration;

import ca.bc.gov.open.ords.figcr.client.api.DocumentApi;
import ca.bc.gov.open.ords.figcr.client.api.HealthApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.DocumentServiceImpl;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.health.HealthService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.health.HealthServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Ords FigvalidationsApi Configuration
 *
 * Inject the FigvalidationsApi in your class to start interacting with ORDS Figaro Web Service
 *
 * @author shaunmillargov
 *
 */
@Configuration
@EnableConfigurationProperties(OrdsFigcrProperties.class)
public class OrdsFigcrConfig {

    private final OrdsFigcrProperties ordsFigcrProperties;

    public OrdsFigcrConfig(OrdsFigcrProperties ordsFigcrProperties) {
        this.ordsFigcrProperties = ordsFigcrProperties;
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        
        apiClient.setBasePath(ordsFigcrProperties.getBasepath());

        if(StringUtils.isNotBlank(ordsFigcrProperties.getUsername()))
            apiClient.setUsername(ordsFigcrProperties.getUsername());

        if(StringUtils.isNotBlank(ordsFigcrProperties.getPassword()))
            apiClient.setPassword(ordsFigcrProperties.getPassword());

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
