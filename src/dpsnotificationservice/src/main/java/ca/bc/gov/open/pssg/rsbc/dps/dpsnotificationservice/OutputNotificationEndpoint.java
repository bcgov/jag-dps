package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.OutputNotificationResponse2;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


/**
 * Output notifications endpoint
 *
 * When a message is submitted to this endpoint, the tenant is identified then a message is queued in the appropriate queue.
 *
 *
 * @author alexjoybc@github
 *
 */
@Endpoint
public class OutputNotificationEndpoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate outputNotificationTopicTemplate;

    public OutputNotificationEndpoint(@Qualifier("outputNotificationTopicTemplate")RabbitTemplate outputNotificationTopicTemplate) {
        this.outputNotificationTopicTemplate = outputNotificationTopicTemplate;
    }

    @PayloadRoot(namespace = Keys.NAMESPACE_URI, localPart = Keys.OUTPUT_NOTIFICATION_REQUEST)
    @ResponsePayload
    public OutputNotificationResponse outputNotificationNotification(@RequestPayload OutputNotificationRequest request) {


        OutputNotificationResponse response = new OutputNotificationResponse();
        OutputNotificationResponse2 response2 = new OutputNotificationResponse2();

        if(request.getOutputNotificationRequest() == null) {
            logger.error("Missing OutputNotificationRequest");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest is required");
            response.setOutputNotificationResponse(response2);
            return response;
        }

        if(request.getOutputNotificationRequest().getBusinessAreaCd() == null) {
            logger.error("Missing OutputNotificationRequest.getBusinessAreaCd");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest.getBusinessAreaCd is required");
            response.setOutputNotificationResponse(response2);
            return response;
        }

        if(
                request.getOutputNotificationRequest().getFileList() == null
                        || request.getOutputNotificationRequest().getFileList() == null
                        || request.getOutputNotificationRequest().getFileList().getFileId() == null
                        || request.getOutputNotificationRequest().getFileList().getFileId().isEmpty()) {

            logger.error("No File to be processed");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest.FileList.FileId must contains a least 1 file");
            response.setOutputNotificationResponse(response2);
            return response;

        }

        logger.info("Received new notification for {}", request.getOutputNotificationRequest().getBusinessAreaCd());

        try {

            logger.debug("Attempting to publish message to outputNotification exchange with key {}", request.getOutputNotificationRequest().getBusinessAreaCd());

            OutputNotificationMessage message = new OutputNotificationMessage(request.getOutputNotificationRequest().getBusinessAreaCd().value());
            request.getOutputNotificationRequest().getFileList().getFileId().stream().forEach(file -> message.AddFile(file));

            outputNotificationTopicTemplate.convertAndSend(message.getBusinessAreaCd(), message);
            logger.info("Successfully published message to outputNotification exchange with key {}", request.getOutputNotificationRequest().getBusinessAreaCd());

            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE);
            response2.setRespMsg(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE);

        } catch (AmqpException amqpException) {

            logger.error("RabbitMq Exception: {}", amqpException.getMessage());
            amqpException.printStackTrace();
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg(amqpException.getMessage());

        }

        response.setOutputNotificationResponse(response2);
        return response;
    }
}
