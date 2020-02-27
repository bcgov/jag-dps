package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * ValidateOrgApplicantServiceResponse class
 * 
 * @author shaunmillargov
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateOrgApplicantServiceResponse {

	private String respMsg;
	private int respCode;
	private String validationResult;

	public ValidateOrgApplicantServiceResponse(String validationResult, int respCode, String respMsg) {
		super();
		this.respMsg = respMsg;
		this.respCode = respCode;
		this.validationResult = validationResult;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public int getRespCode() {
		return respCode;
	}

	public String getValidationResult() {
		return validationResult;
	}

	public static ValidateOrgApplicantServiceResponse ErrorResponse(String errorMessage) {
		return new ValidateOrgApplicantServiceResponse(
				FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE,
				FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
				errorMessage);
	}

	public static ValidateOrgApplicantServiceResponse SuccessResponse(String validationResult, String respCodeStr, String respMsg) {

		return new ValidateOrgApplicantServiceResponse(validationResult, Integer.parseInt(respCodeStr), respMsg);
	}
	
}


