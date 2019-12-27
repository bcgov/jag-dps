package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BamboraConfiguration {

  @RequestMapping("/bamboraconfiguration")
  public String singlepaymenturl(@RequestParam(value="name", defaultValue="defaultvalue") String name) {
    return "Hello from the Bambora configuration controller";
  }
}
