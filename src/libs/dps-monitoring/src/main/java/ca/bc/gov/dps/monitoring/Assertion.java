package ca.bc.gov.dps.monitoring;

public enum Assertion {

    SUCCESS,
    FAILURE;

    public String getCode() {
        return this.name();
    }
}
