package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase;

import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.DfcmsOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * Represents the Dfcms Case Response
 *
 * @author carolcarpenterjustice
 *
 */
@JacksonXmlRootElement(localName = "GetValidOpenDFCMCase")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CaseSequenceNumberResponse {

    @JacksonXmlProperty(localName = "caseDesc")
    private String caseDescription;

    @JacksonXmlProperty(localName = "int")
    private String caseSequenceNumber;



    private CaseSequenceNumberResponse(String caseSequenceNumber, String caseDescription) {
        this.caseSequenceNumber = caseSequenceNumber;
        this.caseDescription = caseDescription;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public String getCaseSequenceNumber() {
        return caseSequenceNumber;
    }

    public static CaseSequenceNumberResponse errorResponse() {
        return new CaseSequenceNumberResponse(
                DfcmsOrdsClientConstants.SERVICE_FAILURE_CD,
                null);
    }

    public static CaseSequenceNumberResponse successResponse(String caseSequenceNumber, String caseDescription) {
        return new CaseSequenceNumberResponse(caseSequenceNumber, caseDescription);
    }

}
