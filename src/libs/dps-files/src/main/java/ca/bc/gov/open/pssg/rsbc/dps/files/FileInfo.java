package ca.bc.gov.open.pssg.rsbc.dps.files;

import java.text.MessageFormat;

/**
 * Represents the file information from the sftp server perspective
 *
 * @author alexjoybc@github
 *
 */
public class FileInfo {

    private String releaseFolderName;
    private String archiveFolderName;
    private String errorFolderName;
    private String rootFolder;
    private String fileId;
    private String imageExtension;

    public FileInfo(String fileId,String imageExtension, String rootFolder,String releaseFolderName, String archiveFolderName, String errorFolderName) {
        this.releaseFolderName = releaseFolderName;
        this.archiveFolderName = archiveFolderName;
        this.errorFolderName = errorFolderName;
        this.rootFolder = rootFolder;
        this.fileId = fileId;
        this.imageExtension = imageExtension;
    }

    public String getReleaseFolderName() {
        return releaseFolderName;
    }

    public String getArchiveFolderName() {
        return archiveFolderName;
    }

    public String getErrorFolderName() {
        return errorFolderName;
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public String getFileId() {
        return fileId;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public String getImageReleaseFileName() {
        return buildImageFileName(this.releaseFolderName);
    }

    public String getImageErrorFileName() {
        return buildImageFileName(this.errorFolderName);
    }

    public String getImageArchiveFileName() {
        return buildImageFileName(this.archiveFolderName);
    }

    public String getMetaDataReleaseFileName() {
        return buildMetaDataFilename(this.releaseFolderName);
    }

    public String getMetaDataErrorFileName() {
        return buildMetaDataFilename(this.errorFolderName);
    }

    public String getMetaDataArchiveFileName() {
        return buildMetaDataFilename(this.archiveFolderName);
    }

    private String buildMetaDataFilename(String folderName) {
        return buildFileName(folderName, "xml");
    }

    private String buildImageFileName(String folderName) {
        return buildFileName(folderName, this.imageExtension);
    }

    private String buildFileName(String folderName, String extension) {
        return MessageFormat.format("{0}/{1}/{2}.{3}", this.rootFolder, folderName , this.fileId, extension);
    }

}
