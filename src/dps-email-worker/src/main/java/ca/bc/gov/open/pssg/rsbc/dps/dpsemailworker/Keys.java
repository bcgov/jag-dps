package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

public class Keys {


    private Keys() {}

    /**
     * The DPS Email value for usage across the app
     */
    public static final String EMAIL_VALUE = "${DPS_TENANT}";

    /**
     * DO NOT CHANGE - The name of the DPS email queue
     */
    public static final String EMAIL_QUEUE_NAME = EMAIL_VALUE + ".emailmessage.Q";

    /**
     * DO NOT CHANGE - The name of the DPS email queue
     */
    public static final String PARKING_QUEUE_NAME = EMAIL_VALUE + ".emailmessage.PL";

    /**
     * DO NOT CHANGE - The default output notification value.
     */
    public static final String OUTPUT_NOTIFICATION_VALUE = "emailMessage";

    /**
     * DO NOT CHANGE - The default kofax control folder
     */
    public static final String KOFAX_CONTROL_FOLDER = "control";

    /**
     * DO NOT CHANGE - The default registration business area
     */
    public static final String REGISTRATION_BUSINESS_AREA = "RDS";

    /**
     * DO NOT CHANGE - The default registration format
     */
    public static final String REGISTRATION_DEFAULT_FORMAT= "1";

    /**
     * DO NOT CHANGE - The default registration record count
     */
    public static final int REGISTRATION_DEFAULT_RECORD_COUNT = 1;

    /**
     * DO NOT CHANGE - The default registration success status
     */
    public static final String REGISTRATION_OPERATION_SUCCESS_STATUS = "0";
}
