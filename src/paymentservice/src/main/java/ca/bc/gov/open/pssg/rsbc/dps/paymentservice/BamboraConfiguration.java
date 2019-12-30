package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BamboraConfiguration {

  @Value("${dps.beanstream.endpoint.approved}")
  private String approved;
  @Value("${dps.beanstream.endpoint.declined}")
  private String declined;
  @Value("${dps.beanstream.endpoint.error}")
  private String error;

  @RequestMapping("/bamboraconfiguration")
  public BeanstreamEndpointResponse singlepaymenturl(@RequestParam(value="name", defaultValue="defaultvalue") String name) {
    System.out.println(approved);
    return new BeanstreamEndpointResponse(approved,declined,error,PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK, PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD) ;
            //"Hello from the Bambora configuration controller";
  }
}
