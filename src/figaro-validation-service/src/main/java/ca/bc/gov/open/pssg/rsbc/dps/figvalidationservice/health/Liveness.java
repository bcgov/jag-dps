package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.health;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * This endpoint is to test the liveness of the service.
 */
@Endpoint(id="liveness")
@Component
public class Liveness {
    @ReadOperation
    public String testLiveness() {
        return "{\"status\":\"UP\"}";
    }
}