package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.configuration;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.FacilityApi;
import ca.bc.gov.open.ords.figcr.client.api.HealthApi;
import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiClient;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantServiceImpl;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.FacilityService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.FacilityServiceImpl;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.health.HealthService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.health.HealthServiceImpl;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.OrganizationService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.OrganizationServiceImpl;
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
    public FacilityApi facilityApi(ApiClient apiClient) { return new FacilityApi(apiClient); }

    @Bean
    public ApplicantApi applicantApi(ApiClient apiClient) { return new ApplicantApi(apiClient); }

    @Bean
    public OrgApi orgApi(ApiClient apiClient) { return new OrgApi(apiClient); }

    @Bean
    public FacilityService facilityService(FacilityApi facilityApi) {
        return new FacilityServiceImpl(facilityApi);
    }

    @Bean
    public ApplicantService applicantService(ApplicantApi applicantApi) {
        return new ApplicantServiceImpl(applicantApi);
    }

    @Bean
    public OrganizationService orgService(OrgApi orgApi) {
        return new OrganizationServiceImpl(orgApi);
    }

    @Bean
    public HealthApi healthApi(ApiClient apiClient) { return new HealthApi(apiClient); }

    @Bean
    public HealthService healthService(HealthApi healthApi) { return new HealthServiceImpl(healthApi); }
}
