package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OtssoaProperties.class)
public class AutoConfiguration {

    private final OtssoaProperties ottsoaProperties;

    public AutoConfiguration(OtssoaProperties ottsoaProperties) {
        this.ottsoaProperties = ottsoaProperties;
    }

    @Bean(name = "otssoaApiClient")
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();

        apiClient.setBasePath(ottsoaProperties.getBasePath());

        if(StringUtils.isNotBlank(ottsoaProperties.getUsername()))
            apiClient.setUsername(ottsoaProperties.getUsername());

        if(StringUtils.isNotBlank(ottsoaProperties.getPassword()))
            apiClient.setPassword(ottsoaProperties.getPassword());

        return apiClient;
    }

    @Bean
    public OtssoaApi otssoaApi(@Qualifier("otssoaApiClient") ApiClient apiClient) {
        return new OtssoaApi(apiClient);
    }

    @Bean
    public OtssoaService otssoaService(OtssoaApi otssoaApi) {
        return new OtssoaServiceImpl(otssoaApi);
    }

}
