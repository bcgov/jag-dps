package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.dps.monitoring.NotificationService;
import ca.bc.gov.dps.monitoring.SystemNotification;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.DpsEmailWorkerException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.Batch;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.error.DpsError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Optional;

public class ErrorMonitoringJob implements MonitoringJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileService fileService;

    private final KofaxProperties kofaxProperties;

    private final SftpProperties sftpProperties;

    private final TenantProperties tenantProperties;

    private final ImportSessionService importSessionService;


    public ErrorMonitoringJob(
            FileService fileService,
            ImportSessionService importSessionService,
            KofaxProperties kofaxProperties,
            SftpProperties sftpProperties, TenantProperties tenantProperties) {
        this.fileService = fileService;
        this.kofaxProperties = kofaxProperties;
        this.sftpProperties = sftpProperties;
        this.importSessionService = importSessionService;
        this.tenantProperties = tenantProperties;
    }

    @Override
    public void execute() {

        logger.info("starting error job");

        fileService.listFiles(getErrorFolderPath())
                .forEach(filename -> handleKofaxError(filename));
    }

    private void handleKofaxError(String filename) {

        if (!filename.endsWith(".xml")) return;

        try {

            logger.info("find error, file name is {}", getErrorFileName(filename));
            InputStream fileContent = fileService.getFileContent(getErrorFileName(filename));
            ImportSession importSession = importSessionService.convertToImportSession(fileContent);
            logger.info(importSession.getErrorCode());

            logger.debug("Attempting to move file to {}", getErrorHoldFileName(filename));
            fileService.moveFile(getErrorFileName(filename), getErrorHoldFileName(filename));
            logger.info("Successfully moved file to {}", getErrorHoldFileName(filename));

            SystemNotification systemNotification = new SystemNotification
                    .Builder()
                    .withLevel(Level.INFO)
                    .withCorrelationId(getFirstBatchId(importSession).isPresent() ? getFirstBatchId(importSession).get() : null)
                    .withTransactionId(filename)
                    .withAction(MessageFormat.format("Manual Intervention (File can be found in Kofax {0} Error Hold " +
                            "Directory)", tenantProperties.getName()))
                    .withApplicationName(MessageFormat.format("ODPS({0})", tenantProperties.getName()))
                    .withComponent(MessageFormat.format("KOFAX {0} Import", tenantProperties.getName()))
                    .withDetails(MessageFormat.format("Kofax {0} Import failed", tenantProperties.getName()))
                    .withType(DpsError.KOFAX_ERROR.getCode())
                    .withMessage(importSession.getErrorMessage())
                    .buildError();

            NotificationService.notify(systemNotification);


        } catch (DpsEmailWorkerException | DpsSftpException ex) {

            logger.error("DpsEmailWorkerException while processing error message", ex);

        }

    }

    private Optional<String> getFirstBatchId(ImportSession importSession) {

        Optional<Batch> batch = importSession.getBatches().getBatches().stream().findFirst();

        if(batch.isPresent())
            return batch
                    .get()
                    .getBatchFields().getBatchFields()
                    .stream().filter(x -> x.name.equals(kofaxProperties.getBatchFieldImportId())).map(x -> x.getValue())
                    .findFirst();

        return Optional.empty();
    }

    private String getErrorFileName(String filename) {
        return MessageFormat.format("{0}/{1}", getErrorFolderPath(), filename);
    }

    private String getErrorFolderPath() {
        return MessageFormat.format("{0}/{1}", sftpProperties.getRemoteLocation(), kofaxProperties.getErrorLocation());
    }

    private String getErrorHoldFileName(String filename) {
        return MessageFormat.format("{0}/{1}", getErrorHoldFolderPath(), filename);
    }

    private String getErrorHoldFolderPath() {
        return MessageFormat.format("{0}/{1}", sftpProperties.getRemoteLocation(),
                kofaxProperties.getErrorHoldLocation());
    }

}