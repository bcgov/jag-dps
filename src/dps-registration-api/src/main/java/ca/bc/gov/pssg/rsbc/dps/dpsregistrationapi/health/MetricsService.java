package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health;

import org.springframework.boot.actuate.health.HealthComponent;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("metrics")
@Produces(MediaType.APPLICATION_JSON)
public interface MetricsService {

    @GET
    @Path("health")
    HealthComponent health();


}
