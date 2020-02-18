package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.notification.OutputNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Comsumes messages pushed to the CRRP Queue
 *
 * @author alexjoybc@github
 */
@Component
public class OutputNotificationConsumer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = Keys.CRRP_QUEUE_NAME)
    public void receiveMessage(OutputNotificationMessage message) {

        logger.info("received message for {}", message.getBusinessAreaCd());

    }

}
