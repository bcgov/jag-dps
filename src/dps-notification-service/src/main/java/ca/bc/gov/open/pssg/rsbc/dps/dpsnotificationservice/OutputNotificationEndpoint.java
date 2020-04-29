package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

<<<<<<< HEAD

=======
import bcgov.reeks.dps_extensions_common_wsprovider.outputnotificationws.*;
>>>>>>> dps-notificationservice-refactor
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.pssg.rsbc.dps.dps_extensions_common_wsprovider.outputnotificationws.ObjectFactory;
import ca.bc.gov.pssg.rsbc.dps.dps_extensions_common_wsprovider.outputnotificationws.OutputNotificationRequest;
import ca.bc.gov.pssg.rsbc.dps.dps_extensions_common_wsprovider.outputnotificationws.OutputNotificationResponse2;
import ca.bc.gov.pssg.rsbc.dps.dps_extensions_common_wsprovider.outputnotificationws.OutputNotificationWSPortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Output notifications endpoint
 *
 * When a message is submitted to this endpoint, the tenant is identified then a message is queued in the appropriate queue.
 *
 *
 * @author alexjoybc@github
 *
 */
@Service
public class OutputNotificationEndpoint implements OutputNotificationWSPortType {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RabbitTemplate mainExchangeRabbitTemplate;

    public OutputNotificationEndpoint(@Qualifier("mainExchangeRabbitTemplate")RabbitTemplate mainExchangeRabbitTemplate) {

        this.mainExchangeRabbitTemplate = mainExchangeRabbitTemplate;
    }

    @Override
    public OutputNotificationResponse2 outputNotification(OutputNotificationRequest outputNotificationRequest) {

        ObjectFactory factory = new ObjectFactory();

        OutputNotificationResponse2 response2 = factory.createOutputNotificationResponse2();

        if (outputNotificationRequest.getOutputNotificationRequest() == null) {
            logger.error("Missing OutputNotificationRequest");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest is required");
            return response2;
        }

        if (outputNotificationRequest.getOutputNotificationRequest().getBusinessAreaCd() == null) {
            logger.error("Missing OutputNotificationRequest.getBusinessAreaCd");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest.getBusinessAreaCd is required");
            return response2;
        }

        if (outputNotificationRequest.getOutputNotificationRequest().getFileList() == null ||
                outputNotificationRequest.getOutputNotificationRequest().getFileList().getFileId() == null ||
                outputNotificationRequest.getOutputNotificationRequest().getFileList().getFileId().isEmpty()) {

            logger.error("No File to be processed");
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg("OutputNotificationRequest.FileList.FileId must contains a least 1 file");
            return response2;

        }

        logger.info("Received new notification for {}", outputNotificationRequest.getOutputNotificationRequest().getBusinessAreaCd());

        try {

            outputNotificationRequest.getOutputNotificationRequest().getFileList().getFileId().stream().forEach(file -> {

                OutputNotificationMessage message = new OutputNotificationMessage(outputNotificationRequest.getOutputNotificationRequest().getBusinessAreaCd().toString(), file);

                logger.debug("Attempting to publish message to outputNotification exchange with key [{}], fileId: [{}]", message.getBusinessAreaCd(), message.getFileId());
                mainExchangeRabbitTemplate.convertAndSend(message.getBusinessAreaCd(), message);
                logger.info("Successfully published message to outputNotification exchange with key [{}], fileId: [{}]", message.getBusinessAreaCd(), message.getFileId());

            });

            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE);
            response2.setRespMsg(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE);

        } catch (AmqpException amqpException) {

            logger.error("RabbitMq Exception: ", amqpException);
            response2.setRespCode(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE);
            response2.setRespMsg(amqpException.getMessage());

        }

        return response2;
    }
}
