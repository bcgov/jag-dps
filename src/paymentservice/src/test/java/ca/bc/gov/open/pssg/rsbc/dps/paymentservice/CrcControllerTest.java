package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;


import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration.CrcProperties;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrcControllerTest {

    public static final String ERROR = "error";
    public static final String DECLINED = "declined";
    public static final String APPROVED = "approved";
    private CrcController sut;


    @Test
    public void withValidConfigurationShouldReturnConfiguration() {

        CrcProperties properties = new CrcProperties();
        properties.setApproved(APPROVED);
        properties.setDeclined(DECLINED);
        properties.setError(ERROR);

        sut = new CrcController(properties);

        BeanstreamEndpointResponse response = sut.getBeanstreamEndpoints();

        Assertions.assertEquals(APPROVED, response.getApproved());
        Assertions.assertEquals(DECLINED, response.getDeclined());
        Assertions.assertEquals(ERROR, response.getError());
        Assertions.assertEquals("success", response.getRespMsg());
        Assertions.assertEquals(0, response.getRespCode());

    }

    @Test
    public void withMissionConfigurationShouldReturnConfiguration() {

        CrcProperties properties = new CrcProperties();
        properties.setApproved("");
        properties.setDeclined(DECLINED);
        properties.setError(ERROR);

        sut = new CrcController(properties);

        BeanstreamEndpointResponse response = sut.getBeanstreamEndpoints();

        Assertions.assertEquals("fail", response.getRespMsg());
        Assertions.assertEquals(-1, response.getRespCode());

    }


}
