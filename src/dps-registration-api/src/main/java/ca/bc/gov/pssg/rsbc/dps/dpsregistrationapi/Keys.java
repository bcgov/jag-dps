package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import org.apache.commons.lang3.StringUtils;

public class Keys {



    protected Keys() {}

    // **************** SOAP CONFIGURATION ****************

    /**
     * The xml namespace
     */
    public static final String NAMESPACE_URI = "https://github.com/bcgov/jag-dps";

    /**
     * DO NOT CHANGE - The default output notification value.
     */
    public static final String REGISTRATION_SERVICE_VALUE = "registrationService";

    /**
     * DO NOT CHANGE - The xsd files that defines requests and responses.
     */
    public static final String REGISTRATION_SERVICE_XSD = REGISTRATION_SERVICE_VALUE + ".xsd";

    /**
     * The name of the SOAP port.
     */
    public static final String REGISTRATION_SERVICE_PORT = StringUtils.capitalize(REGISTRATION_SERVICE_VALUE) + "Port";


    public static final String REGISTRATION_SERVICE_OBJECT_REQUEST = "setRegisterObjectRequest" ;

    public static final String REGISTRATION_SERVICE_PACKAGE_REQUEST = "setRegisterPackageRequest" ;

    public static final int ERROR_STATUS_CODE = -1;

}
