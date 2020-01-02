package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.BeanstreamEndpointResponse;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BamboraConfiguration {

	@Value("${dps.crc.endpoint.approved}")
	private String approved;
	@Value("${dps.crc.endpoint.declined}")
	private String declined;
	@Value("${dps.crc.endpoint.error}")
	private String error;

	@RequestMapping(value = "/bamboraconfiguration", method = RequestMethod.GET)
	public BeanstreamEndpointResponse singlepaymenturl() {
		try {
			// Make sure autowired params are not null (May be missing from configMap)
			if ( StringUtils.isEmpty(approved) || StringUtils.isEmpty(declined) || StringUtils.isEmpty(error))
				throw new PaymentServiceException(PaymentServiceConstants.PAYMENT_SERVICE_ERR_MISSING_CONFIG_PARAMS);
			return new BeanstreamEndpointResponse(approved, declined, error,
					PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK,
					PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD);
		}
		catch (PaymentServiceException e)
		{
			e.printStackTrace();
			return new BeanstreamEndpointResponse("","","",PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL,
					PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
		}
	}
}
