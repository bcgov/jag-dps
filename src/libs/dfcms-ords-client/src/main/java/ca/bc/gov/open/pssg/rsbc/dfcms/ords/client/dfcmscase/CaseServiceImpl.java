package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase;

import ca.bc.gov.open.ords.dfcms.client.api.DfcmsApi;
import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;
import ca.bc.gov.open.ords.dfcms.client.api.model.CaseSequenceNumberOrdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dfcms Case Service Implementation using ORDS services.
 *
 * @author carolcarpenterjustice
 */
public class CaseServiceImpl implements CaseService {

    private final DfcmsApi dfcmsApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CaseServiceImpl(DfcmsApi dfcmsApi) {
        this.dfcmsApi = dfcmsApi;
    }

    @Override
    public CaseSequenceNumberResponse caseSequenceNumber(String driverLicenseNo, String surnameCode) {

        try {
            CaseSequenceNumberOrdsResponse response = this.dfcmsApi.caseSequenceNumberGet(driverLicenseNo, surnameCode);
            return CaseSequenceNumberResponse.SuccessResponse(response.getCaseSequenceNumber(), response.getCaseDescription(), response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as Case Service, caseSequenceNumber", ex);
            return CaseSequenceNumberResponse.ErrorResponse(ex.getMessage());
        }
    }
}
