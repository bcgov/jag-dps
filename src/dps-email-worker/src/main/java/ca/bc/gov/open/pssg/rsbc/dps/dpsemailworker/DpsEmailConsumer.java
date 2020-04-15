package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
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

    public static final String XML = ".xml";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DpsEmailService dpsEmailService;

    private final StorageService storageService;

    private final FileService fileService;

    private final SftpProperties sftpProperties;

    private final ImportSessionService importSessionService;

    public DpsEmailConsumer(DpsEmailService dpsEmailService, StorageService storageService, FileService fileService,
                            SftpProperties sftpProperties, ImportSessionService importSessionService) {
        this.dpsEmailService = dpsEmailService;
        this.storageService = storageService;
        this.fileService = fileService;
        this.sftpProperties = sftpProperties;
        this.importSessionService = importSessionService;
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

            //TODO: when id will be generated for kofax, it will replace TBD.
            logger.info("Attempting to move email to processed folder\"");
            DpsEmailProcessedResponse dpsEmailProcessedResponse = dpsEmailService.dpsEmailProcessed(message.getBase64EmailId(), "TBD");
            logger.info("Successfully moved email to processed folder");

        } catch (Exception e) {
            // TODO: handle exception using rabbit mq
            logger.error("Error in {} while processing message: ", e.getClass().getSimpleName(), e);
            throw new DpsEmailWorkerException("Exception while processing message.", e.getCause());
        } finally {
            MDC.remove(MdcConstants.MDC_TRANSACTION_ID_KEY);
        }
    }

}
