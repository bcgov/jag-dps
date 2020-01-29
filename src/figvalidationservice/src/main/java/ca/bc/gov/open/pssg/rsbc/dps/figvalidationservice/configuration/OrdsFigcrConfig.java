package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.configuration;

import ca.bc.gov.open.ords.figcr.client.api.FacilityApi;
import ca.bc.gov.open.ords.figcr.client.api.FigvalidationsApi;
import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility.FacilityService;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility.FacilityServiceImpl;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org.OrgService;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org.OrgServiceImpl;
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
    public FigvalidationsApi defaultApi(ApiClient apiClient) {
        return new FigvalidationsApi(apiClient);
    }

    @Bean
    public FacilityApi facilityApi(ApiClient apiClient) { return new FacilityApi(apiClient); }

    @Bean
    public FacilityService facilityService(FacilityApi facilityApi) {
        return new FacilityServiceImpl(facilityApi);
    }

    @Bean
    public OrgApi orgApi(ApiClient apiClient) { return new OrgApi(apiClient); }

    @Bean
    public OrgService orgService(OrgApi orgApi) {
        return new OrgServiceImpl(orgApi);
    }
}
