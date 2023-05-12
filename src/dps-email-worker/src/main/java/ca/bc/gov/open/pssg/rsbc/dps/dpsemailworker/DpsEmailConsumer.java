package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.dps.monitoring.NotificationService;
import ca.bc.gov.dps.monitoring.SystemNotification;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.registration.RegistrationService;
import ca.bc.gov.open.pssg.rsbc.models.DpsFileInfo;
import ca.bc.gov.open.pssg.rsbc.models.DpsMetadata;
import ca.bc.gov.open.pssg.rsbc.dps.cache.StorageService;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailProcessedResponse;
import ca.bc.gov.open.pssg.rsbc.dps.email.client.DpsEmailService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.monitoring.MdcConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.text.MessageFormat;

@Component
public class DpsEmailConsumer {

    private static final String XML = "xml";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DpsEmailService dpsEmailService;

    private final StorageService storageService;

    private final FileService fileService;

    private final SftpProperties sftpProperties;

    private final ImportSessionService importSessionService;

    private final RegistrationService registrationService;

    public DpsEmailConsumer(DpsEmailService dpsEmailService, StorageService storageService, FileService fileService,
                            SftpProperties sftpProperties, ImportSessionService importSessionService,
                            RegistrationService registrationService) {
        this.dpsEmailService = dpsEmailService;
        this.storageService = storageService;
        this.fileService = fileService;
        this.sftpProperties = sftpProperties;
        this.importSessionService = importSessionService;
        this.registrationService = registrationService;
    }

    @RabbitListener(queues = Keys.EMAIL_QUEUE_NAME)
    public void receiveMessage(DpsMetadata message) {

        MDC.put(MdcConstants.MDC_TRANSACTION_ID_KEY, message.getTransactionId().toString());

        logger.info("received new {}", message);

        ImportSession session = importSessionService.generateImportSession(message);
        if(!session.getBatchName().isPresent()) throw new DpsEmailWorkerException("batch name is required.");

        try {

            logger.debug("attempting to get message meta data [{}]", message);
            DpsFileInfo dpsFileInfo = message.getFileInfo();

            // content to be used.
            byte[] content = storageService.get(dpsFileInfo.getId());
            logger.info("message attachment content retrieved [{}]", dpsFileInfo.getId());

            logger.debug("Attempting to upload image file to SFTP server");
            fileService.uploadFile(new ByteArrayInputStream(content), MessageFormat.format("{0}/{1}", sftpProperties.getRemoteLocation(), message.getFileInfo().getName()));
            logger.info("Successfully uploaded image file to remote SFTP server");

            logger.debug("Attempting to upload definition file to SFTP server");
            fileService.uploadFile(new ByteArrayInputStream(importSessionService.convertToXmlBytes(session)), MessageFormat.format("{0}/{1}.{2}", sftpProperties.getRemoteLocation(), session.getBatchName().get(), XML));
            logger.info("Successfully uploaded definition file to remote SFTP server");

            logger.debug("Attempting to upload control file to SFTP server");
            fileService.uploadFile(new ByteArrayInputStream("".getBytes()), MessageFormat.format("{0}/{1}/{2}.{3}", sftpProperties.getRemoteLocation(), Keys.KOFAX_CONTROL_FOLDER,  session.getBatchName().get(), XML));
            logger.info("Successfully uploaded control file to remote SFTP server");

            logger.debug("Attempting to remove document from redis cache");
            storageService.delete(dpsFileInfo.getId());
            logger.info("Successfully removed document from redis cache");


            if(registrationService.isActive()) {
                logger.info("Attempting to register package");
                registrationService.registerPackage(message);
                logger.info("Successfully registered package to OTS database.");
            } else {
                logger.info("Registration Service is not activated.");
            }

            logger.info("Attempting to move email to processed folder");
            DpsEmailProcessedResponse dpsEmailProcessedResponse = dpsEmailService.dpsEmailProcessed(message.getBase64EmailId(), message.getTransactionId().toString());

            if(!dpsEmailProcessedResponse.isAcknowledge()) {
                notifyMoveEmailToError(message, dpsEmailProcessedResponse);
                MDC.remove(MdcConstants.MDC_TRANSACTION_ID_KEY);
                return;
            }

            logger.info("Successfully moved email to processed folder");

        } catch (Exception e) {

            logger.error("Error in {} while processing message: ", e.getClass().getSimpleName(), e);
            throw new DpsEmailWorkerException("Exception while processing message.", e.getCause());

        } finally {
            MDC.remove(MdcConstants.MDC_TRANSACTION_ID_KEY);
        }

        notifySuccess(message);

    }

    private void notifySuccess(DpsMetadata message) {

        SystemNotification success = new SystemNotification
                .Builder()
                .withCorrelationId(message.getTransactionId().toString())
                .withCorrelationId(message.getFileInfo().getName())
                .withApplicationName(Keys.APPLICATION_NAME)
                .withComponent("Image import")
                .withMessage("All files were successfully uploaded to KOFAX sftp server.")
                .withType("DPS UPLOAD SUCCESS")
                .buildSuccess();

        NotificationService.notify(success);

    }

    private void notifyMoveEmailToError(DpsMetadata message, DpsEmailProcessedResponse response) {
        SystemNotification success = new SystemNotification
                .Builder()
                .withCorrelationId(message.getTransactionId().toString())
                .withCorrelationId(message.getFileInfo().getName())
                .withApplicationName(Keys.APPLICATION_NAME)
                .withComponent("Image import")
                .withAction(MessageFormat.format("Manual Intervention: email with subject {0} in {1} error hold folder can be moved to Processed folder, " +
                        "it was successfully processed by the system", message.getSubject(), message.getTo()))
                .withMessage(response.getMessage())
                .withType("MAILBOX ERROR")
                .buildError();

        NotificationService.notify(success);
    }

    // Anything that hits the parking queue we can assume we can't process, so...
    // 1. Move the email to the error folder in exchange
    // 2. Delete the attached doc from the cache
    @RabbitListener(queues = Keys.PARKING_QUEUE_NAME)
    public void receiveParkedMessage(DpsMetadata message) {

        logger.error("Error: email {} - landed in parking lot", message.toString());
        MDC.put(MdcConstants.MDC_TRANSACTION_ID_KEY, message.getTransactionId().toString());

        try {

            // Move the email to the error folder in exchange
            logger.error("Attempting to move email {} to ErrHold folder", message.toString());
            DpsEmailProcessedResponse dpsEmailProcessedResponse = dpsEmailService.dpsEmailFailed(message.getBase64EmailId(), "TBD");
            logger.error("1");
            // If the email was moved, clear it from the cache too
            if (dpsEmailProcessedResponse.isAcknowledge()) {
                logger.error("Error: email {} moved to ErrHold folder in exchange", message.toString());
                logger.debug("Attempting to remove document from redis cache");
                DpsFileInfo dpsFileInfo = message.getFileInfo();
                storageService.delete(dpsFileInfo.getId());
                logger.debug("Successfully removed document from redis cache");
            }
            else
            {
                logger.error("Error: {} failed to move to ErrHold folder in exchange", message.toString());
            }

        } catch (Exception e) {
            logger.error("Error in {} while processing parked message: ", e.getClass().getSimpleName(), e);
            throw new DpsEmailWorkerException("Exception while processing parked message.", e.getCause());
        } finally {
            MDC.remove(MdcConstants.MDC_TRANSACTION_ID_KEY);
        }
    }


}
