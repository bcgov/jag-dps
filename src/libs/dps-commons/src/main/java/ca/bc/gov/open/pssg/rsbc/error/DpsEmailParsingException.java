package ca.bc.gov.open.pssg.rsbc.error;

public class DpsEmailParsingException extends DpsException {

    public DpsEmailParsingException() {
        super(DpsError.EMAIL_PARSING_ERROR);
    }

    public DpsEmailParsingException(String message) {
        super(message, DpsError.EMAIL_PARSING_ERROR);
    }

    public DpsEmailParsingException(String message, Throwable cause) {
        super(message, cause, DpsError.EMAIL_PARSING_ERROR);
    }

    public DpsEmailParsingException(Throwable cause) {
        super(cause, DpsError.EMAIL_PARSING_ERROR);
    }

    public DpsEmailParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, DpsError.EMAIL_PARSING_ERROR);
    }

}
