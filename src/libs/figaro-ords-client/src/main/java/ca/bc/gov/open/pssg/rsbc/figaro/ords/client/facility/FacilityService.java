package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility;

/**
 * Collection of validation services for facilities.
 *
 * @author alexjoybc@github
 */
public interface FacilityService {

    ValidateFacilityPartyResponse validateFacilityParty(ValidateFacilityPartyRequest request);

}
