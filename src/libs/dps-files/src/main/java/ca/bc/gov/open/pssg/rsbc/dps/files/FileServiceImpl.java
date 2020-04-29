package ca.bc.gov.open.pssg.rsbc.dps.files;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;

import java.io.InputStream;
import java.util.List;

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
    public void moveFilesToArchive(FileInfo fileInfo) {
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
    public void moveFilesToError(FileInfo fileInfo) {
        sftpService.moveFile(fileInfo.getImageReleaseFileName(), fileInfo.getImageErrorFileName());
        sftpService.moveFile(fileInfo.getMetaDataReleaseFileName(), fileInfo.getMetaDataErrorFileName());
    }

    @Override
    public InputStream getImageFileContent(FileInfo fileInfo) {
        return sftpService.getContent(fileInfo.getImageReleaseFileName());
    }

    @Override
    public InputStream getMetadataFileContent(FileInfo fileInfo) {
        return sftpService.getContent(fileInfo.getMetaDataReleaseFileName());
    }

    @Override
    public InputStream getFileContent(String fileName) {
        return sftpService.getContent(fileName);
    }

    @Override
    public void uploadFile(InputStream inputStream, String fileName) {
        sftpService.put(inputStream, fileName);
    }

    @Override
    public List<String> listFiles(String remoteDirectory) {
        return sftpService.listFiles(remoteDirectory);
    }

    @Override
    public void moveFile(String sourceFileName, String destinationFileName) {
        sftpService.moveFile(sourceFileName, destinationFileName);
    }

}
