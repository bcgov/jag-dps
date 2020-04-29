package ca.bc.gov.open.pssg.rsbc.error;

public enum DpsError {

    KOFAX_ERROR("KOFAX ERROR"),
    EMAIL_PARSING_ERROR("EMAIL PARSING ERROR");

    private String code;

    DpsError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
