package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.configuration;

import ca.bc.gov.open.ords.dfcms.client.api.DfcrmsApi;
import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * Ords Dfcms Configuration
 *
 * Inject the DefaultApi in your class to start interacting with ORDS dfcms Web Service
 *
 * @author alexjoybc@github
 *
 */
@Configuration
@EnableConfigurationProperties(OrdsDfcmsProperties.class)
public class OrdsDfcmsConfig {

    private OrdsDfcmsProperties ordsDfcmsProperties;

    public OrdsDfcmsConfig(OrdsDfcmsProperties ordsDfcmsProperties) {
        this.ordsDfcmsProperties = ordsDfcmsProperties;
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(ordsDfcmsProperties.getBasePath());

        if(StringUtils.isNotBlank(ordsDfcmsProperties.getUsername()))
            apiClient.setUsername(ordsDfcmsProperties.getUsername());

        if(StringUtils.isNotBlank(ordsDfcmsProperties.getPassword()))
            apiClient.setPassword(ordsDfcmsProperties.getPassword());

        return apiClient;
    }

    @Bean
    public DfcrmsApi defaultApi(ApiClient apiClient) {
        return new DfcrmsApi(apiClient);
    }

}
