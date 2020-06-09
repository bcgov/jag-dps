package ca.bc.gov.open.pssg.rsbc.dps.files;

import java.text.MessageFormat;

/**
 * Represents the file information from the sftp server perspective
 *
 * @author alexjoybc@github
 *
 */
public class FileInfo {

    private static final String RELEASE_FOLDER_NAME = "release";
    private static final String ERROR_FOLDER_NAME = "error";
    private static final String ARCHIVE_FOLDER_NAME = "archive";

    private String rootFolder;
    private String fileId;
    private String imageExtension;

    public FileInfo(String fileId,String imageExtension, String rootFolder) {
        this.rootFolder = rootFolder;
        this.fileId = fileId;
        this.imageExtension = imageExtension;
    }


    public String getRootFolder() {
        return rootFolder;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileName() {
        return buildFileName(this.imageExtension);
    }

    public String getImageExtension() {
        return this.imageExtension;
    }

    public String getImageReleaseFileName() {
        return buildImageFileName(RELEASE_FOLDER_NAME);
    }

    public String getImageErrorFileName() {
        return buildImageFileName(ERROR_FOLDER_NAME);
    }

    public String getImageArchiveFileName() {
        return buildImageFileName(ARCHIVE_FOLDER_NAME);
    }

    public String getMetaDataReleaseFileName() {
        return buildMetaDataFilename(RELEASE_FOLDER_NAME);
    }

    public String getMetaDataErrorFileName() {
        return buildMetaDataFilename(ERROR_FOLDER_NAME);
    }

    public String getMetaDataArchiveFileName() {
        return buildMetaDataFilename(ARCHIVE_FOLDER_NAME);
    }

    private String buildMetaDataFilename(String folderName) {
        return buildFullFileName(folderName, "xml");
    }

    private String buildImageFileName(String folderName) {
        return buildFullFileName(folderName, this.imageExtension);
    }

    private String buildFileName(String extension) {
        return MessageFormat.format("{0}.{1}", this.fileId, extension);
    }

    private String buildFullFileName(String folderName, String extension) {
        return MessageFormat.format("{0}/{1}/{2}", this.rootFolder, folderName, buildFileName(extension));
    }

    @Override
    public String toString() {
        return MessageFormat.format("FileInfo: original metadata: [{0}], original image: [{1}]", this.getMetaDataReleaseFileName(), this.getImageReleaseFileName());
    }

}
