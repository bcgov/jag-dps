package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration;

import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;

public interface RegistrationService {

    void registerPackage(DpsMetadata dpsMetadata);

}
