package ca.bc.gov.open.pssg.rsbc.vips.ords.client.health;

import ca.bc.gov.open.ords.vips.client.api.HealthApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;
import ca.bc.gov.open.ords.vips.client.api.model.HealthOrdsResponse;

/**
 * Health Service Implementation using ORDS api
 *
 * @author carolcarpenterjustice
 *
 */
public class HealthServiceImpl implements HealthService {

    private final HealthApi healthApi;

    public HealthServiceImpl(HealthApi healthApi) {
        this.healthApi = healthApi;
    }

    /**
     * health check for the endpoint
     *
     * @return
     * @throws ApiException
     */
    @Override
    public HealthResponse health() throws ApiException {

        HealthOrdsResponse response = healthApi.health();
        return HealthResponse.SuccessResponse(response.getAppid(), response.getMethod(), response.getStatus(), response.getHost(), response.getInstance());
    }
}
