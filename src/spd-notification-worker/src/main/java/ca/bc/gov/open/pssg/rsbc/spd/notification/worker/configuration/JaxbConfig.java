package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.configuration;

import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Configuration
public class JaxbConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * kofaxOutputMetadataContext bean to be used to manipulate kofax output metadata xml.
     *
     * @return - the kofax output metadata context
     * @throws JAXBException
     */
    @Bean
    public JAXBContext kofaxOutputMetadataContext() throws JAXBException {
        logger.info("Configuring kofax output JAXBContext");
        return JAXBContext.newInstance(Data.class);
    }

}
