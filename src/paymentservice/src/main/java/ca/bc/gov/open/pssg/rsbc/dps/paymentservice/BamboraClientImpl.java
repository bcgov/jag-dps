package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Bambora Client class Implementation
 * 
 * May be expanded in the future to include other types of Bambora transactions. 
 * 
 * @author smillar
 *
 */
public class BamboraClientImpl implements PaymentClient {
	
	private static final Logger logger = LogManager.getLogger(BamboraClientImpl.class);

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
	 * Note: Refer to See https://help.na.bambora.com/hc/en-us/articles/115010303987-Hash-validation-for-Checkout for a complete 
	 * description on the Payment URL calculation. 
	 * 
	 * @param singlePaymentRequest
	 * @return
	 * @throws PaymentServiceException
	 */
	@Override
	public URL calculateSinglePaymentURL(SinglePaymentRequest spr)
			throws PaymentServiceException {
		
		// add correlationId if non provided in request. 
		if (StringUtils.isEmpty(spr.getCorrelationId())) {
			this.correlationId = generateUniqueCorrelationId();
		}
		
		String redirect = null;
		
		try {
	
			// Add the optional parameters if available.
			String errorParam = StringUtils.isNotEmpty(spr.getErrorPage() ) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_ERROR_PAGE + "=" + spr.getErrorPage(): "";
			String declinedParam = StringUtils.isNotEmpty( spr.getApprovedPage()) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_APPV_PAGE + "=" + spr.getApprovedPage(): "";
			String approvedParam = StringUtils.isNotEmpty( spr.getDeclinedPage()) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_DECL_PAGE + "=" + spr.getDeclinedPage(): "";
			String ref1Param = StringUtils.isNotEmpty( spr.getRef1()) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF1 + "=" + spr.getRef1() : "";
			String ref2Param = StringUtils.isNotEmpty( spr.getRef2()) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF2 + "=" + spr.getRef2() : "";
			String ref3Param = StringUtils.isNotEmpty( spr.getRef3()) ? "&" + PaymentServiceConstants.BAMBORA_PARAM_REF3 + "=" + spr.getRef3() : "";
		
			String[] params = new String[] { 
					PaymentServiceConstants.BAMBORA_PARAM_MERCHANT_ID + "=" + this.merchantId,
					PaymentServiceConstants.BAMBORA_PARAM_TRANS_TYPE + "=" + spr.getTransType().toString(),
					PaymentServiceConstants.BAMBORA_PARAM_TRANS_ORDER_NUMBER + "=" + spr.getInvoiceNumber()
			};
			
			String paramString = String.join("&", params);
			
			paramString += errorParam + approvedParam + declinedParam + ref1Param + ref2Param + ref3Param;
			paramString += "&" + PaymentServiceConstants.BAMBORA_PARAM_TRANS_AMOUNT + "=";
			paramString += String.format("%1$,.2f", spr.getTotalItemsAmount() + spr.getTotalPST() + spr.getTotalGST());
	
			// Replace spaces with escaped equivalent
			paramString = paramString.replace(" ", "%20");
	
			// Add hash key at end of params
			paramString = paramString + this.hashKey;
	
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
			return new URL(redirect);
		
		} catch (Exception ex) {
			logger.fatal("Error at calculateSinglePaymentURL: " + ex.getMessage());
			throw new PaymentServiceException(ex.getMessage());
			
		} finally {
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
