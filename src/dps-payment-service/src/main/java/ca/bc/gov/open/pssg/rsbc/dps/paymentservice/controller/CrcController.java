package ca.bc.gov.open.pssg.rsbc.dps.paymentservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.PaymentServiceConstants;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration.CrcProperties;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableConfigurationProperties(CrcProperties.class)
public class CrcController {

    private static final Logger logger = LogManager.getLogger(CrcController.class);
    private final CrcProperties crcProperties;

    public CrcController(CrcProperties crcProperties) {
        this.crcProperties = crcProperties;
    }


    @RequestMapping(value = "/getBeanstreamEndpoints",
            produces = { "application/xml" },
            method = RequestMethod.GET)
    @ApiOperation(value = "Generates application callback endpoints for Payment Service", response = BeanstreamEndpointResponse.class, tags={ "PaymentServices"})
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful operation", response = BeanstreamEndpointResponse.class) })
    public BeanstreamEndpointResponse getBeanstreamEndpoints() {

        if (StringUtils.isEmpty(crcProperties.getApproved()) || StringUtils.isEmpty(crcProperties.getDeclined()) || StringUtils.isEmpty(crcProperties.getError())) {
            logger.error("CRC endpoints are not set properly in the config. Failed to read the properties. ");
            return new BeanstreamEndpointResponse("", "", "", PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL,
                    PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
        }

        logger.debug("CRC endpoints are present in the config. Returning the properties successfully.");
        return new BeanstreamEndpointResponse(crcProperties.getApproved(), crcProperties.getDeclined(),crcProperties.getError(),
                    PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
                    PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD);

    }

}
