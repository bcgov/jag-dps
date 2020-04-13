package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

public class DpsMessagingProperties {


    private String exchangeName;
    private String routingKey;
    private int retryCount;
    private int retryDelay;

    public DpsMessagingProperties(String exchangeName, String routingKey, int retryCount, int retryDelay) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.retryCount = retryCount;
        this.retryDelay = retryDelay;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getQueueName() {
        return routingKey + "_q";
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public int getRetryCount() { return retryCount; }

    public int getRetryDelay() {
        return retryDelay;
    }
}
