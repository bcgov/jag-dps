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

    public static final String NAMESPACE_URI = "https://github.com/bcgov/jag-dps";
    public static final String OUTPUT_NOTIFICATION_VALUE = "outputNotification";
    public static final String OUTPUT_NOTIFICATION_XSD = OUTPUT_NOTIFICATION_VALUE + ".xsd";
    public static final String OUTPUT_NOTIFICATION_PORT = StringUtils.capitalize(OUTPUT_NOTIFICATION_VALUE) + "Port";

    public static final String OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE = "0";
    public static final String OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE = "success";

}
