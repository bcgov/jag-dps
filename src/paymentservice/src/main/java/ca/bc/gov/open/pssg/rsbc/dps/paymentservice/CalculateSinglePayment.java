package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;


/**
 * 
 * CalculateSinglePayment Controller.
 * 
 * @author 176899
 *
 */
@RestController
public class CalculateSinglePayment {

  /**
   * 
   * singlepaymenturl 
   * 
   * WARNING: Do not modify parameters. This operation must replicate legacy system exactly. 
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
   */
  @RequestMapping("/getSinglePaymentURL")
  public SinglePaymentResponse singlepaymenturl(
		  @RequestParam(value="transType", required = true) String transType,
		  @RequestParam(value="invoiceNumber", required = true) String invoiceNumber,
		  @RequestParam(value="approvedPage", required = true) String approvedPage,
		  @RequestParam(value="declinedPage", required = true) String declinedPage,
		  @RequestParam(value="errorPage", required = true) String errorPage,
		  @RequestParam(value="totalItemsAmount", required = true) String totalItemsAmount,
		  @RequestParam(value="ref1", required = false) String ref1,
		  @RequestParam(value="ref2", required = false) String ref2,
		  @RequestParam(value="ref3", required = false) String ref3,
		  @RequestParam(value="minutesToExpire", required = true) String minutesToExpire) {
	  
	  //TODO - Complete main algorithm and logging 
	  
	  return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK, PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD, "https://mylongpaymenturl");
	  
  }
  
  /**
   * handleMissingParams - Missing parameter handler. 
   * 
   * Returns legacy system equivalent response when required parameters missing from request. 
   * 
   * @param ex
   * @return
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public SinglePaymentResponse handleMissingParams(MissingServletRequestParameterException ex) {
      
	  // TODO - Add appropriate logging here.  
	  
	  String paramName = ex.getParameterName();
      // log.fatal (....
     
	 String errMsg = String.format(PaymentServiceConstants.PAYMENT_SERVICE_ERR_MISSING_PARAM, paramName);
	  
	 return new SinglePaymentResponse(errMsg, PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD, null);
      
  }
}