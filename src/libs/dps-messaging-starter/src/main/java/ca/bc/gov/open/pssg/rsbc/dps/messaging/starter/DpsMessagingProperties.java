package ca.bc.gov.open.pssg.rsbc.dps.messaging.starter;

public class DpsMessagingProperties {


    private String exchangeName;
    private String routingKey;

    public DpsMessagingProperties(String exchangeName, String routingKey) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
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


}
