package ca.bc.gov.open.pssg.rsbc.dps.cache;

public class DpsRedisException extends RuntimeException {

    public DpsRedisException(String message) {
        super(message);
    }


    public DpsRedisException(String message, Throwable cause) {
        super(message, cause);
    }
}
