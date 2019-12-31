package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;

/**
 * Bambora Client class Implementation
 * 
 * May be expanded in the future to include other types of Bambora transactions. 
 * 
 * @author smillar
 *
 */
public class BamboraClientImpl implements PaymentClient {

	private URL hostedPaymentURL = null;
	private String merchantId = null;
	private String hashKey = null;
	private int minutesToExpire;
	private String correlationId = null;
	
	public URL getHostedPaymentServiceURL() {
		return hostedPaymentURL;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public URL getHostedPaymentURL() {
		return hostedPaymentURL;
	}

	public int getMinutesToExpire() {
		return minutesToExpire;
	}
	
	public String getCorrelationId() {
		return correlationId;
	}

	// constructor
	public BamboraClientImpl(URL hostedPaymentURL, String merchantId, String hashKey, int minutesToExpire) throws PaymentServiceException {
		this.hostedPaymentURL = hostedPaymentURL;
		this.merchantId = merchantId;
		this.hashKey = hashKey;
		this.minutesToExpire = minutesToExpire;
	}

	/**
	 * 
	 * Calculate Single Payment URL 
	 * 
	 * @param correlationId
	 * @param transType
	 * @param invoiceNumber
	 * @param totalItemsAmount
	 * @param totalGST
	 * @param totalPST
	 * @param approvedPage
	 * @param declinedPage
	 * @param errorPage
	 * @param ref1
	 * @param ref2
	 * @param ref3
	 * @return
	 * @throws PaymentServiceException
	 */
	@Override
	public URL calculateSinglePaymentURL(String correlationId, BamboraTransType transType, String invoiceNumber, double totalItemsAmount, double totalGST, 
			double totalPST, String approvedPage, String declinedPage, String errorPage, String ref1, String ref2, String ref3)
			throws PaymentServiceException {
		
		// add correlationId if non provided in request. 
		if (StringUtils.isEmpty(correlationId)) {
			this.correlationId = generateUniqueCorrelationId();
	    }
		
		String redirect = null;
		
		try {
	
			// Add the optional parameters if available.
			String errorParam = (errorPage != null) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_ERROR_PAGE + "=" + errorPage
					: "";
			String declinedParam = (approvedPage != null)
					? "&" + PaymentServiceConstants.BAMBORA_PARAM_APPV_PAGE + "=" + approvedPage
					: "";
			String approvedParam = (declinedPage != null)
					? "&" + PaymentServiceConstants.BAMBORA_PARAM_DECL_PAGE + "=" + declinedPage
					: "";
			String endUserIdParam = (ref1 != null) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF1 + "=" + ref1 : "";
			String statementParam = (ref2 != null) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF2 + "=" + ref2 : "";
			String echoParam = (ref3 != null) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF3 + "=" + ref3 : "";
	
			// Create the base url
			String paramString = PaymentServiceConstants.BAMBORA_PARAM_MERCHANT_ID + "=" + this.merchantId + "&"
					+ PaymentServiceConstants.BAMBORA_PARAM_TRANS_TYPE + "=" + transType.toString() + "&"
					+ PaymentServiceConstants.BAMBORA_PARAM_TRANS_ORDER_NUMBER + "=" + invoiceNumber +
					errorParam + approvedParam + declinedParam + endUserIdParam + statementParam + echoParam +
						"&" + PaymentServiceConstants.BAMBORA_PARAM_TRANS_AMOUNT + "="
					+ String.format("%1$,.2f", totalItemsAmount + totalPST + totalGST);
	
			// Replace spaces with escaped equivalent
			paramString = paramString.replace(" ", "%20");
	
			// Add hash key at end of params
			paramString = paramString + this.hashKey;
	
			// Calculate the MD5 value using the Hash Key set on the Order Settings page
			// (Located on the Bambora account page).
			// 
			// How?
			// Place the hash key after the last parameter.
			// Perform an MD5 hash on the text up to the end of the key, then
			// Replace the hash key with hashValue=[hash result].
			// Add the result to the hosted service url.
			// Note: Hash is calculated on the params ONLY.. Does NOT include the hosted payment page url.
			// See https://help.na.bambora.com/hc/en-us/articles/115010303987-Hash-validation-for-Checkout
			
			 //TODO - Complete logging once available.
			//logger.info("Calculating MD5 on paramString " + paramString);
			String hashed = getHash(paramString);
	
			// Calculate the expiry based on the minutesToExpire value.
			SimpleDateFormat sdfDate = new SimpleDateFormat(PaymentServiceConstants.BAMBORA_PARAM_HASH_EXPIRY_FORMAT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, this.minutesToExpire);
			String expiry = sdfDate.format(cal.getTime());
	
			// Add hash and expiry to the redirect
			paramString = paramString.replace(this.hashKey, "&" + PaymentServiceConstants.BAMBORA_PARAM_HASH_VALUE + "=" + hashed
					+ "&" + PaymentServiceConstants.BAMBORA_PARAM_HASH_EXPIRY + "=" + expiry);
	
			redirect = this.hostedPaymentURL + "?" + paramString;
	
			 //TODO - Complete logging once available.
			//logger.info("Single Payment URL calculated as: " + redirect);
			
			return new URL(redirect);
		
		} catch (Exception ex) {
			//TODO - Complete logging once available.
			//logger.fatal("Error at calculateSinglePaymentURL: " + ex.getMessage());
			throw new PaymentServiceException(ex.getMessage());
			
		} finally {
			//TODO - Complete logging once available.
			//MDC.remove(PaymentServiceConstants.PAYMENT_CORRELATION_ID);
		}
	}

	
	/**
	 * getHash - Calculates an MD5 hash on a message with a given key.
	 * 
	 * @param message
	 * @return
	 * @throws PaymentServiceException
	 */
	private String getHash(String message) throws PaymentServiceException {
		String digest = DigestUtils.md5Hex(message);
		return digest.toUpperCase();
	}
	
	/**
	 * 
	 * generateUniqueCorrelationId - provides a new correlationId. 
	 * @return
	 */
	private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
