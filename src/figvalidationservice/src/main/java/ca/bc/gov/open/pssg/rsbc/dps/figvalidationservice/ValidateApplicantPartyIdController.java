package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantPartyIdResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * ValidateApplicantPartyIdController class. 
 * 
 * All response codes are set by FIGARO. 0 = success, all failures will be returned as NEGATIVE values with an associated message.  
 * 
 * @author shaunmillargov
 *
 */
@RestController
public class ValidateApplicantPartyIdController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FigaroValidationImpl figservice;  // connection to ORDS client. 
	
	@RequestMapping(value = "/validateApplicantPartyId",
			produces = { "application/xml" }, 
			method = RequestMethod.GET)
		@ApiOperation(value = "Validate Applicant Party Id", notes = "", response = ValidateApplicantPartyIdResponse.class, tags={ "Figaro Validation Services"})
	  	@ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantPartyIdResponse.class) })
	public ValidateApplicantPartyIdResponse validateApplicantPartyId(
			@ApiParam(value = "applPartyId", required = false) @RequestParam(value="applPartyId", defaultValue="0") String applPartyId) throws FigaroValidationServiceException {
		 
		try {
			
			 return figservice.validateApplicantPartyId( applPartyId );
		 
		} catch (FigaroValidationServiceException ex) {
			logger.error("Exception caught as ValidatePartyId : " + ex.getMessage()); 
			ex.printStackTrace();
			return new ValidateApplicantPartyIdResponse(
					ex.getMessage(),
					FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD);
		}

	}
}

