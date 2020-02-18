package ca.bc.gov.open.pssg.rsbc.dps.notification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileInfoTest {

    public static final String FILE_ID = "fileId";
    public static final String IMAGE_EXTENSION = "pdf";
    public static final String ROOT_FOLDER = "rootFolder";
    public static final String RELEASE_FOLDER_NAME = "releaseFolderName";
    public static final String ARCHIVE_FOLDER_NAME = "archiveFolderName";
    public static final String ERROR_FOLDER_NAME = "errorFolderName";

    @Test
    public void WithFileNameShouldReturnAppropriateLocations() {


        FileInfo sut = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER, RELEASE_FOLDER_NAME, ARCHIVE_FOLDER_NAME, ERROR_FOLDER_NAME);

        Assertions.assertEquals(FILE_ID, sut.getFileId());
        Assertions.assertEquals(IMAGE_EXTENSION, sut.getImageExtension());
        Assertions.assertEquals(ROOT_FOLDER, sut.getRootFolder());
        Assertions.assertEquals(RELEASE_FOLDER_NAME, sut.getReleaseFolderName());
        Assertions.assertEquals(ARCHIVE_FOLDER_NAME, sut.getArchiveFolderName());
        Assertions.assertEquals(ERROR_FOLDER_NAME, sut.getErrorFolderName());
        Assertions.assertEquals("rootFolder/releaseFolderName/fileId.pdf", sut.getImageReleaseFileName());
        Assertions.assertEquals("rootFolder/archiveFolderName/fileId.pdf", sut.getImageArchiveFileName());
        Assertions.assertEquals("rootFolder/errorFolderName/fileId.pdf", sut.getImageErrorFileName());
        Assertions.assertEquals("rootFolder/releaseFolderName/fileId.xml", sut.getMetaDataReleaseFileName());
        Assertions.assertEquals("rootFolder/archiveFolderName/fileId.xml", sut.getMetaDataArchiveFileName());
        Assertions.assertEquals("rootFolder/errorFolderName/fileId.xml", sut.getMetaDataErrorFileName());

    }
}
