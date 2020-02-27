package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.text.MessageFormat;

/**
 *
 * Represents the DPS Document Response
 *
 * @author carolcarpenterjustice
 *
 */
@JacksonXmlRootElement(localName = "dpsDocumentResponse")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DpsDocumentResponse {

    private String guid;
    private int respCode;
    private String respMsg;

    private DpsDocumentResponse(int respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    private DpsDocumentResponse(String guid, int respCode, String respMsg) {
        this(respCode, respMsg);
        this.guid = guid;
    }


    public String getGuid() { return guid; }

    public void setGuid(String guid) { this.guid = guid; }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static DpsDocumentResponse ErrorResponse(String errorMessage) {
        return new DpsDocumentResponse(
                FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                errorMessage);
    }

    public static DpsDocumentResponse SuccessResponse(String guid, String respCodeStr, String respMsg) {

        return new DpsDocumentResponse(guid, Integer.parseInt(respCodeStr), respMsg);
    }

    @Override
    public String toString() {
        return MessageFormat.format("DpsDocumentResponse: guid [{0}], respCode [{1}], respMsg [{2}]", this.guid, this.respCode, this.respMsg);
    }
}
