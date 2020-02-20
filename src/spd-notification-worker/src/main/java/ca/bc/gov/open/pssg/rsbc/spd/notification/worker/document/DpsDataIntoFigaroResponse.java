package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.FigaroServiceConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

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

    public static DpsDataIntoFigaroResponse ErrorResponse(String validationResult) {
        return new DpsDataIntoFigaroResponse(
                FigaroServiceConstants.FIGARO_SERVICE_FAILURE_CD,
                FigaroServiceConstants.FIGARO_SERVICE_BOOLEAN_FALSE);
    }

    public static DpsDataIntoFigaroResponse SuccessResponse(String respCodeStr, String respMsg) {

        return new DpsDataIntoFigaroResponse(Integer.parseInt(respCodeStr), respMsg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DpsDataIntoFigaroResponse {\n");

        sb.append("    respCode: ").append(respCode).append("\n");
        sb.append("    respMsg: ").append(respMsg).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
