package ca.bc.gov.dps.monitoring;

import org.slf4j.*;

/***
 * A service to help capturing notification messages for system errors.
 * Do not put any confidential information when you log system errors.
 *
 * @author alexjoybc@github.com
 */
public class NotificationService {

    protected NotificationService(){}

    private static final String TYPE_KEY = "type";
    private static final String TO_KEY = "to";
    private static final String NOTIFICATION = "notification";

    /**
     * Wraps a log message with Mapped Diagnostic Context
     * @param to
     * @param loggerAction
     */
    public static void notify(String to, LoggerAction loggerAction) {

        Logger logger =  LoggerFactory.getLogger(NotificationService.class);

        MDC.put(TYPE_KEY, NOTIFICATION);
        MDC.put(TO_KEY, to);

        loggerAction.Log(logger);

        MDC.remove(TYPE_KEY);
        MDC.remove(TO_KEY);

    }

}
