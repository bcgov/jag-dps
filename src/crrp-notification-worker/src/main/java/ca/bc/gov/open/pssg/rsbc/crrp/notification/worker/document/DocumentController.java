package ca.bc.gov.open.pssg.rsbc.crrp.notification.worker.document;

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
 * Expose endpoints for document related
 *
 * @author carolcarpenterjustice
 *
 */
@RestController
public class DocumentController {

    private final DocumentService documentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(value = "/dpsDataIntoFigaro",
            produces = { MediaType.APPLICATION_XML_VALUE },
            method = RequestMethod.GET)
    @ApiOperation(value = "DPS Data into Figaro", response = DpsDataIntoFigaroResponse.class, tags={"Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = DpsDataIntoFigaroResponse.class) })
    public DpsDataIntoFigaroResponse DpsDataIntoFigaro(
            @ApiParam(value = "scheduleType", required = false) @RequestParam(value="scheduleType", defaultValue="") String scheduleType,
            @ApiParam(value = "jurisdictionType", required = false) @RequestParam(value="jurisdictionType", defaultValue="") String jurisdictionType,
            @ApiParam(value = "processingStream", required = false) @RequestParam(value="processingStream", defaultValue="") String processingStream,
            @ApiParam(value = "applicationCategory", required = false) @RequestParam(value="applicationCategory", defaultValue="") String applicationCategory,
            @ApiParam(value = "paymentMethod", required = false) @RequestParam(value="paymentMethod", defaultValue="") String paymentMethod,
            @ApiParam(value = "nonFinRejectReason", required = false) @RequestParam(value="nonFinRejectReason", defaultValue="") String nonFinRejectReason,
            @ApiParam(value = "applicationSignedYn", required = false) @RequestParam(value="applicationSignedYn", defaultValue="") String applicationSignedYn,
            @ApiParam(value = "applicationSignedDate", required = false) @RequestParam(value="applicationSignedDate", defaultValue="") String applicationSignedDate,
            @ApiParam(value = "applicationGuardianSignedYn", required = false) @RequestParam(value="applicationGuardianSignedYn", defaultValue="") String applicationGuardianSignedYn,
            @ApiParam(value = "applicationPaymentId", required = false) @RequestParam(value="applicationPaymentId", defaultValue="") String applicationPaymentId,
            @ApiParam(value = "applicationIncompleteReason", required = false) @RequestParam(value="applicationIncompleteReason", defaultValue="") String applicationIncompleteReason,
            @ApiParam(value = "applicationValidateUsername", required = false) @RequestParam(value="applicationValidateUsername", defaultValue="") String applicationValidateUsername,
            @ApiParam(value = "applicationDocumentGuid", required = false) @RequestParam(value="applicationDocumentGuid", defaultValue="") String applicationDocumentGuid,
            @ApiParam(value = "applPartyId", required = false) @RequestParam(value="applPartyId", defaultValue="") String applPartyId,
            @ApiParam(value = "applSurname", required = false) @RequestParam(value="applSurname", defaultValue="") String applSurname,
            @ApiParam(value = "applFirstName", required = false) @RequestParam(value="applFirstName", defaultValue="") String applFirstName,
            @ApiParam(value = "applSecondName", required = false) @RequestParam(value="applSecondName", defaultValue="") String applSecondName,
            @ApiParam(value = "applBirthDate", required = false) @RequestParam(value="applBirthDate", defaultValue="") String applBirthDate,
            @ApiParam(value = "applGender", required = false) @RequestParam(value="applGender", defaultValue="") String applGender,
            @ApiParam(value = "applBirthPlace", required = false) @RequestParam(value="applBirthPlace", defaultValue="") String applBirthPlace,
            @ApiParam(value = "applAddlSurname1", required = false) @RequestParam(value="applAddlSurname1", defaultValue="") String applAddlSurname1,
            @ApiParam(value = "applAddlFirstName1", required = false) @RequestParam(value="applAddlFirstName1", defaultValue="") String applAddlFirstName1,
            @ApiParam(value = "applAddlSecondName1", required = false) @RequestParam(value="applAddlSecondName1", defaultValue="") String applAddlSecondName1,
            @ApiParam(value = "applAddlSurname2", required = false) @RequestParam(value="applAddlSurname2", defaultValue="") String applAddlSurname2,
            @ApiParam(value = "applAddlFirstName2", required = false) @RequestParam(value="applAddlFirstName2", defaultValue="") String applAddlFirstName2,
            @ApiParam(value = "applAddlSecondName2", required = false) @RequestParam(value="applAddlSecondName2", defaultValue="") String applAddlSecondName2,
            @ApiParam(value = "applAddlSurname3", required = false) @RequestParam(value="applAddlSurname3", defaultValue="") String applAddlSurname3,
            @ApiParam(value = "applAddlFirstName3", required = false) @RequestParam(value="applAddlFirstName3", defaultValue="") String applAddlFirstName3,
            @ApiParam(value = "applAddlSecondName3", required = false) @RequestParam(value="applAddlSecondName3", defaultValue="") String applAddlSecondName3,
            @ApiParam(value = "applStreetAddress", required = false) @RequestParam(value="applStreetAddress", defaultValue="") String applStreetAddress,
            @ApiParam(value = "applCity", required = false) @RequestParam(value="applCity", defaultValue="") String applCity,
            @ApiParam(value = "applProvince", required = false) @RequestParam(value="applProvince", defaultValue="") String applProvince,
            @ApiParam(value = "applCountry", required = false) @RequestParam(value="applCountry", defaultValue="") String applCountry,
            @ApiParam(value = "applPostalCode", required = false) @RequestParam(value="applPostalCode", defaultValue="") String applPostalCode,
            @ApiParam(value = "applDriversLicence", required = false) @RequestParam(value="applDriversLicence", defaultValue="") String applDriversLicence,
            @ApiParam(value = "applPhoneNumber", required = false) @RequestParam(value="applPhoneNumber", defaultValue="") String applPhoneNumber,
            @ApiParam(value = "applEmailAddress", required = false) @RequestParam(value="applEmailAddress", defaultValue="") String applEmailAddress,
            @ApiParam(value = "applOrgPartyId", required = false) @RequestParam(value="applOrgPartyId", defaultValue="") String applOrgPartyId,
            @ApiParam(value = "applOrgFacilityPartyId", required = false) @RequestParam(value="applOrgFacilityPartyId", defaultValue="") String applOrgFacilityPartyId,
            @ApiParam(value = "applOrgFacilityName", required = false) @RequestParam(value="applOrgFacilityName", defaultValue="") String applOrgFacilityName,
            @ApiParam(value = "applOrgContactPartyId", required = false) @RequestParam(value="applOrgContactPartyId", defaultValue="") String applOrgContactPartyId) {

        return this.documentService.dpsDataIntoFigaro(new DpsDataIntoFigaroRequestBody(scheduleType, jurisdictionType, processingStream,
                applicationCategory, paymentMethod, nonFinRejectReason, applicationSignedYn, applicationSignedDate, applicationGuardianSignedYn,
                applicationPaymentId, applicationIncompleteReason, applicationValidateUsername, applicationDocumentGuid, applPartyId,
                applSurname, applFirstName, applSecondName, applBirthDate, applGender, applBirthPlace,
                applAddlSurname1, applAddlFirstName1, applAddlSecondName1,
                applAddlSurname2, applAddlFirstName2, applAddlSecondName2,
                applAddlSurname3, applAddlFirstName3, applAddlSecondName3,
                applStreetAddress, applCity, applProvince, applCountry, applPostalCode,
                applDriversLicence, applPhoneNumber, applEmailAddress,
                applOrgPartyId, applOrgFacilityPartyId, applOrgFacilityName, applOrgContactPartyId));
    }
}

