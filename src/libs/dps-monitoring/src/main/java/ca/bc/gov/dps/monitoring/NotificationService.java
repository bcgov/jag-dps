package ca.bc.gov.dps.monitoring;

import org.slf4j.*;

public class NotificationService {




    public static final String TYPE_KEY = "type";
    public static final String TO_KEY = "to";
    public static final String NOTIFICATION = "";

    public static void notify(String to, LoggerAction loggerAction) {

        Logger logger =  LoggerFactory.getLogger(NotificationService.class);

        MDC.put(TYPE_KEY, NOTIFICATION);
        MDC.put(TO_KEY, to);

        loggerAction.Log(logger);

        MDC.remove(TYPE_KEY);
        MDC.remove(TO_KEY);

    }

}
