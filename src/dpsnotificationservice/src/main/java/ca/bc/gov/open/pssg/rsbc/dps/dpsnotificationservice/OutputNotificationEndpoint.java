package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse2;
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
        OutputNotificationResponse2 response2 = new OutputNotificationResponse2();
        response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE);
        response2.setRespMsg(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE);
        response.setOutputNotificationResponse(response2);
        return response;
    }
}