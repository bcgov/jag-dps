package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.sftp;

import java.io.ByteArrayInputStream;

public interface SftpService {
    ByteArrayInputStream getContent(String remoteFilename) throws DpsSftpException;
}
