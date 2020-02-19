package ca.bc.gov.open.pssg.rsbc.dps.files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileInfoTest {

    public static final String FILE_ID = "fileId";
    public static final String IMAGE_EXTENSION = "pdf";
    public static final String ROOT_FOLDER = "rootFolder";

    @Test
    public void WithFileNameShouldReturnAppropriateLocations() {


        FileInfo sut = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER);

        Assertions.assertEquals(FILE_ID, sut.getFileId());
        Assertions.assertEquals(IMAGE_EXTENSION, sut.getImageExtension());
        Assertions.assertEquals(ROOT_FOLDER, sut.getRootFolder());
        Assertions.assertEquals("rootFolder/release/fileId.pdf", sut.getImageReleaseFileName());
        Assertions.assertEquals("rootFolder/archive/fileId.pdf", sut.getImageArchiveFileName());
        Assertions.assertEquals("rootFolder/error/fileId.pdf", sut.getImageErrorFileName());
        Assertions.assertEquals("rootFolder/release/fileId.xml", sut.getMetaDataReleaseFileName());
        Assertions.assertEquals("rootFolder/archive/fileId.xml", sut.getMetaDataArchiveFileName());
        Assertions.assertEquals("rootFolder/error/fileId.xml", sut.getMetaDataErrorFileName());

    }
}
