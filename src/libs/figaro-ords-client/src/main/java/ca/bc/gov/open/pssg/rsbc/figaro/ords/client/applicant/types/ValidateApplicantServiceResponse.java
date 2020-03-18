package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * ValidateApplicantServiceResponse class
 * 
 * @author shaunmillargov
 *
 */
@JacksonXmlRootElement(localName = "validateApplicantServiceResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateApplicantServiceResponse {

	private String respMsg;
	private int respCode;
	private String validationResult;

	public ValidateApplicantServiceResponse(String validationResult, int respCode, String respMsg) {
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

	public static ValidateApplicantServiceResponse errorResponse(String errorMessage) {
		return new ValidateApplicantServiceResponse(
				FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE,
				FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
				errorMessage);
	}

	public static ValidateApplicantServiceResponse successResponse(String validationResult, String respCodeStr, String respMsg) {

		return new ValidateApplicantServiceResponse(validationResult, Integer.parseInt(respCodeStr), respMsg);
	}
	
}


