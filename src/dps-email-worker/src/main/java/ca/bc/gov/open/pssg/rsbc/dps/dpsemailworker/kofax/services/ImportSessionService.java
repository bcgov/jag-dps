package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;

import java.io.InputStream;


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
    ImportSession generateImportSession(DpsMetadata dpsMetadata);

    byte[] convertToXmlBytes(ImportSession importSession);

    ImportSession convertToImportSession(InputStream is);

}
