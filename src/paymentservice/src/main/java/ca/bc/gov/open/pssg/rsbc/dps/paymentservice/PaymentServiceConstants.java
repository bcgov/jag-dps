package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

/**
 * 
 * PaymentServiceConstants
 * 
 * @author smillar
 *
 */
public class PaymentServiceConstants {


	private PaymentServiceConstants() {}

	
	// Response code types
	public static final int PAYMENT_SERVICE_SUCCESS_CD = 0;
	public static final int PAYMENT_SERVICE_FAILURE_CD = -1;
	
	// Response message types
	public static final String PAYMENT_SERVICE_RESP_MSG_OK = "success";
	public static final String PAYMENT_SERVICE_RESP_MSG_FAIL = "fail";
	
	// Failure message types
	public static final String PAYMENT_SERVICE_ERR_MISSING_PARAM = "Missing %s parameter";

	// Bambora specific parameter names
	public static final String BAMBORA_PARAM_MERCHANT_ID = "merchant_id";
	public static final String BAMBORA_PARAM_TRANS_AMOUNT = "trnAmount";
	public static final String BAMBORA_PARAM_TRANS_ORDER_NUMBER = "trnOrderNumber";
	public static final String BAMBORA_PARAM_TRANS_TYPE = "trnType";
	public static final String BAMBORA_PARAM_ERROR_PAGE = "errorPage";
	public static final String BAMBORA_PARAM_APPV_PAGE = "approvedPage";
	public static final String BAMBORA_PARAM_DECL_PAGE = "declinedPage";
	public static final String BAMBORA_PARAM_REF1 = "ref1";
	public static final String BAMBORA_PARAM_REF2 = "ref2";
	public static final String BAMBORA_PARAM_REF3 = "ref3";
	public static final String BAMBORA_PARAM_HASH_VALUE = "hashValue";
		
	// The format of the field must be passed as YYYYMMDDHHMM. Example June 18, 2008 2:34 PM would be submitted as 200806181434.
	public static final String BAMBORA_PARAM_HASH_EXPIRY = "hashExpiry";
	public static final String BAMBORA_PARAM_HASH_EXPIRY_FORMAT = "yyyyMMddkkmm";
	
	
	// Bambora Transaction types (Purchase, Pre-Auth)
	public enum BamboraTransType {
		P, PA 
	}

}

