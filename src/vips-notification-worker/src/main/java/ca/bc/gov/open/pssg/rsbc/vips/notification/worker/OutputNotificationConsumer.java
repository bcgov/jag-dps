package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.sftp.SftpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Comsumes messages pushed to the CRRP Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SftpService sftpService;

    public OutputNotificationConsumer(SftpService sftpService) {
        this.sftpService = sftpService;
    }


    @RabbitListener(queues = Keys.VIPS_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received message for {}", message.getBusinessAreaCd());

        Optional<String> fileName = message.getFileList().stream().findFirst();

        if(fileName.isPresent()) {

            ByteArrayInputStream bin = sftpService.getContent(fileName.get());

            logger.info("successfully downloaded file [{}]", fileName.get());

            int n = bin.available();
            byte[] bytes = new byte[n];
            bin.read(bytes, 0, n);
            logger.info(new String(bytes, StandardCharsets.UTF_8));

        }

    }

}
