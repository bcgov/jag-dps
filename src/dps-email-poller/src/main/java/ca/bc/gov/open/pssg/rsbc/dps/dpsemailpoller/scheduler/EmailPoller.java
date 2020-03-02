package ca.bc.gov.open.pssg.rsbc.dps.dpsemailpoller.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailPoller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Scheduled(fixedDelay = 1000)
//    // Every second
//    public void scheduleFixedDelayTask() {
//        logger.info("Fixed delay task - {}", new Date());
//    }

    @Scheduled(cron = "${cron.expression}")
    public void pollForEmails() {
        long now = System.currentTimeMillis() / 1000;
        logger.info("Poll for emails - " + now);
    }

}
