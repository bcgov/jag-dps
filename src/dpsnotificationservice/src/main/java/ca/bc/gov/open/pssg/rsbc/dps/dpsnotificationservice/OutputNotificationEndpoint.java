package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.Country;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class OutputNotificationEndpoint {


    @PayloadRoot(namespace = Keys.NAMESPACE_URI, localPart = "outputNotificationRequest")
    @ResponsePayload
    public OutputNotificationResponse outputNotificationNotification(@RequestPayload OutputNotificationRequest request) {
        OutputNotificationResponse response = new OutputNotificationResponse();
        Country country = new Country();
        country.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE);
        country.setRespMsg(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE);
        response.setCountry(country);
        return response;
    }
}