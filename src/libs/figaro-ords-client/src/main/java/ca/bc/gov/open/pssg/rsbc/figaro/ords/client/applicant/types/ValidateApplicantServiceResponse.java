package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * ValidateApplicantServiceResponse class
 * 
 * @author shaunmillargov
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateApplicantServiceResponse {

	private String respMsg;
	private int respCode;
	private String validationResult;

	public ValidateApplicantServiceResponse(String respMsg, int respCode, String validationResult) {
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
	
}


