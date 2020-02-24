package ca.bc.gov.open.pssg.rsbc.figaro.ords.client;

import ca.bc.gov.open.ords.figcr.client.api.HealthApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.HealthOrdsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HealthServiceImplTest {

    public static final String API_EXCEPTION = "api exception";
    private HealthServiceImpl sut;

    private static final String APP_ID = "FIGCRP";
    private static final String METHOD = "health_check";
    private static final String STATUS = "success";
    private static final String HOST = "devdb";
    private static final String INSTANCE = "deva";

    @Mock
    private HealthApi healthApiMock;

    @BeforeAll
    public void setup() throws ApiException {
        MockitoAnnotations.initMocks(this);

        sut = new HealthServiceImpl(healthApiMock);
    }

    @Test
    public void withHealthReturnValidResponse() throws ApiException {

        HealthOrdsResponse successResponse = new HealthOrdsResponse();
        successResponse.setAppid(APP_ID);
        successResponse.setMethod(METHOD);
        successResponse.setStatus(STATUS);
        successResponse.setHost(HOST);
        successResponse.setInstance(INSTANCE);

        Mockito.when(healthApiMock.health()).thenReturn(successResponse);

        HealthResponse result = sut.health();

        Assertions.assertEquals(APP_ID, result.getAppid());
        Assertions.assertEquals(METHOD, result.getMethod());
        Assertions.assertEquals(STATUS, result.getStatus());
        Assertions.assertEquals(HOST, result.getHost());
        Assertions.assertEquals(INSTANCE, result.getInstance());
    }
}
