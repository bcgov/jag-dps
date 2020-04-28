package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.DpsEmailWorkerException;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.MessageFormat;

public class ErrorMonitoringJob implements MonitoringJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileService fileService;

    private final KofaxProperties kofaxProperties;

    private final SftpProperties sftpProperties;

    private final ImportSessionService importSessionService;

    public ErrorMonitoringJob(
            FileService fileService,
            ImportSessionService importSessionService,
            KofaxProperties kofaxProperties,
            SftpProperties sftpProperties) {
        this.fileService = fileService;
        this.kofaxProperties = kofaxProperties;
        this.sftpProperties = sftpProperties;
        this.importSessionService = importSessionService;
    }

    @Override
    public void execute() {
        fileService.listFiles(getFolderPath())
                .forEach(filename -> handleKofaxError(filename));
    }

    private void handleKofaxError(String filename) {

        if(!filename.endsWith(".xml")) return;
        try {

            InputStream fileContent = fileService.getFileContent(getFileName(filename));
            ImportSession importSession = importSessionService.convertToImportSession(fileContent);
            logger.info(importSession.getErrorCode());

        } catch (DpsEmailWorkerException | DpsSftpException ex) {

            logger.error("DpsEmailWorkerException while processing error message", ex);

        }

    }

    private String getFileName(String filename) {
        return MessageFormat.format("{0}/{1}", getFolderPath(), filename);
    }

    private String getFolderPath() {
        return MessageFormat.format("{0}/{1}", sftpProperties.getRemoteLocation(), kofaxProperties.getErrorLocation());
    }

}