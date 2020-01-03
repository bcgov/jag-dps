package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class BamboraConfiguration {

    private static final Logger logger = LogManager.getLogger(BamboraConfiguration.class);

    @Value("${dps.crc.endpoint.approved}")
    private String approved;
    @Value("${dps.crc.endpoint.declined}")
    private String declined;
    @Value("${dps.crc.endpoint.error}")
    private String error;

    @RequestMapping(value = "/bamboraconfiguration",
	  produces = { "application/xml" }, 
	  method = RequestMethod.GET)
	  @ApiOperation(value = "Generates application callback endpoints for Payment Service", notes = "", response = BeanstreamEndpointResponse.class, tags={ "PaymentServices"})
	  @ApiResponses(value = { 
	      @ApiResponse(code = 200, message = "Successful operation", response = BeanstreamEndpointResponse.class) })
    public BeanstreamEndpointResponse singlepaymenturl() {
        // Make sure autowired params are not null (May be missing from configMap)
        if (StringUtils.isEmpty(approved) || StringUtils.isEmpty(declined) || StringUtils.isEmpty(error)) {
            logger.fatal("CRC endpoints are not set properly in the config. Failed to read the properties. ");
            return new BeanstreamEndpointResponse("", "", "", PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL,
                    PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
        } else {
            logger.debug("CRC endpoints are present in the config. Returning the properties successfully.");
            return new BeanstreamEndpointResponse(approved, declined, error,
                    PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
                    PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD);
        }

    }

}
