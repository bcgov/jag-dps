package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.dps.monitoring.NotificationService;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;

@RestController
public class JobController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileService fileService;

    private final ExecutorService executorService;

    private final KofaxProperties kofaxProperties;

    private final SftpProperties sftpProperties;

    public JobController(
            FileService fileService,
            @Qualifier("jobExecutorService") ExecutorService executorService, KofaxProperties kofaxProperties,
            SftpProperties sftpProperties) {
        this.fileService = fileService;
        this.executorService = executorService;
        this.kofaxProperties = kofaxProperties;
        this.sftpProperties = sftpProperties;
    }

    @PostMapping(path = "/job/error", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JobResponse> createErrorJob() {

        //TODO: remove once tested
        NotificationService.notify("test@gov.bc.ca", logger -> {
            logger.warn("this is a test email");
        });

        executorService.execute(() -> {
            fileService
                    .listFiles(MessageFormat.format("{0}/{1}", sftpProperties.getRemoteLocation(), kofaxProperties.getErrorLocation()))
                    .forEach(file -> {

                        NotificationService.notify("test@gov.bc.ca", logger -> {
                            logger.warn("error in file {}", file);
                        });

                    });
        });



        JobResponse response = new JobResponse();
        response.setScheduled(true);

        logger.info("Error job has been scheduled to run in the background.");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }




}
