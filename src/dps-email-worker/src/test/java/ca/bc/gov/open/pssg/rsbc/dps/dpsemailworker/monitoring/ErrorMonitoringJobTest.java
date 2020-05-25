package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.monitoring;

import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.Fakes;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.configuration.TenantProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.KofaxProperties;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models.ImportSession;
import ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.services.ImportSessionService;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ErrorMonitoringJobTest {

    public static final String REMOTE_LOCATION = "remotelocation";
    public static final String CASE_1 = "case1";
    public static final String CASE_2 = "case2";

    private ErrorMonitoringJob sut;

    @Mock
    private KofaxProperties kofaxPropertiesMock;

    @Mock
    private FileService fileServiceMock;

    @Mock
    private ImportSessionService importSessionServiceMock;

    @BeforeEach
    public void setUp() {

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        TenantProperties tenantProperties = new TenantProperties();
        tenantProperties.setName("tenant");

        MockitoAnnotations.initMocks(this);

        List<String> fileList1 = new ArrayList<>();
        fileList1.add(CASE_1 + ".xml");
        fileList1.add(".");

        Mockito
                .when(fileServiceMock.listFiles(Mockito.eq(REMOTE_LOCATION + "/" + CASE_1)))
                .thenReturn(fileList1);

        List<String> fileList2 = new ArrayList<>();
        fileList2.add(CASE_1 + ".xml");
        fileList2.add(CASE_2 + ".xml");

        Mockito
                .when(fileServiceMock.listFiles(Mockito.eq(REMOTE_LOCATION + "/" + CASE_2)))
                .thenReturn(fileList2);

        Mockito
                .when(fileServiceMock.getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_1 + "/" + CASE_1 + ".xml")))
                .thenReturn(Fakes.getImportSessionInputStream());

        Mockito
                .when(fileServiceMock.getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_2 + "/" + CASE_1 + ".xml")))
                .thenReturn(Fakes.getImportSessionInputStream());

        Mockito
                .when(fileServiceMock.getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_2 + "/" + CASE_2 + ".xml")))
                .thenThrow(new DpsSftpException("a random exception", null));

        ImportSession importSessionCase1 = new ImportSession("userId", "password", "1", "an error");

        Mockito.when(importSessionServiceMock.convertToImportSession(Mockito.any(InputStream.class))).thenReturn(importSessionCase1);


        sut = new ErrorMonitoringJob(fileServiceMock, importSessionServiceMock, kofaxPropertiesMock, sftpProperties,
                tenantProperties);

    }


    @Test
    public void withFileShouldProcess() {

        Mockito.when(kofaxPropertiesMock.getErrorLocation()).thenReturn(CASE_1);


        Assertions.assertDoesNotThrow(() -> sut.run());

        Mockito.verify(fileServiceMock, Mockito.times(1)).getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_1 + "/" + CASE_1 + ".xml"));

        Mockito.verify(importSessionServiceMock, Mockito.times(1)).convertToImportSession(Mockito.any(InputStream.class));

    }

    @Test
    public void withExceptionShouldBeHandled() {

        Mockito.when(kofaxPropertiesMock.getErrorLocation()).thenReturn(CASE_2);

        sut.run();

        Mockito.verify(fileServiceMock, Mockito.times(1)).getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_2 + "/" + CASE_1 + ".xml"));
        Mockito.verify(fileServiceMock, Mockito.times(1)).getFileContent(Mockito.eq(REMOTE_LOCATION + "/" + CASE_2 + "/" + CASE_2 + ".xml"));

        Mockito.verify(importSessionServiceMock, Mockito.times(1)).convertToImportSession(Mockito.any(InputStream.class));

    }








}
