package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.health;

import ca.bc.gov.open.ords.dfcms.client.api.handler.ApiException;

public interface HealthService {

        HealthResponse health() throws ApiException;
}
