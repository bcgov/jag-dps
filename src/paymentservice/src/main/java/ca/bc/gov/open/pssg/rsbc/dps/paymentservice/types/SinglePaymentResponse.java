package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * SinglePaymentResponse 
 * 
 * @author smillar
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SinglePaymentResponse {

	private String respMsg;
	private int respCode;
	private String respValue;
	
	public SinglePaymentResponse(String respMsg, int respCode, String respValue) {
		this.respMsg = respMsg;
		this.respCode = respCode; 
		this.respValue = respValue; 
	}
	
	public String getRespMsg() {
		return respMsg;
	}

	public int getRespCode() {
		return respCode;
	}

	public String getRespValue() {
		return respValue;
	}
}
