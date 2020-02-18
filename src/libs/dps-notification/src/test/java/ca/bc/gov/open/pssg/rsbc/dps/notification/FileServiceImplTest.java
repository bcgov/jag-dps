package ca.bc.gov.open.pssg.rsbc.dps.notification;

import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileServiceImplTest {


    private FileServiceImpl sut;

    public static final String FILE_ID = "fileId";
    public static final String IMAGE_EXTENSION = "pdf";
    public static final String ROOT_FOLDER = "rootFolder";
    public static final String RELEASE_FOLDER_NAME = "releaseFolderName";
    public static final String ARCHIVE_FOLDER_NAME = "archiveFolderName";
    public static final String ERROR_FOLDER_NAME = "errorFolderName";

    @Mock
    private SftpService sftpServiceMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(sftpServiceMock).moveFile(Mockito.anyString(), Mockito.anyString());
        sut = new FileServiceImpl(sftpServiceMock);
    }

    @Test
    public void WithFileInfoItShouldMoveFilesToArchive() {

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER, RELEASE_FOLDER_NAME, ARCHIVE_FOLDER_NAME, ERROR_FOLDER_NAME);

        Assertions.assertDoesNotThrow(() -> sut.MoveFilesToArchive(fileInfo));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/releaseFolderName/fileId.pdf"),
                        Mockito.eq("rootFolder/archiveFolderName/fileId.pdf"));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/releaseFolderName/fileId.xml"),
                        Mockito.eq("rootFolder/archiveFolderName/fileId.xml"));

    }

    @Test
    public void WithFileInfoItShouldMoveMetaDataToArchive() {

        FileInfo fileInfo = new FileInfo(FILE_ID, IMAGE_EXTENSION, ROOT_FOLDER, RELEASE_FOLDER_NAME, ARCHIVE_FOLDER_NAME, ERROR_FOLDER_NAME);

        Assertions.assertDoesNotThrow(() -> sut.MoveFilesToError(fileInfo));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/releaseFolderName/fileId.pdf"),
                        Mockito.eq("rootFolder/errorFolderName/fileId.pdf"));

        Mockito.verify(sftpServiceMock, Mockito.times(1))
                .moveFile(Mockito.eq("rootFolder/releaseFolderName/fileId.xml"),
                        Mockito.eq("rootFolder/errorFolderName/fileId.xml"));

    }


}
