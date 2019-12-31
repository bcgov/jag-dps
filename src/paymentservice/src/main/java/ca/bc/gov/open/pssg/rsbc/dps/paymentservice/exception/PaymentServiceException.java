package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception;

/**
 * 
 * @author smillar
 *
 */
public class PaymentServiceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609347277221070068L;

	public PaymentServiceException(Throwable reason) {
        super(reason);
    }
	
	public PaymentServiceException(String message) {
		super(message);
	}
}
