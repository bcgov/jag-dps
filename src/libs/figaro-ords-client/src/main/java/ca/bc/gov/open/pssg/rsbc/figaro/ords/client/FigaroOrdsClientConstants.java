package ca.bc.gov.open.pssg.rsbc.figaro.ords.client;

/**
 * 
 * Figaro Validation service constants. 
 * 
 * @author shaunmillargov
 *
 */
public class FigaroOrdsClientConstants {

	private FigaroOrdsClientConstants() {}

	// Response code types; anything >= 0 is good, anything < 0 is bad. 
	public static final int SERVICE_FAILURE_CD = -1;
	public static final int SERVICE_SUCCESS_CD = 0;

	// boolean type constants
	public static final String SERVICE_BOOLEAN_FALSE = "F";
	public static final String SERVICE_BOOLEAN_TRUE = "T";
	
	
	
}

