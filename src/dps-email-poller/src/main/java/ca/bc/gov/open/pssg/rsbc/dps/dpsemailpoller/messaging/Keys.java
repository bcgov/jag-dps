package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.messaging;

import org.apache.commons.lang3.StringUtils;

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
     * The xml namespace
     */
    public static final String NAMESPACE_URI = "https://github.com/bcgov/jag-dps";

    /**
     * DO NOT CHANGE - The default email message value.
     */
    public static final String EMAIL_MESSAGE_VALUE = "emailMessage";

    /**
     * DO NOT CHANGE - The xsd files that defines requests and responses.
     */
    public static final String EMAIL_MESSAGE_XSD = EMAIL_MESSAGE_VALUE + ".xsd";

    /**
     * The name of the SOAP port.
     */
    public static final String EMAIL_MESSAGE_PORT = StringUtils.capitalize(EMAIL_MESSAGE_VALUE) + "Port";

    /**
     * The name of the request xml object.
     */
    public static final String EMAIL_MESSAGE_REQUEST = EMAIL_MESSAGE_VALUE + "Request";


    // **************** APPLICATION CONFIGURATION ****************

    /**
     * The email message success code.
     */
    public static final String EMAIL_MESSAGE_RESPONSE_SUCCESS_CODE = "0";

    /**
     * The email message error code.
     */
    public static final String EMAIL_MESSAGE_RESPONSE_ERROR_CODE = "-1";

    /**
     * The email message success message.
     */
    public static final String EMAIL_MESSAGE_RESPONSE_SUCCESS_MESSAGE = "success";

}
