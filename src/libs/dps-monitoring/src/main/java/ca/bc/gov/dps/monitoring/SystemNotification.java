package ca.bc.gov.dps.monitoring;

import org.slf4j.event.Level;

import java.util.HashMap;
import java.util.Map;

public class SystemNotification {

    public static class Builder {

        private String correlationId;
        private String transactionId;
        private String applicationName;
        private String component;
        private String type;
        private Level level;
        private String message;
        private String details;
        private String action;

        public Builder withCorrelationId(String correlationId) { this.correlationId = correlationId; return this; }
        public Builder withTransactionId(String transactionId) {this.transactionId = transactionId; return this; }
        public Builder withApplicationName(String applicationName) { this.applicationName = applicationName; return this; }
        public Builder withComponent(String component) { this.component = component; return this; }
        public Builder withType(String type) { this.type = type; return this; }
        public Builder withLevel(Level level) { this.level = level; return this; }
        public Builder withMessage(String message) { this.message = message; return this; }
        public Builder withDetails(String details) { this.details = details; return this; }
        public Builder withAction(String action) {this.action = action; return this; }
        
        private SystemNotification build(Assertion assertion, Level defaultLevel) {

            SystemNotification systemNotification = new SystemNotification(assertion);

            systemNotification.correlationId = correlationId;
            systemNotification.transactionId = transactionId;
            systemNotification.applicationName = applicationName;
            systemNotification.component = component;
            systemNotification.type = type;
            systemNotification.level = level == null ? defaultLevel : level;
            systemNotification.message = message;
            systemNotification.details = details;
            systemNotification.action = action;

            return systemNotification;
            
        }
        
        public SystemNotification buildSuccess() {
            return build(Assertion.SUCCESS, Level.INFO);
        }
        
        public SystemNotification buildError() {
            return build(Assertion.FAILURE, Level.ERROR);
        }
  
    }

    private String correlationId;
    private String transactionId;
    private Assertion assertion;
    private String applicationName;
    private String component;
    private String type;
    private Level level;
    private String message;
    private String details;
    private String action;

    private SystemNotification(Assertion assertion) {
        this.assertion = assertion;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAction() {
        return action;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getComponent() {
        return component;
    }

    public String getType() {
        return type;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Assertion getAssertion() {
        return assertion;
    }

    public Map<String, String> toMap() {

        Map<String, String> result = new HashMap<>();
        result.put("assertion", assertion.getCode());
        result.put("correlationId", correlationId);
        result.put("transactionId", transactionId);
        result.put("applicationName", applicationName);
        result.put("component", component);
        result.put("type", type);
        result.put("details", details);
        result.put("action", action);

        return result;

    }

}
