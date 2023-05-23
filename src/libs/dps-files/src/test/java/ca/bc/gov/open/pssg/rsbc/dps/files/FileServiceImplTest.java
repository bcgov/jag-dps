package ca.bc.gov.open.pssg.rsbc.dps.files;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileServiceImplTest {


    public static final String FILE_NAME = "test.txt";
    public static final String REMOTE_DIRECTORY = "remoteDirectory";
    public static final String FILE_1 = "file1";
    public static final String FILE_2 = "file2";
    public static final String FAKECONTENT = "fakecontent";
    public static final String FILENAME_1_TXT = "filename_1.txt";
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
        Mockito.when(sftpServiceMock.getContent(FILENAME_1_TXT)).thenReturn(getFakeInputString());

        List<String> fakeFileList = new ArrayList<>();
        fakeFileList.add(FILE_1);
        fakeFileList.add(FILE_2);
        Mockito.when(sftpServiceMock.listFiles(REMOTE_DIRECTORY)).thenReturn(fakeFileList);

        sut = new FileServiceImpl(sftpServiceMock);
    }

    @Test
    public void WithFileInfoItShouldMoveFilesToArchive() {

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER, "error");

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

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER, "error");

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

    @Test
    public void shouldListFiles() {

        List<String> actual = sut.listFiles(REMOTE_DIRECTORY);

        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.contains(FILE_1));
        Assertions.assertTrue(actual.contains(FILE_2));

    }

    @Test
    public void shouldGetContentOfFile() throws IOException {


        InputStream content = sut.getFileContent(FILENAME_1_TXT);

        Assertions.assertEquals(FAKECONTENT, IOUtils.toString(content, String.valueOf(StandardCharsets.UTF_8)));

    }


    private ByteArrayInputStream getFakeInputString() {

        String fake = FAKECONTENT;

        return new ByteArrayInputStream(fake.getBytes());

    }


}
