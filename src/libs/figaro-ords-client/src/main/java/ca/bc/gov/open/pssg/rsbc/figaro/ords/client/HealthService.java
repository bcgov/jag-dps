package ca.bc.gov.open.pssg.rsbc.figaro.ords.client;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;

public interface HealthService {

        HealthResponse health() throws ApiException;
}
