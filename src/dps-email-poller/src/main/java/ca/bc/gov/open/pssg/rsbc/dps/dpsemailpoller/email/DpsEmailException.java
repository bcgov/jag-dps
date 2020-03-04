package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.email;

public class DpsEmailException extends RuntimeException {

    public DpsEmailException(String message) {
        super(message);
    }

    public DpsEmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
