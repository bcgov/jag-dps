package ca.bc.gov.open.pssg.rsbc.dps.dpsvalidationservice.health;

import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;

public interface HealthService {

        HealthResponse health() throws ApiException;
}
