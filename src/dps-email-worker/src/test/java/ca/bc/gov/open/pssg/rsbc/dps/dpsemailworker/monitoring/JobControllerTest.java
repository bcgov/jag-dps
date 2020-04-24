package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JobControllerTest {

    @Mock
    private FileService fileServiceMock;

    @Mock
    private ExecutorService executorServiceMock;

    private JobController sut;

    @BeforeAll
    public void setUp() {

        List<String> fakeFileList = new ArrayList<>();
        fakeFileList.add("file.xml");

        MockitoAnnotations.initMocks(this);
        Mockito.when(fileServiceMock.listFiles(Mockito.anyString())).thenReturn(fakeFileList);

        KofaxProperties kofaxProperties = new KofaxProperties();
        kofaxProperties.setErrorLocation("error");

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation("upload");

        sut = new JobController(fileServiceMock, executorServiceMock, kofaxProperties, sftpProperties);

    }

    @Test
    @DisplayName("Success - when job is scheduled")
    public void withCallShouldScheduleJob() {

        ResponseEntity<JobResponse> actual = sut.createErrorJob();

        Assertions.assertEquals(HttpStatus.ACCEPTED, actual.getStatusCode());

        Assertions.assertTrue(actual.getBody().getScheduled());

        Mockito.verify(executorServiceMock, Mockito.times(1)).execute(Mockito.any(Runnable.class));

    }

}
