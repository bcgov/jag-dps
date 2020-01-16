package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantForSharingResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceResponse;
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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FigaroValidationImpl figservice; // connection to ORDS client.

	@RequestMapping(value = "/validateApplicantForSharing", produces = {"application/xml" }, method = RequestMethod.GET)
	
	@ApiOperation(value = "Validate Applicant For Sharing", notes = "", response = ValidateApplicantForSharingResponse.class, tags = {
			"Figaro Validation Services" })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantForSharingResponse.class) })

	public ValidateApplicantForSharingResponse validateApplicantForSharingResponse(
			@ApiParam(value = "applPartyId", required = false) @RequestParam(value = "applPartyId", defaultValue = "0", required = false) String applPartyId,
			@ApiParam(value = "jurisdictionType", required = false) @RequestParam(value = "jurisdictionType", defaultValue = "", required = false) String jurisdictionType)
			throws FigaroValidationServiceException {

		try {

			return figservice
					.validateApplicantForSharing(new ValidateApplicantForSharingRequest(applPartyId, jurisdictionType));

		} catch (FigaroValidationServiceException ex) {
			logger.error("Exception caught as ValidateApplicantForSharingResponse : " + ex.getMessage());
			ex.printStackTrace();
			return new ValidateApplicantForSharingResponse(ex.getMessage(),
					FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE,
					FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD);
		}
	}

}
