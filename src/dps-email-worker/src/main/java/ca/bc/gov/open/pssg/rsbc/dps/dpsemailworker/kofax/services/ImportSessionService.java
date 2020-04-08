package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services;

import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;


/**
 * Suite of services for generating KOFAX import session
 * @author alexjoybc@github
 */
public interface ImportSessionService {

    /**
     * Generate the xml needed for kofax interaction
     * @param dpsMetadata dpsMetatada info
     * @return
     */
    String generateImportSessionXml(DpsMetadata dpsMetadata);

}
