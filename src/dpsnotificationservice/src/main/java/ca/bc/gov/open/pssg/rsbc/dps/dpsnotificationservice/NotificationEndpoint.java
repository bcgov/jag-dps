package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NotificationEndpoint {

    private static final String NAMESPACE_URI = "https://github.com/bcgov/jag-dps";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "outputNotificationRequest")
    @ResponsePayload
    public OutputNotificationResponse outputNotification(@RequestPayload OutputNotificationRequest outputNotificationRequest) {


        OutputNotificationResponse response = new OutputNotificationResponse();
        OutputNotificationResponse2 response2 = new OutputNotificationResponse2();
        response2.setRespMsg("success");
        response2.setRespCode("0");
        response.setOutputNotificationResponse(response2);
        return response;
    }


}
