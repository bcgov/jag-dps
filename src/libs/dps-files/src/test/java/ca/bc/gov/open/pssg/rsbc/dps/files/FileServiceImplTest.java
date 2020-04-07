package ca.bc.gov.open.pssg.rsbc.dps.files;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileServiceImplTest {


    public static final String FILE_NAME = "test.txt";
    private FileServiceImpl sut;

    public static final String FILE_ID = "fileId";
    public static final String IMAGE_EXTENSION = "pdf";
    public static final String ROOT_FOLDER = "rootFolder";

    @Mock
    private SftpService sftpServiceMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(sftpServiceMock).moveFile(Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(sftpServiceMock).put(Mockito.any(InputStream.class), Mockito.anyString());
        sut = new FileServiceImpl(sftpServiceMock);
    }

    @Test
    public void WithFileInfoItShouldMoveFilesToArchive() {

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER);

        Assertions.assertDoesNotThrow(() -> sut.moveFilesToArchive(fileInfo));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/release/fileId.pdf"),
                        Mockito.eq("rootFolder/archive/fileId.pdf"));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/release/fileId.xml"),
                        Mockito.eq("rootFolder/archive/fileId.xml"));

    }

    @Test
    public void WithFileInfoItShouldMoveMetaDataToArchive() {

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER);

        Assertions.assertDoesNotThrow(() -> sut.moveFilesToError(fileInfo));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/release/fileId.pdf"),
                        Mockito.eq("rootFolder/error/fileId.pdf"));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/release/fileId.xml"),
                        Mockito.eq("rootFolder/error/fileId.xml"));

    }

    @Test
    public void withFileItShouldUploadFile() {

        String value = "some content";

        Assertions.assertDoesNotThrow(() -> sut.uploadFile(new ByteArrayInputStream(value.getBytes()), FILE_NAME));

        Mockito.verify(sftpServiceMock, Mockito.times(1)).put(Mockito.any(InputStream.class), Mockito.eq(FILE_NAME));

    }


}
