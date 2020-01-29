package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgDrawDownBalanceOrdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Org Service Implementation using ORDS services.
 *
 * @author carolcarpenterjustice
 */
public class OrgServiceImpl implements OrgService {

    private final OrgApi orgApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrgServiceImpl(OrgApi orgApi) {
        this.orgApi = orgApi;
    }

    @Override
    public ValidateOrgDrawDownBalanceResponse validateOrgDrawDownBalance(ValidateOrgDrawDownBalanceRequest request) {

        try {
            ValidateOrgDrawDownBalanceOrdsResponse response = this.orgApi.validateOrgDrawDownBalance(request.getJurisdictionType(), request.getOrgPartyId(), request.getScheduleType());
            return  ValidateOrgDrawDownBalanceResponse.SuccessResponse(response.getValidationResult(), response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {

            logger.error("Validate Org Service did throw exception: " + ex.getMessage());
            ex.printStackTrace();

            return ValidateOrgDrawDownBalanceResponse.ErrorResponse(ex.getMessage());
        }
    }
}
