package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.dps.monitoring.NotificationService;
import ca.bc.gov.dps.monitoring.SystemNotification;
import ca.bc.gov.open.jagvipsclient.document.DocumentService;
import ca.bc.gov.open.jagvipsclient.document.VipsDocumentResponse;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.vips.notification.worker.generated.models.Data;
import com.migcomponents.migbase64.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Comsumes messages pushed to the VIPS Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {


    private static final String IMAGE_EXTENSION = "PDF";
    private static final String MIME = "application";
    private static final String MIME_SUBTYPE = "pdf";
    private static final int SUCCESS_CODE = 0;
    private static final String DPS_FILE_ID_KEY = "dps.fileId";
    private static final String DPS_BUSINESS_AREA_CD_KEY = "dps.businessAreaCd";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileService fileService;
    private final SftpProperties sftpProperties;
    private final DocumentService documentService;
    private final JAXBContext kofaxOutputMetadataContext;

    public OutputNotificationConsumer(FileService fileService,
                                      SftpProperties sftpProperties,
                                      DocumentService documentService,
                                      @Qualifier("kofaxOutputMetadataContext") JAXBContext kofaxOutputMetadataContext) {
        this.fileService = fileService;
        this.sftpProperties = sftpProperties;
        this.documentService = documentService;
        this.kofaxOutputMetadataContext = kofaxOutputMetadataContext;
    }

    @RabbitListener(queues = Keys.VIPS_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received new {}", message);

        MDC.put(DPS_FILE_ID_KEY, message.getFileId());
        MDC.put(DPS_BUSINESS_AREA_CD_KEY, message.getBusinessAreaCd());

        FileInfo fileInfo = new FileInfo(message.getFileId(), IMAGE_EXTENSION, sftpProperties.getRemoteLocation(), "error");

        try {

            logger.debug("attempting to download file [{}]", fileInfo.getMetaDataReleaseFileName());
            String metadata = getMetadata(fileInfo);
            logger.info("successfully downloaded file [{}]", fileInfo.getMetaDataReleaseFileName());

            VipsDocumentResponse vipsDocumentResponse =
                    documentService.vipsDocument(
                            unmarshallMetadataXml(metadata).getDocumentData().getDType(),
                            Base64.encodeToString(metadata.getBytes(),false),
                            MIME,
                            MIME_SUBTYPE,
                            "",
                            getImage(fileInfo));

            if (vipsDocumentResponse.getRespCode() == SUCCESS_CODE) {
                logger.info("success: {} with {}", vipsDocumentResponse, fileInfo);
                moveFilesToArchive(fileInfo);

                signalSuccess(message);

            } else {
                logger.error("error: {} with {}", vipsDocumentResponse, fileInfo);
                moveFilesToError(fileInfo);
            }

        } catch (IOException | JAXBException e) {
            logger.error("{} while processing file id [{}]: ", e.getClass().getSimpleName(), fileInfo.getFileId(), e);
            moveFilesToError(fileInfo);
        } catch (DpsSftpException e) {
            logger.error("{} while processing file id [{}]:", e.getClass().getSimpleName(), fileInfo.getFileId(), e);
        } finally {
            MDC.remove(DPS_FILE_ID_KEY);
            MDC.remove(DPS_BUSINESS_AREA_CD_KEY);
        }
    }

    private String getMetadata(FileInfo fileInfo) throws IOException {
        logger.debug("attempting get file metadata");
        InputStream is = fileService.getMetadataFileContent(fileInfo);
        return IOUtils.toString(is, StandardCharsets.UTF_8.name());
    }

    private Data unmarshallMetadataXml(String content) throws JAXBException {

        logger.debug("attempting to serialize file");
        Unmarshaller unmarshaller = this.kofaxOutputMetadataContext.createUnmarshaller();
        return (Data) unmarshaller.unmarshal(new StringReader(content));
    }

    private File getImage(FileInfo fileInfo) throws IOException {

        logger.debug("attempting to get file: {}", fileInfo.getImageReleaseFileName());

        File imageTempFile = File.createTempFile(fileInfo.getFileId(), "." + IMAGE_EXTENSION);
        imageTempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(imageTempFile)) {
            IOUtils.copy(fileService.getImageFileContent(fileInfo), out);
        }
        logger.info("successfully downloaded file [{}]", fileInfo.getImageReleaseFileName());

        return imageTempFile;
    }

    private void moveFilesToArchive(FileInfo fileInfo) {
        fileService.moveFilesToArchive(fileInfo);
        logger.info("files have been moved to archive location, {}", fileInfo);
    }

    private void moveFilesToError(FileInfo fileInfo) {
        fileService.moveFilesToError(fileInfo);
        logger.warn("files have been moved to error location, {}", fileInfo);
    }

    private void signalSuccess(OutputNotificationMessage message) {

        SystemNotification success = new SystemNotification
                .Builder()
                .withTransactionId(message.getBusinessAreaCd())
                .withCorrelationId(message.getFileId())
                .withApplicationName(Keys.APP_NAME)
                .withComponent("Notification Worker")
                .withMessage("Data successfully transferred to VIPS")
                .withType("VIPS NOTIFICATION WORKER SUCCESS")
                .buildSuccess();

        NotificationService.notify(success);
    }

}
