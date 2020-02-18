package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.files.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpService;
import ca.bc.gov.open.pssg.rsbc.dps.vips.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document.VipsDocumentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.Reader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationConsumerTest {

    private static final String TYPE_CODE_SUCCESS = "1";
    private static final String DOCUMENT_ID = "123";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";

    public static final String CASE_1 = "case1";
    public static final String REMOTE_LOCATION = "remote";
    private OutputNotificationConsumer sut;

    @Mock
    private SftpService sftpServiceMock;

    @Mock
    private FileService fileServiceMock;

    @Mock
    private DocumentService documentServiceMock;

    @Mock
    private JAXBContext jaxbContextMock;

    @Mock
    private Unmarshaller unmarshallerMock;

    private ByteArrayInputStream fakeContent() {
        String content = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
                "<Data>\n" +
                "  <BatchData>\n" +
                "    <BatchID>81</BatchID>\n" +
                "    <FaxReceivedDate>2019-12-16T23:23:58Z</FaxReceivedDate>\n" +
                "    <ImportDate>2019-12-16</ImportDate>\n" +
                "    <ImportID>{2a37d377-f099-4d8f-af7c-9e74c4fcc58f}</ImportID>\n" +
                "    <OriginatingNumber></OriginatingNumber>\n" +
                "  </BatchData>\n" +
                "  <DocumentData>\n" +
                "    <externalFile>E:\\VIPS\\Release\\0000002D.PDF</externalFile>\n" +
                "    <adpNumber></adpNumber>\n" +
                "    <documentPages>1</documentPages>\n" +
                "    <dType>REQDOCMOC</dType>\n" +
                "    <irpNumber>212112312</irpNumber>\n" +
                "    <policeFileNo></policeFileNo>\n" +
                "    <ulNumber></ulNumber>\n" +
                "    <validationUser>KYLEAR_A</validationUser>\n" +
                "    <vinoiNumber></vinoiNumber>\n" +
                "  </DocumentData>\n" +
                "</Data>";
        return new ByteArrayInputStream(content.getBytes());
    }

    @BeforeAll
    public void setUp() throws JAXBException {

        MockitoAnnotations.initMocks(this);

        Mockito.when(sftpServiceMock.getContent(Mockito.eq(REMOTE_LOCATION + "/release/" + CASE_1 + ".xml"))).thenReturn(fakeContent());
        Mockito.when(sftpServiceMock.getContent(Mockito.eq(REMOTE_LOCATION + "/release/" + CASE_1 + ".PDF"))).thenReturn(fakeContent());

        Mockito.doNothing().when(fileServiceMock).MoveFilesToArchive(Mockito.any(FileInfo.class));
        Mockito.doNothing().when(fileServiceMock).MoveFilesToError(Mockito.any(FileInfo.class));


        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        Data dataSuccess = new Data();
        dataSuccess.setDocumentData(new Data.DocumentData());
        dataSuccess.getDocumentData().setDType(TYPE_CODE_SUCCESS);

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(dataSuccess);

        VipsDocumentResponse successResponse = VipsDocumentResponse.SuccessResponse(DOCUMENT_ID, STATUS_CODE, STATUS_MESSAGE);
        Mockito.when(documentServiceMock.vipsDocument( Mockito.eq(TYPE_CODE_SUCCESS), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),Mockito.any())).thenReturn(successResponse);

        sut = new OutputNotificationConsumer(sftpServiceMock, fileServiceMock, sftpProperties, documentServiceMock, jaxbContextMock);
    }

    @Test
    public void withAMessageShouldProcess() {

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, CASE_1);
            sut.receiveMessage(message);
        });
    }
}

