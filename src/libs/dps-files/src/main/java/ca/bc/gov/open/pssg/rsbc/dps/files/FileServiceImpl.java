package ca.bc.gov.open.pssg.rsbc.dps.files;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;

/**
 * Implementation of the FileService using sftp server.
 *
 * @author alexjoybc@github
 */
public class FileServiceImpl implements FileService {

    private final SftpService sftpService;

    public FileServiceImpl(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    /**
     * Moves image and metadata file on the sftp server to the archive folder.
     *
     * @param fileInfo
     * @throws DpsSftpException
     */
    @Override
    public void MoveFilesToArchive(FileInfo fileInfo) throws DpsSftpException {
        sftpService.moveFile(fileInfo.getImageReleaseFileName(), fileInfo.getImageArchiveFileName());
        sftpService.moveFile(fileInfo.getMetaDataReleaseFileName(), fileInfo.getMetaDataArchiveFileName());
    }

    /**
     * Moves image and metadata file on the sftp server to the error folder.
     *
     * @param fileInfo
     * @throws DpsSftpException
     */
    @Override
    public void MoveFilesToError(FileInfo fileInfo) throws DpsSftpException {
        sftpService.moveFile(fileInfo.getImageReleaseFileName(), fileInfo.getImageErrorFileName());
        sftpService.moveFile(fileInfo.getMetaDataReleaseFileName(), fileInfo.getMetaDataErrorFileName());
    }

}
