package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility;

import ca.bc.gov.open.ords.figcr.client.api.FacilityApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateFacilityPartyOrdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Facility Service Implementation using ORDS services.
 *
 * @author alexjoybc@github
 */
public class FacilityServiceImpl implements FacilityService {

    private final FacilityApi facilityApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FacilityServiceImpl(FacilityApi facilityApi) {
        this.facilityApi = facilityApi;
    }

    @Override
    public ValidateFacilityPartyResponse validateFacilityParty(ValidateFacilityPartyRequest request) {

        try {
            ValidateFacilityPartyOrdsResponse response = this.facilityApi.validateFacilityParty(
                    request.getFacilityPartyId(), request.getFacilitySubname(), request.getFacilitySubname2(),
                    request.getFacilitySubname3(),request.getFacilitySubname4(),request.getFacilitySubname5());
            return  ValidateFacilityPartyResponse.successResponse(response.getValidationResult(),
                    response.getStatusCode(), response.getStatusMessage(),
                    response.getFoundFacilityPartyId(), response.getFoundFacilityName());

        } catch (ApiException ex) {
            logger.error("Exception caught as Facility Service, validateFacilityParty : " + ex.getMessage(), ex);
            return ValidateFacilityPartyResponse.errorResponse(ex.getMessage());
        }
    }

}
