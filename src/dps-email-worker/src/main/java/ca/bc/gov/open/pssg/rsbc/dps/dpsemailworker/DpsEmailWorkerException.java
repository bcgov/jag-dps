package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

public class DpsEmailWorkerException extends RuntimeException {


    public DpsEmailWorkerException(String message) {
        super(message);
    }

    public DpsEmailWorkerException(String message, Throwable cause) {
        super(message, cause);
    }
}
