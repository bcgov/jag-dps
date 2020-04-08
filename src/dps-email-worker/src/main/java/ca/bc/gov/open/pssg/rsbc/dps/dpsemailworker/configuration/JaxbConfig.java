package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Configuration
public class JaxbConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public JAXBContext kofaxImportSessionJaxbContext() throws JAXBException {
        logger.info("Configuring kofax ImportSession JAXBContext");
        return JAXBContext.newInstance(ImportSession.class);
    }

}
