package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * LocateMatchingApplicantsController class. 
 * 
 * @author shaunmillargov
 *
 */
@RestController
public class LocateMatchingApplicantsController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FigaroValidationImpl figservice;  // connection to ORDS client. 
	
	@RequestMapping(value = "/locateMatchingApplicants",
			produces = { "application/xml" }, 
			method = RequestMethod.GET)
		@ApiOperation(value = "Locate Matching Applicant Search", notes = "", response = LocateMatchingApplicantsResponse.class, tags={ "Figaro Validation Services"})
	  	@ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = LocateMatchingApplicantsResponse.class) })
	public LocateMatchingApplicantsResponse locateMatchingApplicants(
			@ApiParam(value = "applSurname", required = false) @RequestParam(value="applSurname", defaultValue="") String applSurname,
			@ApiParam(value = "applFirstName", required = false) @RequestParam(value="applFirstName", defaultValue="") String applFirstName,
			@ApiParam(value = "applSecondInitial", required = false) @RequestParam(value="applSecondInitial", defaultValue="") String applSecondInitial,
			@ApiParam(value = "applBirthDate", required = false) @RequestParam(value="applBirthDate", defaultValue="") String applBirthDate,
			@ApiParam(value = "applDriversLicence", required = false) @RequestParam(value="applDriversLicence", defaultValue="") String applDriversLicence,
			@ApiParam(value = "applBirthPlace", required = false) @RequestParam(value="applBirthPlace", defaultValue="") String applBirthPlace,
			@ApiParam(value = "applGenderTxt", required = false) @RequestParam(value="applGenderTxt", defaultValue="") String applGenderTxt,
			@ApiParam(value = "applAliasSurname1", required = false) @RequestParam(value="applAliasSurname1", defaultValue="") String applAliasSurname1,
			@ApiParam(value = "applAliasFirstName1", required = false) @RequestParam(value="applAliasFirstName1", defaultValue="") String applAliasFirstName1,
			@ApiParam(value = "applAliasSecondInitial1", required = false) @RequestParam(value="applAliasSecondInitial1", defaultValue="") String applAliasSecondInitial1,
			@ApiParam(value = "applAliasSurname2", required = false) @RequestParam(value="applAliasSurname2", defaultValue="") String applAliasSurname2,
			@ApiParam(value = "applAliasFirstName2", required = false) @RequestParam(value="applAliasFirstName2", defaultValue="") String applAliasFirstName2,
			@ApiParam(value = "applAliasSecondInitial2", required = false) @RequestParam(value="applAliasSecondInitial2", defaultValue="") String applAliasSecondInitial2,
			@ApiParam(value = "applAliasSurname3", required = false) @RequestParam(value="applAliasSurname3", defaultValue="") String applAliasSurname3,
			@ApiParam(value = "applAliasFirstName3", required = false) @RequestParam(value="applAliasFirstName3", defaultValue="") String applAliasFirstName3,
			@ApiParam(value = "applAliasSecondInitial3", required = false) @RequestParam(value="applAliasSecondInitial3", defaultValue="") String applAliasSecondInitial3) throws FigaroValidationServiceException {
		 
		
		try {
			
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
			 
		} catch (FigaroValidationServiceException ex) {
			logger.error("Exception caught as LocateMatchingApplicants : " + ex.getMessage()); 
			ex.printStackTrace();
			return new LocateMatchingApplicantsResponse(
					ex.getMessage(), 
					FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD);
		}	 			 

	
	}}

