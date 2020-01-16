package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * ValidateApplicantForSharingController class to handle requests for /validateApplicantForSharing
 * 
 * @author archanasudha
 *
 */
@RestController
public class ValidateApplicantForSharingController {

	@Autowired
	private FigaroValidationImpl figservice; // connection to ORDS client.

	@RequestMapping(value = "/validateApplicantForSharing", produces = {
			"application/xml" }, method = RequestMethod.GET)
	@ApiOperation(value = "Validate Applicant For Sharing", notes = "", response = ValidateApplicantForSharingResponse.class, tags={ "Figaro Validation Services"})
  	@ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantForSharingResponse.class) })
    
	public ValidateApplicantForSharingResponse validateApplicantForSharingResponse(
			@ApiParam(value = "applPartyId", required = false) @RequestParam(value = "applPartyId", defaultValue = "0",required = false) String applPartyId,
			@ApiParam(value = "jurisdictionType", required = false) @RequestParam(value = "jurisdictionType", defaultValue = "",required = false) String jurisdictionType)
			throws FigaroValidationServiceException {

		return figservice
				.validateApplicantForSharing(new ValidateApplicantForSharingRequest(applPartyId, jurisdictionType));
	}

}
