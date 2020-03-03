package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.text.MessageFormat;

/**
 *
 * Represents the DPS Data Into Figaro Response
 *
 * @author carolcarpenterjustice
 *
 */
@JacksonXmlRootElement(localName = "dpsDataIntoFigaroResponse")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DpsDataIntoFigaroResponse {

    private int respCode;
    private String respMsg;

    private DpsDataIntoFigaroResponse(int respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static DpsDataIntoFigaroResponse errorResponse(String errorMessage) {
        return new DpsDataIntoFigaroResponse(
                FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                errorMessage);
    }

    public static DpsDataIntoFigaroResponse successResponse(String respCodeStr, String respMsg) {

        return new DpsDataIntoFigaroResponse(Integer.parseInt(respCodeStr), respMsg);
    }

    @Override
    public String toString() {
        return MessageFormat.format("DpsDataIntoFigaroResponse: respCode [{0}], respMsg [{1}]", this.respCode, this.respMsg);
    }
}
