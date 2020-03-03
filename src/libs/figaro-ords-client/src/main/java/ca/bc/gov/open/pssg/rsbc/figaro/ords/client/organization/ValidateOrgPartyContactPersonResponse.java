package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * Represents the Validate Org Party Contact Person Response
 *
 * @author carolcarpenterjustice
 */
@JacksonXmlRootElement(localName = "contactPersons")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidateOrgPartyContactPersonResponse {

    private String contactPersonName;
    private String contactPersonRole;
    private String contactPersonPartyId;

    /**
     *
     * ValidateOrgPartyContactPersonResponse default constructor.
     *
     * @param contactPersonName
     * @param contactPersonRole
     * @param contactPersonPartyId
     *
     */
    private ValidateOrgPartyContactPersonResponse(String contactPersonName, String contactPersonRole, String contactPersonPartyId) {
        this.contactPersonName = contactPersonName;
        this.contactPersonRole = contactPersonRole;
        this.contactPersonPartyId = contactPersonPartyId;
    }

    public String getContactPersonName() { return contactPersonName; }

    public String getContactPersonRole() { return contactPersonRole; }

    public String getContactPersonPartyId() { return contactPersonPartyId; }

    public static ValidateOrgPartyContactPersonResponse SuccessResponse(String contactPersonName, String contactPersonRole, String contactPersonPartyId) {
        return new ValidateOrgPartyContactPersonResponse(contactPersonName, contactPersonRole, contactPersonPartyId);
    }
}
