package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Bambora Client class Implementation
 *
 * <p>
 * May be expanded in the future to include other types of Bambora transactions.
 * </p>
 *
 * @author smillar
 */
public class BamboraClientImpl implements PaymentClient {

    private static final Logger logger = LogManager.getLogger(BamboraClientImpl.class);

    private URL hostedPaymentURL;
    private String merchantId;
    private String hashKey;
    private int minutesToExpire;

    // constructor
    public BamboraClientImpl(URL hostedPaymentURL, String merchantId, String hashKey, int minutesToExpire) throws PaymentServiceException {
        this.hostedPaymentURL = hostedPaymentURL;
        this.merchantId = merchantId;
        this.hashKey = hashKey;
        this.minutesToExpire = minutesToExpire;
    }

    /**
     * Calculate Single Payment URL
     * <p>
     * Note: Refer to See https://help.na.bambora.com/hc/en-us/articles/115010303987-Hash-validation-for-Checkout for
     * a complete
     * description on the Payment URL calculation.
     *
     * @param spr
     * @return
     * @throws PaymentServiceException
     */
    @Override
    public URL calculateSinglePaymentURL(SinglePaymentRequest spr) throws MalformedURLException {
        return new URL(MessageFormat.format("{0}?{1}", this.hostedPaymentURL, buildQueryString(spr)));
    }

    private String buildQueryString(SinglePaymentRequest spr) {
        ArrayList parameters = new ArrayList<String>();

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_MERCHANT_ID, this.merchantId));

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_TRANS_TYPE, spr.getTransType().toString()));

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_TRANS_ORDER_NUMBER, spr.getInvoiceNumber()));

        if(!StringUtils.isBlank(spr.getErrorPage()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_ERROR_PAGE, spr.getErrorPage()));

        if(!StringUtils.isBlank(spr.getApprovedPage()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_APPV_PAGE, spr.getApprovedPage()));

        if(!StringUtils.isBlank(spr.getDeclinedPage()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_DECL_PAGE, spr.getDeclinedPage()));

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_TRANS_AMOUNT, formatTotalAmount(spr.getTotalItemsAmount())));

        if(!StringUtils.isBlank(spr.getRef1()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_REF1, spr.getRef1()));

        if(!StringUtils.isBlank(spr.getRef2()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_REF2, spr.getRef2()));

        if(!StringUtils.isBlank(spr.getRef3()))
            parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_REF3, spr.getRef3()));

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_HASH_EXPIRY, getExpiryDate()));

        parameters.add(formatParam(PaymentServiceConstants.BAMBORA_PARAM_HASH_VALUE, getHash(String.join("&", parameters))));

        return String.join("&", parameters);

    }

    private String formatTotalAmount(Double value) {
        return String.format("%1$,.2f", value);
    }

    private String formatParam(String paramName, String value) {
        return MessageFormat.format("{0}={1}", paramName, value);
    }

    private String getExpiryDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(PaymentServiceConstants.BAMBORA_PARAM_HASH_EXPIRY_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, this.minutesToExpire);
        return sdfDate.format(cal.getTime());
    }


    /**
     * getHash - Calculates an MD5 hash on a message with a given key.
     *
     * @param message
     * @return
     * @throws PaymentServiceException
     */
    private String getHash(String message) {
        return DigestUtils.md5Hex(message + this.hashKey).toUpperCase();
    }

}
