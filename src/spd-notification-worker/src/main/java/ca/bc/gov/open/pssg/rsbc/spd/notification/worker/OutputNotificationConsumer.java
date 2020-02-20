package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * Comsumes messages pushed to the CRRP Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {

    private final FileService fileService;
    private final SftpProperties sftpProperties;
    private final DocumentService documentService;
    private final JAXBContext kofaxOutputMetadataContext;

    public static final String IMAGE_EXTENSION = "PDF";

    private static final int SUCCESS_CODE = 0;
    private static final String DPS_FILE_ID_KEY = "dps.fileId";
    private static final String DPS_BUSINESS_AREA_CD_KEY = "dps.businessAreaCd";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OutputNotificationConsumer(FileService fileService,
                                      SftpProperties sftpProperties,
                                      DocumentService documentService,
                                      @Qualifier("kofaxOutputMetadataContext") JAXBContext kofaxOutputMetadataContext) {
        this.fileService = fileService;
        this.sftpProperties = sftpProperties;
        this.documentService = documentService;
        this.kofaxOutputMetadataContext = kofaxOutputMetadataContext;
    }

    @RabbitListener(queues = Keys.CRRP_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received new {}", message);

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
}
