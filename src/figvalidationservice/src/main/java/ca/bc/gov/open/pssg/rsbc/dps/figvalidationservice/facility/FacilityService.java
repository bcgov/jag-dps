package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;


/**
 * Collection of validation services for facilities.
 *
 * @author alexjoybc@github
 */
public interface FacilityService {

    ValidateFacilityPartyResponse validateFacilityParty(ValidateFacilityPartyRequest request);

}
