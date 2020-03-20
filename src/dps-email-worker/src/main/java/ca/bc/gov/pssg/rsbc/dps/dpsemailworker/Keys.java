package ca.bc.gov.pssg.rsbc.dps.dpsemailworker;

public class Keys {
    private Keys() {}


    /**
     * The DPS Email value for usage across the app
     */
    public static final String EMAIL_VALUE = "${DPS_TENANT}";

    /**
     * DO NOT CHANGE - The name of the DPS email queue
     */
    public static final String EMAIL_QUEUE_NAME = EMAIL_VALUE + "_q";

    /**
     * DO NOT CHANGE - The default output notification value.
     */
    public static final String OUTPUT_NOTIFICATION_VALUE = "emailMessage";

}
