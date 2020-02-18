package ca.bc.gov.open.pssg.rsbc.dps.notification;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;

/**
 * An interface that expose functionality to manipulate files.
 *
 * @author alexjoybc@github
 *
 */
public interface FileService {

    /**
     * A service that moves files to archive
     * @param fileInfo
     * @throws DpsSftpException
     */
    void MoveFilesToArchive(FileInfo fileInfo) throws DpsSftpException;

    /**
     * A service that move files to error
     * @param fileInfo
     * @throws DpsSftpException
     */
    void MoveFilesToError(FileInfo fileInfo) throws DpsSftpException;

}
