package ca.bc.gov.dps.monitoring;

import org.slf4j.*;

import java.text.MessageFormat;
import java.util.Map;

/***
 * A service to help capturing notification messages for system errors.
 * Do not put any confidential information when you log system errors.
 *
 * @author alexjoybc@github.com
 */
public class NotificationService {


    private static final String DEFAULT_TYPE = "UNKNOWN";
    private static final String PREFIX_PATTERN = "notification.{0}";
    private static final String IS_NOTIFICATION_KEY = "display";
    public static final String IS_NOTIFICATION_VALUE = "true";
    private static final String NOTIFICATION_TYPE = "type";

    protected NotificationService(){}

    /**
     * Wraps a log message with Mapped Diagnostic Context
     * @param SystemNotification
     */
    public static void notify(SystemNotification systemNotification) {

        Logger logger =  LoggerFactory.getLogger(NotificationService.class);

        MDC.put(buildKey(IS_NOTIFICATION_KEY), IS_NOTIFICATION_VALUE);

        systemNotification.toMap().forEach((key, value) -> MDC.put(buildKey(key), value));

        switch (systemNotification.getLevel()) {
            case INFO:
                logger.info(systemNotification.getMessage());
                break;
            case WARN:
                logger.warn(systemNotification.getMessage());
                break;
            case DEBUG:
                logger.debug(systemNotification.getMessage());
                break;
            case ERROR:
                logger.error(systemNotification.getMessage());
                break;
            case TRACE:
                logger.info(systemNotification.getMessage());
                break;
        }

        systemNotification.toMap().forEach((key, value) -> MDC.remove(buildKey(key)));
        MDC.remove(buildKey(IS_NOTIFICATION_KEY));

    }

    private static String buildKey(String key) {
        return MessageFormat.format(PREFIX_PATTERN, key);
    }


}
