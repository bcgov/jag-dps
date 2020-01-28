package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * ValidateApplicantServiceController class. 
 * 
 * All response codes are set by FIGARO. 0 = success, all failures will be returned as NEGATIVE values with an associated message.  
 * 
 * @author shaunmillargov
 *
 */
@RestController
public class ValidateApplicantServiceController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FigaroValidationImpl figservice;  // connection to ORDS client. 
	
	public ValidateApplicantServiceController(FigaroValidationImpl figservice) {
		this.figservice = figservice;
	}

	@RequestMapping(value = "/validateApplicantService",
			produces = { "application/xml" }, 
			method = RequestMethod.GET)
		@ApiOperation(value = "Validate Applicant Service", notes = "", response = ValidateApplicantServiceResponse.class, tags={ "Figaro Validation Services"})
	  	@ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantServiceResponse.class) })
	public ValidateApplicantServiceResponse validateApplicantService(
			@ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="") String orgPartyId,
			@ApiParam(value = "applPartyId", required = false) @RequestParam(value="applPartyId", defaultValue="") String applPartyId) throws FigaroValidationServiceException {
		 
		
		try {

			ValidateApplicantServiceOrdsResponse _ordsResponse = figservice
					.validateApplicantServiceOrdsResponse(applPartyId, orgPartyId);

			return new ValidateApplicantServiceResponse(_ordsResponse.getStatusMessage(),
					Integer.parseInt(_ordsResponse.getStatusCode()), _ordsResponse.getValidationResult()

			);

		} catch (FigaroValidationServiceException ex) {
			logger.error("An exception occured in ValidateApplicantServiceResponse validateApplicantService() : " + ex.getMessage());
			ex.printStackTrace();
			return new ValidateApplicantServiceResponse(ex.getMessage(),
					FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD,
					FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE);
		}

	}}

