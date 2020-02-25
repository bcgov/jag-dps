package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.health;

/**
 *  Represents a Health Response
 *
 * @author carolcarpenterjustice
 *
 */
public class HealthResponse {

    private String appid;
    private String method;
    private String status;
    private String host;
    private String instance;

    public HealthResponse(String appid, String method, String status,
                          String host, String instance) {
        this.appid = appid;
        this.method = method;
        this.status = status;
        this.host = host;
        this.instance = instance;
    }

    public String getAppid() {
        return appid;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getHost() {
        return host;
    }

    public String getInstance() {
        return instance;
    }

    public static HealthResponse SuccessResponse(String appid, String method, String status,
                                                 String host, String instance) {

        return new HealthResponse(appid, method, status, host, instance);
    }
}
