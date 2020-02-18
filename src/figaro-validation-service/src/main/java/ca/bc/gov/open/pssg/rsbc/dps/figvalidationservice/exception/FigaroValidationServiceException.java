package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception;

/**
 * 
 * FigaroValidationServiceException class
 * 
 * Custom exception for Figaro Validation Operations. 
 * 
 * @author shaunmillargov
 *
 */
public class FigaroValidationServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250088612082352045L;

	public FigaroValidationServiceException(String message) {
		super(message);
	}

	public FigaroValidationServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
