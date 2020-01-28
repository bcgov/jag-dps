package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * Represents the Validate Facility Party Response
 *
 * @author alexjoybc@github
 *
 */
@JacksonXmlRootElement(localName = "validateFacilityPartyResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateFacilityPartyResponse {

    private String validationResult;
    private String foundFacilityPartyId;
    private String foundFacilityName;
    private int respCode;
    private String respMsg;


    private ValidateFacilityPartyResponse(String validationResult, int respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.validationResult = validationResult;
    }

    /**
     *
     * ValidateFacilityPartyResponse default constructor.
     *
     * @param validationResult
     * @param foundFacilityPartyId
     * @param foundFacilityName
     * @param respCode
     * @param respMsg
     */
    private ValidateFacilityPartyResponse(String validationResult, int respCode, String respMsg, String foundFacilityPartyId,
                                         String foundFacilityName) {
        this(validationResult, respCode, respMsg);
        this.foundFacilityPartyId = foundFacilityPartyId;
        this.foundFacilityName = foundFacilityName;

    }

    public String getValidationResult() {
        return validationResult;
    }

    public String getFoundFacilityPartyId() {
        return foundFacilityPartyId;
    }

    public String getFoundFacilityName() {
        return foundFacilityName;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static ValidateFacilityPartyResponse ErrorResponse(String validationResult) {
        return new ValidateFacilityPartyResponse(
                validationResult,
                FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD,
                FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE);
    }


    public static ValidateFacilityPartyResponse SuccessResponse(String validationResult, String respCodeStr, String respMsg, String foundFacilityPartyId,
                                                String foundFacilityName) {

        return new ValidateFacilityPartyResponse(validationResult, Integer.parseInt(respCodeStr), respMsg, foundFacilityPartyId, foundFacilityName);

    }
}
