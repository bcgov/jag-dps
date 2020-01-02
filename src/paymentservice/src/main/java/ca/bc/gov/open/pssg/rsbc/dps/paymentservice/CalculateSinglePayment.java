package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * CalculateSinglePayment Controller.
 * 
 * @author smillar
 *
 */
@RestController
public class CalculateSinglePayment {

	private static final Logger logger = LogManager.getLogger(CalculateSinglePayment.class);

	@Value("${bambora.hostedpaymentendpoint}")
	private String hostedPaymentEndpoint;

	@Value("${bambora.merchantid}")
	private String merchantId;

	@Value("${bambora.hashkey}")
	private String hashKey;

	/**
	 * 
	 * singlepaymenturl
	 * 
	 * WARNING: Do not modify parameters. This operation must replicate legacy
	 * system exactly.
	 * 
	 * Note: In the event of an exception, this methods will return an HTTP status
	 * code of 200. THIS IS EXPECTED AND WHAT THE LEGACY SYSTEM DOES. PLEASE DO NOT
	 * CHANGE THIS. An error is indicated to the calling system by the -1 status
	 * code in the response structure.
	 * 
	 * @param transType
	 * @param invoiceNumber
	 * @param approvedPage
	 * @param declinedPage
	 * @param errorPage
	 * @param totalItemsAmount
	 * @param ref1
	 * @param ref2
	 * @param ref3
	 * @param minutesToExpire
	 * @return
	 * @throws PaymentServiceException 
	 */
	@RequestMapping("/getSinglePaymentURL")
	public SinglePaymentResponse singlepaymenturl(@RequestParam(value = "transType", required = true) String transType,
			@RequestParam(value = "invoiceNumber", required = true) String invoiceNumber,
			@RequestParam(value = "approvedPage", required = true) String approvedPage,
			@RequestParam(value = "declinedPage", required = true) String declinedPage,
			@RequestParam(value = "errorPage", required = true) String errorPage,
			@RequestParam(value = "totalItemsAmount", required = true) String totalItemsAmount,
			@RequestParam(value = "ref1", required = false) String ref1,
			@RequestParam(value = "ref2", required = false) String ref2,
			@RequestParam(value = "ref3", required = false) String ref3,
			@RequestParam(value = "minutesToExpire", required = true) String minutesToExpire) {

		// TODO - Complete logging once available.

		PaymentClient client = null;

		try {
			
			// Make sure autowired params are not null (May be missing from configMap)
			if ( StringUtils.isEmpty(hostedPaymentEndpoint) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(hashKey)) 
				throw new PaymentServiceException(PaymentServiceConstants.PAYMENT_SERVICE_ERR_MISSING_CONFIG_PARAMS);

			client = new BamboraClientImpl(new URL(hostedPaymentEndpoint), merchantId, hashKey,
					Integer.parseInt(minutesToExpire));

			URL response = client.calculateSinglePaymentURL(null, BamboraTransType.valueOf(transType), invoiceNumber,
					Double.parseDouble(totalItemsAmount), 0L, 0L, approvedPage, declinedPage, errorPage, ref1, ref2,
					ref3);

			return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
					PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD, response.toExternalForm());

		} catch (PaymentServiceException | NumberFormatException | MalformedURLException e) {
			// TODO - Complete logging once available.
			e.printStackTrace();
			return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL,
					PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD, e.getMessage());
		}

	}

	/**
	 * handleMissingParams - Missing parameter handler.
	 * 
	 * Note: This method, when invoked in the absence of a required parameter, will
	 * return an HTTP status code of 200. THIS IS EXPECTED AND WHAT THE LEGACY
	 * SYSTEM DOES. PLEASE DO NOT CHANGE THIS.
	 * 
	 * Returns legacy system equivalent response when required parameters missing
	 * from request.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public SinglePaymentResponse handleMissingParams(MissingServletRequestParameterException ex) {

		String paramName = ex.getParameterName();
		logger.fatal("Exception in SinglePaymentResponse : " + ex.getMessage());
		String errMsg = String.format(PaymentServiceConstants.PAYMENT_SERVICE_ERR_MISSING_PARAM, paramName);

		return new SinglePaymentResponse(errMsg, PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD, null);

	}
}
