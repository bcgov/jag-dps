package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A Rest Controller for Applicant related operations.
 *
 * @author archanasudha
 * @author shaunmillargov
 * @author alexjoybc@github
 */
@RestController
public class ApplicantController {

    public static final String APPLICANT_API = "Applicant";
    private final ApplicantService applicantService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @RequestMapping(value = "/validateApplicantForSharing", produces = { MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
    @ApiOperation(value = "validateApplicantForSharing", response =
            ValidateApplicantForSharingResponse.class, tags = {APPLICANT_API})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response =
            ValidateApplicantForSharingResponse.class)})
    public ValidateApplicantForSharingResponse validateApplicantForSharing(
            @ApiParam(value = "applPartyId") @RequestParam(value = "applPartyId", defaultValue = "0"
                    , required = false) String applPartyId,
            @ApiParam(value = "jurisdictionType") @RequestParam(value = "jurisdictionType",
                    defaultValue = "", required = false) String jurisdictionType) {

        try {

            ValidateApplicantForSharingOrdsResponse _response =
                    applicantService.validateApplicantForSharing(new ValidateApplicantForSharingRequest(applPartyId,
                            jurisdictionType));

            return new ValidateApplicantForSharingResponse(_response.getValidationResult(),
                    Integer.parseInt(_response.getStatusCode()),
                    _response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as validateApplicantForSharing : " + ex.getMessage());
            ex.printStackTrace();
            return new ValidateApplicantForSharingResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.VALIDATION_SERVICE_FAILURE_CD,
                    FigaroOrdsClientConstants.VALIDATION_SERVICE_BOOLEAN_FALSE);
        }

    }


    @RequestMapping(value = "/validateApplicantPartyId", produces = {  MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
    @ApiOperation(value = "validateApplicantPartyId", response = ValidateApplicantPartyIdResponse.class, tags = { APPLICANT_API })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantPartyIdResponse.class) })
    public ValidateApplicantPartyIdResponse validateApplicantPartyId(
            @ApiParam(value = "applPartyId" ) @RequestParam(value = "applPartyId", defaultValue = "0") String applPartyId)  {

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

        } catch (ApiException ex) {
            logger.error("Exception caught as ValidatePartyId : " + ex.getMessage());
            ex.printStackTrace();

            return new ValidateApplicantPartyIdResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.VALIDATION_SERVICE_FAILURE_CD);

        }

    }

    @RequestMapping(value = "/locateMatchingApplicants",
            produces = { MediaType.APPLICATION_XML_VALUE },
            method = RequestMethod.GET)
    @ApiOperation(value = "locateMatchingApplicants", response = LocateMatchingApplicantsResponse.class, tags={ APPLICANT_API })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = LocateMatchingApplicantsResponse.class) })
    public LocateMatchingApplicantsResponse locateMatchingApplicants(
            @ApiParam(value = "applSurname") @RequestParam(value="applSurname", defaultValue="") String applSurname,
            @ApiParam(value = "applFirstName") @RequestParam(value="applFirstName", defaultValue="") String applFirstName,
            @ApiParam(value = "applSecondInitial") @RequestParam(value="applSecondInitial", defaultValue="") String applSecondInitial,
            @ApiParam(value = "applBirthDate") @RequestParam(value="applBirthDate", defaultValue="") String applBirthDate,
            @ApiParam(value = "applDriversLicence") @RequestParam(value="applDriversLicence", defaultValue="") String applDriversLicence,
            @ApiParam(value = "applBirthPlace") @RequestParam(value="applBirthPlace", defaultValue="") String applBirthPlace,
            @ApiParam(value = "applGenderTxt") @RequestParam(value="applGenderTxt", defaultValue="") String applGenderTxt,
            @ApiParam(value = "applAliasSurname1") @RequestParam(value="applAliasSurname1", defaultValue="") String applAliasSurname1,
            @ApiParam(value = "applAliasFirstName1") @RequestParam(value="applAliasFirstName1", defaultValue="") String applAliasFirstName1,
            @ApiParam(value = "applAliasSecondInitial1") @RequestParam(value="applAliasSecondInitial1", defaultValue="") String applAliasSecondInitial1,
            @ApiParam(value = "applAliasSurname2") @RequestParam(value="applAliasSurname2", defaultValue="") String applAliasSurname2,
            @ApiParam(value = "applAliasFirstName2") @RequestParam(value="applAliasFirstName2", defaultValue="") String applAliasFirstName2,
            @ApiParam(value = "applAliasSecondInitial2") @RequestParam(value="applAliasSecondInitial2", defaultValue="") String applAliasSecondInitial2,
            @ApiParam(value = "applAliasSurname3") @RequestParam(value="applAliasSurname3", defaultValue="") String applAliasSurname3,
            @ApiParam(value = "applAliasFirstName3") @RequestParam(value="applAliasFirstName3", defaultValue="") String applAliasFirstName3,
            @ApiParam(value = "applAliasSecondInitial3") @RequestParam(value="applAliasSecondInitial3", defaultValue="") String applAliasSecondInitial3) {

            return applicantService.locateMatchingApplicants(

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

    }

    @RequestMapping(value = "/validateApplicantService",
            produces = { MediaType.APPLICATION_XML_VALUE },
            method = RequestMethod.GET)
    @ApiOperation(value = "Validate Applicant Service", response = ValidateApplicantServiceResponse.class, tags={ APPLICANT_API })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantServiceResponse.class) })
    public ValidateApplicantServiceResponse validateApplicantService(
            @ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="") String orgPartyId,
            @ApiParam(value = "applPartyId", required = false) @RequestParam(value="applPartyId", defaultValue="") String applPartyId) {

        try {

            ValidateApplicantServiceOrdsResponse _ordsResponse = applicantService
                    .validateApplicantService(applPartyId, orgPartyId);

            return new ValidateApplicantServiceResponse(_ordsResponse.getStatusMessage(),
                    Integer.parseInt(_ordsResponse.getStatusCode()), _ordsResponse.getValidationResult()
            );

        } catch (ApiException ex) {
            logger.error("An exception occurred in ValidateApplicantServiceResponse validateApplicantService() : " + ex.getMessage());
            ex.printStackTrace();
            return new ValidateApplicantServiceResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.VALIDATION_SERVICE_FAILURE_CD,
                    FigaroOrdsClientConstants.VALIDATION_SERVICE_BOOLEAN_FALSE);
        }

    }

}
