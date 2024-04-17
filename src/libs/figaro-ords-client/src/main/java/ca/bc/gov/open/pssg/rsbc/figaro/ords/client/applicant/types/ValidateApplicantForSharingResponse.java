package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * model class for response messages for /validateApplicantForSharing requests
 *
 * @author archanasudha
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateApplicantForSharingResponse {

    private String validationResult;
    private int respCode;
    private String respMsg;

    public ValidateApplicantForSharingResponse(String validationResult, int respCode, String respMsg) {
        super();
        this.validationResult = validationResult;
        this.respMsg = respMsg;
        this.respCode = respCode;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public static ValidateApplicantForSharingResponse errorResponse(String errorMessage) {
        return new ValidateApplicantForSharingResponse(
                FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE,
                FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
                errorMessage);
    }

    public static ValidateApplicantForSharingResponse successResponse(String validationResult, String respCodeStr, String respMsg) {

        return new ValidateApplicantForSharingResponse(validationResult, Integer.parseInt(respCodeStr), respMsg);
    }
}


