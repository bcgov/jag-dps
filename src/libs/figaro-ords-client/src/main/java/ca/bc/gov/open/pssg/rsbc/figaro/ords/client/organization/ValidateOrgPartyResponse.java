package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 *
 * Represents the Validate Org Party Response
 *
 * @author carolcarpenterjustice
 */
@JacksonXmlRootElement(localName = "validateOrgPartyResponse")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidateOrgPartyResponse {

    private String validationResult;
    private String foundOrgPartyId;
    private String foundOrgName;
    private String foundOrgType;
    private int respCode;
    private String respMsg;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ValidateOrgPartyContactPersonResponse> contactPersons;

    /**
     *
     * ValidateOrgPartyResponse default constructor.
     *
     * @param validationResult
     * @param respCode
     * @param respMsg
     *
     * @author carolcarpenterjustice
     */
    private ValidateOrgPartyResponse(String validationResult, int respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.validationResult = validationResult;
    }
    private ValidateOrgPartyResponse(String validationResult, int respCode, String respMsg, String foundOrgPartyId,
                                          String foundOrgName, String foundOrgType,
                                          List<ValidateOrgPartyContactPersonResponse> contactPersons) {
        this(validationResult, respCode, respMsg);
        this.foundOrgPartyId = foundOrgPartyId;
        this.foundOrgName = foundOrgName;
        this.foundOrgType = foundOrgType;
        this.contactPersons = contactPersons;
    }

    public String getValidationResult() { return validationResult; }

    public String getFoundOrgPartyId() { return foundOrgPartyId; }

    public String getFoundOrgName() { return foundOrgName; }

    public String getFoundOrgType() { return foundOrgType; }

    public List<ValidateOrgPartyContactPersonResponse> getContactPersons() { return contactPersons; }

    public int getRespCode() { return respCode; }

    public String getRespMsg() { return respMsg; }

    public static ValidateOrgPartyResponse errorResponse(String errorMessage) {
        return new ValidateOrgPartyResponse(
                FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE,
                FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                errorMessage);
    }

    public static ValidateOrgPartyResponse successResponse(String validationResult, String respCodeStr, String respMsg,
                                                           String foundOrgPartyId, String foundOrgName, String foundOrgType,
                                                           List<ValidateOrgPartyContactPersonResponse> contactPersons) {

        return new ValidateOrgPartyResponse(validationResult, Integer.parseInt(respCodeStr), respMsg, foundOrgPartyId, foundOrgName, foundOrgType, contactPersons);
    }
}
