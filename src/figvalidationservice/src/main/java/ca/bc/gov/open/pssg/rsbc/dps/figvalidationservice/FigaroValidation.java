package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;


/**
 * 
 * Figaro Validation Service interface
 * 
 * @author shaunmillargov
 *
 */
public interface FigaroValidation {

	public LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr)
			throws FigaroValidationServiceException;
	
	public ValidateApplicantServiceOrdsResponse validateApplicantServiceOrdsResponse(String applPartyId,String orgPartyId)
			throws FigaroValidationServiceException;

}



