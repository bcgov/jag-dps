package ca.bc.gov.dps.monitoring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.event.Level;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SystemNotificationTest {


    public static final String CORRELATION_ID = "CORRELATION_ID";
    public static final String TRANSACTION_ID = "ID";
    public static final String APP_NAME = "APP_NAME";
    public static final String COMPONENT = "COMPONENT";
    public static final String MESSAGE = "MESSAGE";
    public static final String TYPE = "TYPE";
    public static final String ACTION = "ACTION";
    public static final String DETAIL = "DETAIL";

    @Test
    public void buildSuccesShouldBuildSystemNotification() {

        SystemNotification actual = new SystemNotification
                .Builder()
                .withCorrelationId(CORRELATION_ID)
                .withTransactionId(TRANSACTION_ID)
                .withApplicationName(APP_NAME)
                .withComponent(COMPONENT)
                .withMessage(MESSAGE)
                .withType(TYPE)
                .withAction(ACTION)
                .withDetails(DETAIL)
                .buildSuccess();

        Assertions.assertEquals(CORRELATION_ID, actual.getCorrelationId());
        Assertions.assertEquals(TRANSACTION_ID, actual.getTransactionId());
        Assertions.assertEquals(MESSAGE, actual.getMessage());
        Assertions.assertEquals(ACTION, actual.getAction());
        Assertions.assertEquals(APP_NAME, actual.getApplicationName());
        Assertions.assertEquals(COMPONENT, actual.getComponent());
        Assertions.assertEquals(DETAIL, actual.getDetails());
        Assertions.assertEquals(TYPE, actual.getType());
        Assertions.assertEquals(Level.INFO, actual.getLevel());
        Assertions.assertEquals(Assertion.SUCCESS, actual.getAssertion());

        Map<String, String> map = actual.toMap();
        
     
        Assertions.assertEquals("SUCCESS", map.get("assertion"));
        Assertions.assertEquals(CORRELATION_ID, map.get("correlationId"));
        Assertions.assertEquals(TRANSACTION_ID, map.get("transactionId"));
        Assertions.assertEquals(APP_NAME, map.get("applicationName"));
        Assertions.assertEquals(COMPONENT, map.get("component"));
        Assertions.assertEquals(TYPE, map.get("type"));
        Assertions.assertEquals(DETAIL, map.get("details"));
        Assertions.assertEquals(ACTION, map.get("action"));

    }


    @Test
    public void buildErrorShouldBuildSystemNotification() {

        SystemNotification actual = new SystemNotification
                .Builder()
                .withCorrelationId(CORRELATION_ID)
                .withTransactionId(TRANSACTION_ID)
                .withApplicationName(APP_NAME)
                .withComponent(COMPONENT)
                .withMessage(MESSAGE)
                .withType(TYPE)
                .withAction(ACTION)
                .withDetails(DETAIL)
                .buildError();

        Assertions.assertEquals(CORRELATION_ID, actual.getCorrelationId());
        Assertions.assertEquals(TRANSACTION_ID, actual.getTransactionId());
        Assertions.assertEquals(MESSAGE, actual.getMessage());
        Assertions.assertEquals(ACTION, actual.getAction());
        Assertions.assertEquals(APP_NAME, actual.getApplicationName());
        Assertions.assertEquals(COMPONENT, actual.getComponent());
        Assertions.assertEquals(DETAIL, actual.getDetails());
        Assertions.assertEquals(TYPE, actual.getType());
        Assertions.assertEquals(Level.ERROR, actual.getLevel());
        Assertions.assertEquals(Assertion.FAILURE, actual.getAssertion());

        Map<String, String> map = actual.toMap();


        Assertions.assertEquals("FAILURE", map.get("assertion"));
        Assertions.assertEquals(CORRELATION_ID, map.get("correlationId"));
        Assertions.assertEquals(TRANSACTION_ID, map.get("transactionId"));
        Assertions.assertEquals(APP_NAME, map.get("applicationName"));
        Assertions.assertEquals(COMPONENT, map.get("component"));
        Assertions.assertEquals(TYPE, map.get("type"));
        Assertions.assertEquals(DETAIL, map.get("details"));
        Assertions.assertEquals(ACTION, map.get("action"));

    }

}
