package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.controller;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentClient;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * CalculateSinglePaymentController.
 *
 * @author smillar
 */
@RestController
public class CalculateSinglePaymentController {

    private static final Logger logger = LogManager.getLogger(CalculateSinglePaymentController.class);
    private static final String DPS_INVOICE_KEY = "dps.invoice";

    private PaymentClient paymentClient;

    public CalculateSinglePaymentController(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    /**
     * singlepaymenturl
     * <p>
     * WARNING: Do not modify input parameters. This operation must replicate legacy
     * system exactly.
     * <p>
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
            produces = {"application/xml"},
            method = RequestMethod.GET)
    @ApiOperation(value = "Generate a Payment endpoint URL", notes = "", response = SinglePaymentResponse.class,
			tags = {"PaymentServices"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response =
			SinglePaymentResponse.class)})
    public SinglePaymentResponse singlepaymenturl(
            @ApiParam(value = "transType", required = true) @RequestParam(value = "transType") String transType,
            @ApiParam(value = "invoiceNumber", required = true) @RequestParam(value = "invoiceNumber") String invoiceNumber,
            @ApiParam(value = "approvedPage", required = true) @RequestParam(value = "approvedPage") String approvedPage,
            @ApiParam(value = "declinedPage", required = true) @RequestParam(value = "declinedPage") String declinedPage,
            @ApiParam(value = "errorPage", required = true) String errorPage,
            @ApiParam(value = "totalItemsAmount", required = true) String totalItemsAmount,
            @ApiParam(value = "ref1", required = false) @RequestParam(value = "ref1", required = false) String ref1,
            @ApiParam(value = "ref2", required = false) @RequestParam(value = "ref2", required = false) String ref2,
            @ApiParam(value = "ref3", required = false) @RequestParam(value = "ref3", required = false) String ref3,
            @ApiParam(value = "minutesToExpire", required = true) @RequestParam(value = "minutesToExpire", required =
					true) String minutesToExpire) {


        logger.debug("Receiving request for invoice [{}]", invoiceNumber);
        MDC.put(DPS_INVOICE_KEY, invoiceNumber);

        URL response;

        try {

            response = paymentClient.calculateSinglePaymentURL(
                    new SinglePaymentRequest.Builder()
                            .withBamboraTransType(BamboraTransType.valueOf(transType))
                            .withInvoiceNumber(invoiceNumber)
                            .withTotalItemsAmount(Double.parseDouble(totalItemsAmount))
                            .withApprovedPage(approvedPage)
                            .withDeclinedPage(declinedPage)
                            .withErrorPage(errorPage)
                            .withRef1(ref1)
                            .withRef2(ref2)
                            .withRef3(ref3)
                            .build(), Integer.parseInt(minutesToExpire));

            logger.info("Successfully generated url for invoice [{}]", invoiceNumber);



        } catch (NumberFormatException | MalformedURLException e) {

            logger.fatal("Exception in singlepaymenturl : " + e.getMessage());
            return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL,
                    PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD, e.getMessage());
        }


        MDC.remove(DPS_INVOICE_KEY);

        return new SinglePaymentResponse(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
                PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD, response.toExternalForm());

    }

    /**
     * handleMissingParams - Missing parameter handler.
     * <p>
     * Note: This method, when invoked in the absence of a required parameter, will
     * return an HTTP status code of 200. THIS IS EXPECTED AND WHAT THE LEGACY
     * SYSTEM DOES. PLEASE DO NOT CHANGE THIS.
     * <p>
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
