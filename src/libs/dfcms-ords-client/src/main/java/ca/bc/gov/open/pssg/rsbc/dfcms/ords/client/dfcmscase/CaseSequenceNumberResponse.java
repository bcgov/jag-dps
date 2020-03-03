package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase;

import ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.DfcmsOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.text.MessageFormat;

/**
 *
 * Represents the Dfcms Case Response
 *
 * @author carolcarpenterjustice
 *
 */
@JacksonXmlRootElement(localName = "caseSequenceNumberResponse")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CaseSequenceNumberResponse {

    private String caseDescription;
    private String caseSequenceNumber;
    private int respCode;
    private String respMsg;

    private CaseSequenceNumberResponse(int respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    private CaseSequenceNumberResponse(String caseSequenceNumber, String caseDescription, int respCode, String respMsg) {
        this(respCode, respMsg);
        this.caseSequenceNumber = caseSequenceNumber;
        this.caseDescription = caseDescription;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public String getCaseSequenceNumber() {
        return caseSequenceNumber;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static CaseSequenceNumberResponse errorResponse(String errorMessage) {
        return new CaseSequenceNumberResponse(
                DfcmsOrdsClientConstants.SERVICE_FAILURE_CD,
                errorMessage);
    }

    public static CaseSequenceNumberResponse successResponse(String caseSequenceNumber, String caseDescription, String respCodeStr, String respMsg) {

        return new CaseSequenceNumberResponse(caseSequenceNumber, caseDescription, Integer.parseInt(respCodeStr), respMsg);
    }

    @Override
    public String toString() {
        return MessageFormat.format("CaseSequenceNumberResponse: caseSequenceNumber [{0}], caseSequenceNumber [{1}], respCode [{2}], respMsg [{3}]",
                this.caseSequenceNumber, this.caseDescription, this.respCode, this.respMsg);
    }

}
