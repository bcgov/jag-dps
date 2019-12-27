package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * SinglePaymentResponse 
 * 
 * @author 176899
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
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public int getRespCode() {
		return respCode;
	}
	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}
	public String getRespValue() {
		return respValue;
	}
	public void setRespValue(String respValue) {
		this.respValue = respValue;
	}
}
