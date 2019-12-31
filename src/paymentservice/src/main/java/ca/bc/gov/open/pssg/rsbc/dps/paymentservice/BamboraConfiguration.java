package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BamboraConfiguration {

    @Value("${CRC_ENDPOINT_APPROVED}")
    private String approved;
    @Value("${CRC_ENDPOINT_DECLINED}")
    private String declined;
    @Value("${CRC_ENDPOINT_ERROR}")
    private String error;

    @RequestMapping(value = "/bamboraconfiguration", method = RequestMethod.GET)
    public BeanstreamEndpointResponse singlepaymenturl() {
       return new BeanstreamEndpointResponse(approved, declined, error, PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK, PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD);
    }
}
