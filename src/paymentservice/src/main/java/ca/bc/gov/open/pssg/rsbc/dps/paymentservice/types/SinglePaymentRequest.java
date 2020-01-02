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
	private Double totalItemsAmount = new Double(0);
	private Double totalGST = new Double(0);
	private Double totalPST = new Double(0); 
	private String approvedPage; 
	private String declinedPage;
	private String errorPage;
	private String ref1;
	private String ref2;
	private String ref3;
	
	public SinglePaymentRequest(String correlationId, BamboraTransType transType, String invoiceNumber, Double totalItemsAmount, Double totalGST, 
			Double totalPST, String approvedPage, String declinedPage, String errorPage, String ref1, String ref2, String ref3) {
		this.correlationId = correlationId;
		this.transType = transType;
		this.invoiceNumber = invoiceNumber; 
		this.totalItemsAmount = totalItemsAmount;
		if (null != totalGST) 
			this.totalGST = totalGST; 
		if (null != totalPST) 
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

	public Double getTotalItemsAmount() {
		return totalItemsAmount;
	}

	public Double getTotalGST() {
		return totalGST;
	}

	public Double getTotalPST() {
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
