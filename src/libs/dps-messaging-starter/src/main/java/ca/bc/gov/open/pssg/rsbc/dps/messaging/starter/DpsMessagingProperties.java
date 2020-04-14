package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.text.MessageFormat;

@ConfigurationProperties(prefix = "dps.messaging")
public class DpsMessagingProperties {

    private String exchangeName;
    private String routingKey;
    private int retryCount;
    private int retryDelay;
    private String type;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(int retryDelay) {
        this.retryDelay = retryDelay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainQueueName() {
        return MessageFormat.format("{0}.{1}.Q", routingKey, exchangeName);
    }

    public String getDeadLetterQueueName() {
        return MessageFormat.format("{0}.{1}.DLQ", routingKey, exchangeName);
    }

    public String getParkingLotQueueName() {
        return MessageFormat.format("{0}.{1}.PL", routingKey, exchangeName);
    }

}
