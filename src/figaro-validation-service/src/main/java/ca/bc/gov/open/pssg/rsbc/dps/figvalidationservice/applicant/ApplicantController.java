package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/validateApplicantForSharing", produces = { MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "validateApplicantForSharing", response = ValidateApplicantForSharingResponse.class, tags = {APPLICANT_API})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantForSharingResponse.class)})
    public ValidateApplicantForSharingResponse validateApplicantForSharing(
            @ApiParam(value = "applPartyId") @RequestParam(value = "applPartyId", defaultValue = "0"
                    , required = false) String applPartyId,
            @ApiParam(value = "jurisdictionType") @RequestParam(value = "jurisdictionType",
                    defaultValue = "", required = false) String jurisdictionType) {

        try {

            ValidateApplicantForSharingResponse response =
                    applicantService.validateApplicantForSharing(new ValidateApplicantForSharingRequest(applPartyId,
                            jurisdictionType));

            return new ValidateApplicantForSharingResponse(response.getValidationResult(),
                    response.getRespCode(),
                    response.getRespMsg());

        } catch (ApiException ex) {
            logger.error("Exception caught as validateApplicantForSharing :", ex);
            return new ValidateApplicantForSharingResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                    FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE);
        }
    }


    @GetMapping(value = "/validateApplicantPartyId", produces = {  MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "validateApplicantPartyId", response = ValidateApplicantPartyIdResponse.class, tags = { APPLICANT_API })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateApplicantPartyIdResponse.class) })
    public ValidateApplicantPartyIdResponse validateApplicantPartyId(
            @ApiParam(value = "applPartyId" ) @RequestParam(value = "applPartyId", defaultValue = "0") String applPartyId)  {

        try {

            ValidateApplicantPartyIdResponse response =  applicantService.validateApplicantPartyId(applPartyId);

            return new ValidateApplicantPartyIdResponse(
                    response.getRespMsg(),
                    response.getRespCode(),
                    response.getFoundSurname(),
                    response.getFoundFirstName(),
                    response.getFoundSecondName(),
                    response.getFoundBirthDate(),
                    response.getFoundDriversLicence(),
                    response.getFoundBirthPlace(),
                    response.getFoundGenderTxt()
            );

        } catch (ApiException ex) {
            logger.error("Exception caught as ValidatePartyId :", ex);
            return new ValidateApplicantPartyIdResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.SERVICE_FAILURE_CD);
        }
    }

    @GetMapping(value = "/locateMatchingApplicants", produces = { MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "locateMatchingApplicants", response = LocateMatchingApplicantsResponse.class, tags={ APPLICANT_API })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = LocateMatchingApplicantsResponse.class) })
    @SuppressWarnings("java:S107")
    //This is a legacy migration and requires this many parameters
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

    @GetMapping(value = "/validateApplicantService", produces = { MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Validate Applicant Service", response = ValidateOrgApplicantServiceResponse.class, tags={ APPLICANT_API })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateOrgApplicantServiceResponse.class) })
    public ValidateOrgApplicantServiceResponse validateOrgApplicantService(
            @ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="") String orgPartyId,
            @ApiParam(value = "applPartyId", required = false) @RequestParam(value="applPartyId", defaultValue="") String applPartyId) {

        try {

            ValidateOrgApplicantServiceResponse response = applicantService
                    .validateOrgApplicantService(applPartyId, orgPartyId);

            return new ValidateOrgApplicantServiceResponse(response.getValidationResult(), response.getRespCode(), response.getRespMsg());

        } catch (ApiException ex) {
            logger.error("An exception occurred in ValidateOrgApplicantServiceResponse validateOrgApplicantService() : ", ex);
            return new ValidateOrgApplicantServiceResponse(ex.getMessage(),
                    FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                    FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE);
        }
    }
}
