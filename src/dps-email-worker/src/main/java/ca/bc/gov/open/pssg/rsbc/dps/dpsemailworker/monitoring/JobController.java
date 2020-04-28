package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.dps.monitoring.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@RestController
public class JobController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExecutorService executorService;

    private final MonitoringJob errorMonitoringJob;

    public JobController(
            @Qualifier("jobExecutorService") ExecutorService executorService,
            @Qualifier("errorMonitoringJob") MonitoringJob errorMonitoringJob
    ) {

        this.executorService = executorService;
        this.errorMonitoringJob = errorMonitoringJob;

    }

    @PostMapping(path = "/job/error", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JobResponse> createErrorJob() {

        //TODO: remove once tested
        NotificationService.notify("test@gov.bc.ca", logger -> {
            logger.warn("this is a test email");
        });

        executorService.execute(() -> errorMonitoringJob.execute());

        JobResponse response = new JobResponse();
        response.setScheduled(true);

        logger.info("Error job has been scheduled to run in the background.");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }




}
