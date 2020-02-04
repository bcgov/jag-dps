package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.ords.client;

import ca.bc.gov.open.ords.figcr.client.api.DocumentApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiClient;
import io.micrometer.core.instrument.util.StringUtils;
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

}
