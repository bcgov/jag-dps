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
        private String errorType;
        private Level level;
        private String message;
        private String details;
        private String action;

        public Builder withCorrelationId(String correlationId) { this.correlationId = correlationId; return this; }
        public Builder withTransactionId(String transactionId) {this.transactionId = transactionId; return this; }
        public Builder withapplicationName(String applicationName) { this.applicationName = applicationName; return this; }
        public Builder withcomponent(String component) { this.component = component; return this; }
        public Builder witherrorType(String errorType) { this.errorType = errorType; return this; }
        public Builder withLevel(Level level) { this.level = level; return this; }
        public Builder withmessage(String message) { this.message = message; return this; }
        public Builder withdetails(String details) { this.details = details; return this; }
        public Builder withAction(String action) {this.action = action; return this; }
        
        public SystemNotification build() {

            SystemNotification systemNotification = new SystemNotification();

            systemNotification.correlationId = correlationId;
            systemNotification.transactionId = transactionId;
            systemNotification.applicationName = applicationName;
            systemNotification.component = component;
            systemNotification.errorType = errorType;
            systemNotification.level = level == null ? Level.INFO : level;
            systemNotification.message = message;
            systemNotification.details = details;
            systemNotification.action = action;

            return systemNotification;
            
        }
  
    }

    private String correlationId;
    private String transactionId;
    private String applicationName;
    private String component;
    private String errorType;
    private Level level;
    private String message;
    private String details;
    private String action;

    private SystemNotification() {}

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

    public String getErrorType() {
        return errorType;
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

    public Map<String, String> toMap() {

        Map<String, String> result = new HashMap<>();
        result.put("correlationId", correlationId);
        result.put("transactionId", transactionId);
        result.put("applicationName", applicationName);
        result.put("component", component);
        result.put("errorType", errorType);
        result.put("details", details);
        result.put("action", action);

        return result;

    }

}
