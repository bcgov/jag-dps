package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import ca.bc.gov.open.pssg.rsbc.dps.vips.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document.VipsDocumentResponse;
import com.migcomponents.migbase64.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.MessageFormat;

/**
 * Comsumes messages pushed to the CRRP Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {

    public static final String METATADATA_EXTENSION = "xml";
    public static final String IMAGE_EXTENSION = "PDF";
    private static final String MIME = "application";
    private static final String MIME_SUBTYPE = "pdf";

    private static final int SUCCESS_CODE = 0;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SftpService sftpService;
    private final SftpProperties sftpProperties;
    private final DocumentService documentService;
    private final JAXBContext kofaxOutputMetadataContext;

    public OutputNotificationConsumer(SftpService sftpService,
                                      SftpProperties sftpProperties,
                                      DocumentService documentService,
                                      @Qualifier("kofaxOutputMetadataContext")JAXBContext kofaxOutputMetadataContext) {
        this.sftpService = sftpService;
        this.sftpProperties = sftpProperties;
        this.documentService = documentService;
        this.kofaxOutputMetadataContext = kofaxOutputMetadataContext;
    }

    @RabbitListener(queues = Keys.VIPS_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) throws JAXBException {

        logger.info("received message for {}", message.getBusinessAreaCd());
        String metadataReleaseFileName = buildFileName(message.getFileId(), Keys.SFTP_RELEASE_DIR, METATADATA_EXTENSION);
        String metadata = getMetadata(metadataReleaseFileName);
        logger.info("successfully downloaded file [{}]", metadataReleaseFileName);

        logger.info("metadata: {}", metadata);
        String base64Metadata =  getBase64Metadata(metadata);
        Data metadataXml = unmarshallMetadataXml(metadata);

        logger.info("received message for {}", message.getBusinessAreaCd());
        try {

            File image = null;
            String imageReleaseFileName = buildFileName(message.getFileId(), Keys.SFTP_RELEASE_DIR, IMAGE_EXTENSION);
            image = getImage(message.getFileId(), imageReleaseFileName);
            logger.info("successfully downloaded file [{}]", imageReleaseFileName);

            VipsDocumentResponse vipsDocumentResponse = documentService.vipsDocument(metadataXml.getDocumentData().getDType(), base64Metadata, MIME, MIME_SUBTYPE, "unused", image);
            logger.info("vipsDocumentResponse: documentId {}, respCode {}, respMsg {}", vipsDocumentResponse.getDocumentId(), vipsDocumentResponse.getRespCode(), vipsDocumentResponse.getRespMsg());

            if (vipsDocumentResponse.getRespCode() == SUCCESS_CODE) {
                // Move xml and pdf files to archive directory
                moveFiles(message.getFileId(), Keys.SFTP_ARCHIVE_DIR, metadataReleaseFileName, imageReleaseFileName);
            } else {
                // Move xml and pdf files to error directory
                moveFiles(message.getFileId(), Keys.SFTP_ERROR_DIR, metadataReleaseFileName, imageReleaseFileName);
            }

        } catch (IOException e) {
            logger.error("IOException while trying to get file from sftp server {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void moveFiles(String fileId, String directory, String xmlFileName, String pdfFileName) {
        String destinationFilename = buildFileName(fileId, directory, METATADATA_EXTENSION);
        logger.info("move file from {} to {}", xmlFileName, destinationFilename);
        sftpService.moveFile(xmlFileName, destinationFilename);

        destinationFilename = buildFileName(fileId, directory, IMAGE_EXTENSION);
        logger.info("move file from {} to {}", pdfFileName, destinationFilename);
        sftpService.moveFile(pdfFileName, destinationFilename);
    }

    private String buildFileName(String fileId, String directory, String extension) {
        return MessageFormat.format("{0}/{1}/{2}.{3}", sftpProperties.getRemoteLocation(), directory, fileId, extension);
    }

    private String getMetadata(String xmlReleaseFileName) {
        ByteArrayInputStream metadataBin = sftpService.getContent(xmlReleaseFileName);

        int n = metadataBin.available();
        byte[] bytes = new byte[n];
        metadataBin.read(bytes, 0, n);

        return new String(bytes);
    }

    private String getBase64Metadata(String content) {
        String base64Metadata = Base64.encodeToString(content.getBytes(), false);

        // base64 has the / slash character which confuses ords parsing of urls.
        // instead convert / to - which is used by other base64 encoders.
        // see:  https://en.wikipedia.org/wiki/base64#variants_summary_table
        base64Metadata = base64Metadata.replace('/','_');
        base64Metadata = base64Metadata.replace('+','-');
        base64Metadata = base64Metadata.replaceAll("\r\n", "");

        return base64Metadata;
    }

    private Data unmarshallMetadataXml(String content) throws JAXBException {
        Unmarshaller unmarshaller = this.kofaxOutputMetadataContext.createUnmarshaller();
        return (Data) unmarshaller.unmarshal(new StringReader(content));
    }

    private File getImage(String fileId, String filename) throws IOException {

        logger.info("sftp filename: {}", filename);
        ByteArrayInputStream imageBin = sftpService.getContent(filename);

        File imageTempFile = File.createTempFile(fileId, "." + IMAGE_EXTENSION);
        imageTempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(imageTempFile)) {
            IOUtils.copy(imageBin, out);
        }
        return imageTempFile;
    }
}
