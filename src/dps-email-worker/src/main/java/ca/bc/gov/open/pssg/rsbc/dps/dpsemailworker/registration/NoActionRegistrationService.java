package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoActionRegistrationService implements RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void registerPackage(DpsMetadata dpsMetadata) {
        logger.debug("no action required, registration is turned off");
    }
}
