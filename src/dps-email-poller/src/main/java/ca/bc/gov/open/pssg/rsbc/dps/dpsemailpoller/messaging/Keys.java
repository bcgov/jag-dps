package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

/**
 * Holds application keys.
 *
 * @author alexjoybc@github
 *
 */
public class Keys {

    private Keys() {}

    // **************** SOAP CONFIGURATION ****************

    /**
     * DO NOT CHANGE - The default email message value.
     */
    public static final String EMAIL_MESSAGE_VALUE = "emailMessage";

    public static final String X_DEAD_LETTER_ROUTING_KEY = "message-ready-dead";

}
