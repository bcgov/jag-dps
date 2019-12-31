package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import java.net.URL;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;

/**
 * 
 * Payment Client interface
 * 
 * @author smillar
 *
 */
public interface PaymentClient {

	/**
	 * 
	 * Single Payment
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
	public URL calculateSinglePaymentURL(String correlationId, BamboraTransType transType, String invoiceNumber,
			double totalItemsAmount, double totalGST, double totalPST, String approvedPage, String declinedPage,
			String errorPage, String ref1, String ref2, String ref3)
			throws PaymentServiceException;

}

