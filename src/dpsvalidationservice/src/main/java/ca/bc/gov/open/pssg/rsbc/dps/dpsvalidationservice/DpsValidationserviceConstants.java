package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice;

/**
 * @author nancymz
 * Constants used in DPS Validation Service
 */
public class DpsValidationserviceConstants {
    public static String VALIDOPEN_DFCMCASE_RESPONSE = "<GetValidOpenDFCMCase>" +
            "                <int>%s</int>" +
            "                <caseDesc>%s</caseDesc>" +
            "                </GetValidOpenDFCMCase>";

    public static String VALIDOPEN_DFCMCASE_ERR_RESPONSE = "<GetValidOpenDFCMCase>" +
            "                <int>%s</int>" +
            "                </GetValidOpenDFCMCase>";

}
