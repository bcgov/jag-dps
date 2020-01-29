package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantPartyIdResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A Rest Controller for Applicant related operations.
 *
 * @author archanasudha
 * @author alexjoybc@github
 */
@RestController
public class ApplicantController {

    private final ApplicantService applicantService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/validateApplicantForSharing", produces = {"application/xml"}, method = RequestMethod.GET)
    @ApiOperation(value = "Validate Applicant For Sharing", response =
            ValidateApplicantForSharingResponse.class, tags = {"Applicant"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response =
            ValidateApplicantForSharingResponse.class)})
    public ValidateApplicantForSharingResponse validateApplicantForSharing(
            @ApiParam(value = "applPartyId", required = false) @RequestParam(value = "applPartyId", defaultValue = "0"
                    , required = false) String applPartyId,
            @ApiParam(value = "jurisdictionType", required = false) @RequestParam(value = "jurisdictionType",
                    defaultValue = "", required = false) String jurisdictionType) {

        try {

            ValidateApplicantForSharingOrdsResponse _response =
                    applicantService.validateApplicantForSharing(new ValidateApplicantForSharingRequest(applPartyId,
                            jurisdictionType));

            return new ValidateApplicantForSharingResponse(_response.getValidationResult(),
                    Integer.parseInt(_response.getStatusCode()),
                    _response.getStatusMessage());

        } catch (FigaroValidationServiceException ex) {
            logger.error("Exception caught as validateApplicantForSharing : " + ex.getMessage());
            ex.printStackTrace();
            return new ValidateApplicantForSharingResponse(ex.getMessage(),
                    FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD,
                    FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE);
        }

    }


    @RequestMapping(value = "/validateApplicantPartyId", produces = { "application/xml" }, method = RequestMethod.GET)
    @ApiOperation(value = "Validate Applicant Party Id", notes = "", response = ValidateApplicantPartyIdResponse.class, tags = {"Figaro Validation Services" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantPartyIdResponse.class) })
    public ValidateApplicantPartyIdResponse validateApplicantPartyId(
            @ApiParam(value = "applPartyId", required = false) @RequestParam(value = "applPartyId", defaultValue = "0") String applPartyId) throws FigaroValidationServiceException {

        try {

            ValidateApplicantPartyIdOrdsResponse _response =  applicantService.validateApplicantPartyId(applPartyId);

            return new ValidateApplicantPartyIdResponse(
                    _response.getStatusMessage(),
                    Integer.parseInt(_response.getStatusCode()),
                    _response.getSurname(),
                    _response.getFirstName(),
                    _response.getSecondName(),
                    _response.getBirthDate(),
                    _response.getDriversLicense(),
                    _response.getBirthPlace(),
                    _response.getGender()
            );

        } catch (FigaroValidationServiceException ex) {
            logger.error("Exception caught as ValidatePartyId : " + ex.getMessage());
            ex.printStackTrace();

            return new ValidateApplicantPartyIdResponse(ex.getMessage(),
                    FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD);

        }

    }

}
