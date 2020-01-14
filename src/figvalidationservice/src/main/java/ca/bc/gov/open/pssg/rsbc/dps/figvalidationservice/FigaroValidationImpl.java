package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.springframework.stereotype.Service;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;

/**
 * 
 * FigaroValidationImpl class 
 * 
 * Note: Calls ORDS clients 
 * 
 * @author shaunmillargov
 *
 */
@Service
public class FigaroValidationImpl implements FigaroValidation {

	@Override
	public LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr)
			throws FigaroValidationServiceException {
		
		// TODO - replace the following dummy response with a call to ORDS  
		
		// Note: Call response code and response message comes from the ORDS call.  
		
		LocateMatchingApplicantsResponse resp = new LocateMatchingApplicantsResponse(
				"Success - found Party Id 209",
				1,
				"209", 
				"MOUSE",
				"MINNIE", 
				null, 
				"1950-01-10", 
				"999999",
				"DISNEY",
				"F"); 
		
		return resp; 
		
	}
	
}

