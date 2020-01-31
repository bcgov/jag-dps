package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

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
     * DO NOT CHANGE - The default output notification value.
     */
    public static final String OUTPUT_NOTIFICATION_VALUE = "outputNotification";

    /**
     * DO NOT CHANGE - The xsd files that defines requests and responses.
     */
    public static final String OUTPUT_NOTIFICATION_XSD = OUTPUT_NOTIFICATION_VALUE + ".xsd";

    /**
     * The name of the SOAP port.
     */
    public static final String OUTPUT_NOTIFICATION_PORT = StringUtils.capitalize(OUTPUT_NOTIFICATION_VALUE) + "Port";

    /**
     * The name of the request xml object.
     */
    public static final String OUTPUT_NOTIFICATION_REQUEST = OUTPUT_NOTIFICATION_VALUE + "Request";


    // **************** APPLICATION CONFIGURATION ****************

    /**
     * The output notification success code.
     */
    public static final String OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE = "0";

    /**
     * The output notification success message.
     */
    public static final String OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE = "success";

}
