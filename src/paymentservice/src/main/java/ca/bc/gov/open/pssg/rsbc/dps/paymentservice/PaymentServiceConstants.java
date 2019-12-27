package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

/**
 * 
 * PaymentServiceConstants
 * 
 * @author smillar
 *
 */
public class PaymentServiceConstants {
	
	// Response code types
	public static final int PAYMENT_SERVICE_SUCCESS_CD = 0;
	public static final int PAYMENT_SERVICE_FAILURE_CD = -1;
	
	// Response message types
	public static final String PAYMENT_SERVICE_RESP_MSG_OK = "success";
	public static final String PAYMENT_SERVICE_RESP_MSG_FAIL = "fail";
	
	// Failure message types
	public static final String PAYMENT_SERVICE_ERR_MISSING_PARAM = "Missing %s parameter";
}

