package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SftpService sftpService;
    private final SftpProperties sftpProperties;

    public OutputNotificationConsumer(SftpService sftpService, SftpProperties sftpProperties) {
        this.sftpService = sftpService;
        this.sftpProperties = sftpProperties;
    }

    @RabbitListener(queues = Keys.VIPS_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received message for {}", message.getBusinessAreaCd());
        ByteArrayInputStream metadataBin = sftpService.getContent(buildFileName(message.getFileId(), METATADATA_EXTENSION));
        logger.info("successfully downloaded file [{}]", buildFileName(message.getFileId(), METATADATA_EXTENSION));

        int n = metadataBin.available();
        byte[] bytes = new byte[n];
        metadataBin.read(bytes, 0, n);
        logger.info(new String(bytes, StandardCharsets.UTF_8));

        logger.info("received message for {}", message.getBusinessAreaCd());
        ByteArrayInputStream imageBin = sftpService.getContent(buildFileName(message.getFileId(), METATADATA_EXTENSION));
        logger.info("successfully downloaded file [{}]", buildFileName(message.getFileId(), IMAGE_EXTENSION));

    }

    private String buildFileName(String fileId, String extension) {
        return MessageFormat.format("{0}/release/{1}.{2}",sftpProperties.getRemoteLocation(), fileId, extension);
    }

}
