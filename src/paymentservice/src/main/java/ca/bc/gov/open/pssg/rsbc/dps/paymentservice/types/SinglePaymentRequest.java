package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants.BamboraTransType;

/**
 * SinglePaymentResponse object
 * 
 * @author smillar
 *
 */
public class SinglePaymentRequest {
	
	private String correlationId;
	private BamboraTransType transType;
	private String invoiceNumber;
	private double totalItemsAmount;
	private double totalGST;
	private double totalPST; 
	private String approvedPage; 
	private String declinedPage;
	private String errorPage;
	private String ref1;
	private String ref2;
	private String ref3;
	
	public SinglePaymentRequest(String correlationId, BamboraTransType transType, String invoiceNumber, double totalItemsAmount, double totalGST, 
			double totalPST, String approvedPage, String declinedPage, String errorPage, String ref1, String ref2, String ref3) {
		this.correlationId = correlationId;
		this.transType = transType;
		this.invoiceNumber = invoiceNumber; 
		this.totalItemsAmount = totalItemsAmount;
		this.totalGST = totalGST; 
		this.totalPST = totalPST;
		this.approvedPage = approvedPage; 
		this.declinedPage = declinedPage;
		this.errorPage = errorPage;
		this.ref1 = ref1; 
		this.ref2 = ref2; 
		this.ref3 = ref3;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public BamboraTransType getTransType() {
		return transType;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public double getTotalItemsAmount() {
		return totalItemsAmount;
	}

	public double getTotalGST() {
		return totalGST;
	}

	public double getTotalPST() {
		return totalPST;
	}

	public String getApprovedPage() {
		return approvedPage;
	}

	public String getDeclinedPage() {
		return declinedPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public String getRef1() {
		return ref1;
	}

	public String getRef2() {
		return ref2;
	}

	public String getRef3() {
		return ref3;
	}
}
