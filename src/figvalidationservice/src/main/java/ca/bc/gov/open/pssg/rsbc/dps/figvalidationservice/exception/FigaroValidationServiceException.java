package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception;

/**
 * 
 * FigaroValidationServiceException class
 * 
 * @author shaunmillargov
 *
 */
public class FigaroValidationServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250088612082352045L;

	public FigaroValidationServiceException(Throwable reason) {
        super(reason);
    }
	
	public FigaroValidationServiceException(String message) {
		super(message);
	}
}

