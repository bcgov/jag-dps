package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

/**
 * 
 * Figaro Validation service constants. 
 * 
 * @author shaunmillargov
 *
 */
public class FigaroValidationServiceConstants {
	
	private static final String VALIDATION_CORRELATION_ID = "correlationId";
	
	// Response code types
	public static final int VALIDATION_SERVICE_FAILURE_CD = 0;
	public static final int VALIDATION_SERVICE_SUCCESS_CD = 1;
	
	
	// Response message types
	public static final String VALIDATION_SERVICE_RESP_MSG_FAIL = "fail";
	
	// Failure message types
	public static final String VALIDATION_SERVICE_ERR_MISSING_PARAM = "Missing %s parameter";
	public static final String VALIDATION_SERVICE_ERR_MISSING_CONFIG_PARAMS = "Missing application configuration parameters. Check Validation Service setup.";
	
}

