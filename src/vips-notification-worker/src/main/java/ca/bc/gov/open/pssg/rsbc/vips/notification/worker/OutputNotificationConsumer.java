package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import ca.bc.gov.open.pssg.rsbc.dps.vips.notification.worker.generated.models.KofaxOutputMetadata;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document.DocumentService;
import com.migcomponents.migbase64.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SftpService sftpService;
    private final SftpProperties sftpProperties;
    private final DocumentService documentService;
    private final JAXBContext kofaxOutputMetadataContext;

    public OutputNotificationConsumer(SftpService sftpService, SftpProperties sftpProperties,
                                      DocumentService documentService, @Qualifier("kofaxOutputMetadataContext")JAXBContext kofaxOutputMetadataContext) {
        this.sftpService = sftpService;
        this.sftpProperties = sftpProperties;
        this.documentService = documentService;
        this.kofaxOutputMetadataContext = kofaxOutputMetadataContext;
    }

    @RabbitListener(queues = Keys.VIPS_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) throws JAXBException {

        logger.info("received message for {}", message.getBusinessAreaCd());
        String metadata = getMetadata(message.getFileId());
        logger.info("successfully downloaded file [{}]", buildFileName(message.getFileId(), METATADATA_EXTENSION));

        String base64 =  getBase64Metadata(metadata);
        KofaxOutputMetadata metadataXml = unmarshallMetadataXml(metadata);

        logger.info("received message for {}", message.getBusinessAreaCd());
        byte[] image = getImage(message.getFileId());
        logger.info("successfully downloaded file [{}]", buildFileName(message.getFileId(), IMAGE_EXTENSION));

    }

    private String buildFileName(String fileId, String extension) {
        return MessageFormat.format("{0}/release/{1}.{2}",sftpProperties.getRemoteLocation(), fileId, extension);
    }

    private String getMetadata(String fileId) {
        ByteArrayInputStream metadataBin = sftpService.getContent(buildFileName(fileId, METATADATA_EXTENSION));

        int n = metadataBin.available();
        byte[] bytes = new byte[n];
        metadataBin.read(bytes, 0, n);

        return new String(bytes);
    }



    private String getBase64Metadata(String content) {
        return Base64.encodeToString(content.getBytes(), false);
    }

    private KofaxOutputMetadata unmarshallMetadataXml(String content) throws JAXBException {

        Unmarshaller unmarshaller = this.kofaxOutputMetadataContext.createUnmarshaller();
        return (KofaxOutputMetadata) unmarshaller.unmarshal(new StringReader(content));

    }

    private byte[] getImage(String fileId) {

        ByteArrayInputStream imageBin = sftpService.getContent(buildFileName(fileId, IMAGE_EXTENSION));

        int n = imageBin.available();
        byte[] bytes = new byte[n];
        imageBin.read(bytes, 0, n);

        return bytes;

    }


}
