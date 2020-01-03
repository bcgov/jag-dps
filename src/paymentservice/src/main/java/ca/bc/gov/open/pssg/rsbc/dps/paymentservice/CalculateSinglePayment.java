package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
	 * WARNING: Do not modify input parameters. This operation must replicate legacy
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
	@RequestMapping(value = "/getSinglePaymentURL",
			  produces = { "application/xml" }, 
			  method = RequestMethod.GET)
			  @ApiOperation(value = "Generate a Payment endpoint URL", notes = "", response = SinglePaymentResponse.class, tags={ "PaymentServices"})
			  @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = SinglePaymentResponse.class) })
	public SinglePaymentResponse singlepaymenturl(
			@ApiParam(value = "transType", required = true) @RequestParam(value = "transType") String transType,
			@ApiParam(value = "invoiceNumber", required = true) @RequestParam(value = "invoiceNumber") String invoiceNumber,
			@ApiParam(value = "approvedPage", required = true) @RequestParam(value = "approvedPage") String approvedPage,
			@ApiParam(value = "declinedPage", required = true) @RequestParam(value = "declinedPage") String declinedPage,
			@RequestParam(value = "errorPage", required = true) String errorPage,
			@RequestParam(value = "totalItemsAmount", required = true) String totalItemsAmount,
			@ApiParam(value = "ref1", required = false) @RequestParam(value = "ref1", required = false) String ref1,
			@ApiParam(value = "ref2", required = false) @RequestParam(value = "ref2", required = false) String ref2,
			@ApiParam(value = "ref3", required = false) @RequestParam(value = "ref3", required = false) String ref3,
			@ApiParam(value = "minutesToExpire", required = true) @RequestParam(value = "minutesToExpire", required = true) String minutesToExpire) {

		PaymentClient client = null;

		try {
			
			// Make sure autowired params are not null (May be missing from configMap)
			if ( StringUtils.isEmpty(hostedPaymentEndpoint) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(hashKey)) 
				throw new PaymentServiceException(PaymentServiceConstants.PAYMENT_SERVICE_ERR_MISSING_CONFIG_PARAMS);

			client = new BamboraClientImpl(new URL(hostedPaymentEndpoint), merchantId, hashKey,
					Integer.parseInt(minutesToExpire));

			URL response = client.calculateSinglePaymentURL(new SinglePaymentRequest(null, BamboraTransType.valueOf(transType), invoiceNumber,
					Double.parseDouble(totalItemsAmount), null, null, approvedPage, declinedPage, errorPage, ref1, ref2,
					ref3));

			return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
					PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD, response.toExternalForm());

		} catch (PaymentServiceException | NumberFormatException | MalformedURLException e) {

			logger.fatal("Exception in singlepaymenturl : " + e.getMessage());
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
