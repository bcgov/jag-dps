package ca.bc.gov.open.pssg.rsbc.vips.ords.client.health;

import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;

public interface HealthService {

        HealthResponse health() throws ApiException;
}
