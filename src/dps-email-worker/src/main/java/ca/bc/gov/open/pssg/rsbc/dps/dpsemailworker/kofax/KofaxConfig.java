package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Configuration
@EnableConfigurationProperties({KofaxProperties.class})
public class KofaxConfig {

    private final KofaxProperties kofaxProperties;

    private final TenantProperties tenantProperties;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public KofaxConfig(KofaxProperties kofaxProperties, TenantProperties tenantProperties) {
        this.kofaxProperties = kofaxProperties;
        this.tenantProperties = tenantProperties;
    }

    @Bean
    public JAXBContext kofaxImportSessionJaxbContext() throws JAXBException {
        logger.info("Configuring kofax ImportSession JAXBContext");
        return JAXBContext.newInstance(ImportSession.class);
    }

    @Bean
    public ImportSessionService inboundService(@Qualifier("kofaxImportSessionJaxbContext")JAXBContext kofaxImportSessionJaxbContext) {
        return new ImportSessionServiceImpl(kofaxProperties, tenantProperties, kofaxImportSessionJaxbContext);
    }

}
