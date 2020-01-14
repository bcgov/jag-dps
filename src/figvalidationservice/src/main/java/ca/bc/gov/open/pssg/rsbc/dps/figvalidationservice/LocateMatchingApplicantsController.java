package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;

/**
 * 
 * LocateMatchingApplicantsController  class. 
 * 
 * @author shaunmillargov
 *
 */
@RestController
public class LocateMatchingApplicantsController {
	
	@Autowired
	private FigaroValidationImpl figservice;  // connection to ORDS client. 
	
	@RequestMapping(value = "/locateMatchingApplicants",
	  produces = { "application/xml" }, 
	  method = RequestMethod.GET)
	public LocateMatchingApplicantsResponse locateMatchingApplicants(
			@RequestParam(value="applSurname", defaultValue="") String applSurname,
			@RequestParam(value="applFirstName", defaultValue="") String applFirstName,
			@RequestParam(value="applSecondInitial", defaultValue="") String applSecondInitial,
			@RequestParam(value="applBirthDate", defaultValue="") String applBirthDate,
			@RequestParam(value="applDriversLicence", defaultValue="") String applDriversLicence,
			@RequestParam(value="applBirthPlace", defaultValue="") String applBirthPlace,
			@RequestParam(value="applGenderTxt", defaultValue="") String applGenderTxt,
			@RequestParam(value="applAliasSurname1", defaultValue="") String applAliasSurname1,
			@RequestParam(value="applAliasFirstName1", defaultValue="") String applAliasFirstName1,
			@RequestParam(value="applAliasSecondInitial1", defaultValue="") String applAliasSecondInitial1,
			@RequestParam(value="applAliasSurname2", defaultValue="") String applAliasSurname2,
			@RequestParam(value="applAliasFirstName2", defaultValue="") String applAliasFirstName2,
			@RequestParam(value="applAliasSecondInitial2", defaultValue="") String applAliasSecondInitial2,
			@RequestParam(value="applAliasSurname3", defaultValue="") String applAliasSurname3,
			@RequestParam(value="applAliasFirstName3", defaultValue="") String applAliasFirstName3,
			@RequestParam(value="applAliasSecondInitial3", defaultValue="") String applAliasSecondInitial3) throws FigaroValidationServiceException {
		 
		
		 return figservice.locateMatchingApplicants(
				
				new LocateMatchingApplicantsRequest( 
						applSurname,
						applFirstName,
						applSecondInitial,
						applBirthDate,
						applDriversLicence,
						applBirthPlace,
						applGenderTxt,
						applAliasSurname1,
						applAliasFirstName1,
						applAliasSecondInitial1,
						applAliasSurname2,
						applAliasFirstName2,
						applAliasSecondInitial2,
						applAliasSurname3,
						applAliasFirstName3,
						applAliasSecondInitial3));

	
	}}

