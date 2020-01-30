package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import ca.bc.gov.open.ords.figcr.client.api.FigvalidationsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FigaroValidationImpl class
 * <p>
 * Note: Calls ORDS clients
 *
 * @author shaunmillargov
 */
@Service
public class FigaroValidationImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FigvalidationsApi ordsapi;

  


}

