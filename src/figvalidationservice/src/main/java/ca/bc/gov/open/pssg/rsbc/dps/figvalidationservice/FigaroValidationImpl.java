package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.springframework.stereotype.Service;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantPartyIdResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceResponse;

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

	@Override
	public ValidateApplicantServiceResponse validateApplicantService(ValidateApplicantServiceRequest lmr)
			throws FigaroValidationServiceException {
		
		// TODO - replace the following dummy response with a call to ORDS  
		
		// Note: Call response code and response message comes from the ORDS call.  
				
		ValidateApplicantServiceResponse resp = new ValidateApplicantServiceResponse(
				"success",
				0,
				"N"
				); 
		
		return resp;
		 
	}
	
	@Override
	/*
	 * service method to get the response for /validateApplicantForSharing requests
	 */
	public ValidateApplicantForSharingResponse validateApplicantForSharing(
			ValidateApplicantForSharingRequest validateApplicantForSharingRequest)
			throws FigaroValidationServiceException {
		
		// TODO - replace the following dummy response with actual response from ORDS.  

		ValidateApplicantForSharingResponse validateApplicantForSharingResponse = new ValidateApplicantForSharingResponse(
				"N", "success", 0);

		return validateApplicantForSharingResponse;

	}
	
	@Override
	public ValidateApplicantPartyIdResponse validateApplicantPartyId(String applParyId)
			throws FigaroValidationServiceException {
		
		// TODO - replace the following dummy response with a call to ORDS  
		
		// Note: Call response code and response message comes from the ORDS call.  
						
		ValidateApplicantPartyIdResponse resp = new ValidateApplicantPartyIdResponse(
					"success",
					0, 
					"Cool",
					"Joe", 
					"Second",
					"2016/10/14",
					"0123456",
					"Toronto",
					"F"
					); 
			
			return resp;
			
	}
	
}

