package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller;

public class Keys {

    private Keys(){}

    public static final String TENANT = "${DPS_TENANT}";

    public static final String APP_NAME = TENANT + "-email-poller";

    public static final String PDF_CONTENT_TYPE = "application/pdf";

}
