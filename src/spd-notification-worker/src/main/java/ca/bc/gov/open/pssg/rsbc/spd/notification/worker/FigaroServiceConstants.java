package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

/**
 * 
 * Figaro Figaro service constants.
 * 
 * @author carolcarpenterjustice
 *
 */
public class FigaroServiceConstants {

	private FigaroServiceConstants() {}

	// Response code types; anything >= 0 is good, anything < 0 is bad. 
	public static final int FIGARO_SERVICE_FAILURE_CD = -1;
	public static final int FIGARO_SERVICE_SUCCESS_CD = 0;
	
	// Response message types
	public static final String FIGARO_SERVICE_RESP_MSG_FAIL = "fail";

	// boolean type constants
	public static final String FIGARO_SERVICE_BOOLEAN_FALSE = "F";
	public static final String FIGARO_SERVICE_BOOLEAN_TRUE = "T";
	
	
	
}

