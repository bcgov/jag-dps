package ca.bc.gov.open.pssg.rsbc.error;

public class DpsException extends RuntimeException {

    private DpsError dpsError;

    public DpsException(DpsError dpsError) {
        this.dpsError = dpsError;
    }

    public DpsException(String message, DpsError dpsError) {
        super(message);
        this.dpsError = dpsError;
    }

    public DpsException(String message, Throwable cause, DpsError dpsError) {
        super(message, cause);
        this.dpsError = dpsError;
    }

    public DpsException(Throwable cause, DpsError dpsError) {
        super(cause);
        this.dpsError = dpsError;
    }

    public DpsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                        DpsError dpsError) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.dpsError = dpsError;
    }

    public DpsError getDpsError() {
        return dpsError;
    }
}
