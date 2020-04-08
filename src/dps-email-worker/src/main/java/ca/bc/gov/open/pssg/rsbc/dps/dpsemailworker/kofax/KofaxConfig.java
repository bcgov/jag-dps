package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;

@Configuration
@EnableConfigurationProperties({KofaxProperties.class})
public class KofaxConfig {

    private final KofaxProperties kofaxProperties;

    private final TenantProperties tenantProperties;


    public KofaxConfig(KofaxProperties kofaxProperties, TenantProperties tenantProperties) {
        this.kofaxProperties = kofaxProperties;
        this.tenantProperties = tenantProperties;
    }

    @Bean
    public ImportSessionService inboundService(@Qualifier("kofaxImportSessionJaxbContext")JAXBContext kofaxImportSessionJaxbContext) {
        return new ImportSessionServiceImpl(kofaxProperties, tenantProperties, kofaxImportSessionJaxbContext);
    }


}
