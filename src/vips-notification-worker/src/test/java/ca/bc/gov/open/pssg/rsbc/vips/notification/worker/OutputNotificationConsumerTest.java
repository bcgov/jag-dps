package ca.bc.gov.open.pssg.rsbc.vips.notification.worker;

import ca.bc.gov.open.jagvipsclient.document.DocumentService;
import ca.bc.gov.open.jagvipsclient.document.VipsDocumentResponse;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.vips.notification.worker.generated.models.Data;
import com.migcomponents.migbase64.Base64;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Reader;
import java.text.MessageFormat;

@DisplayName("OutputNotificationConsumer test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationConsumerTest {

    public static final String CASE_1 = "case1";
    public static final String CASE_SFTP_EXCEPTION = "case2";
    public static final String CASE_JAXB_EXCEPTION = "case3";
    public static final String CASE_API_ERROR_CODE = "case4";

    private static final String TYPE_CODE_SUCCESS = "1";
    private static final String TYPE_CODE_ERROR = "2";
    private static final String DOCUMENT_ID = "123";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_CODE_ERROR = "-1";
    private static final String STATUS_MESSAGE = "success";

    public static final String REMOTE_LOCATION = "remote";
    public static final String METADATA = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
            "<Data>\n" +
            "  <BatchData>\n" +
            "    <BatchID>81</BatchID>\n" +
            "    <FaxReceivedDate>2019-12-16T23:23:58Z</FaxReceivedDate>\n" +
            "    <ImportDate>2019-12-16</ImportDate>\n" +
            "    <ImportID>'{2a37d377-f099-4d8f-af7c-9e74c4fcc58f}'</ImportID>\n" +
            "    <OriginatingNumber></OriginatingNumber>\n" +
            "  </BatchData>\n" +
            "  <DocumentData>\n" +
            "    <externalFile>E:\\VIPS\\Release\\0000002D.PDF</externalFile>\n" +
            "    <adpNumber></adpNumber>\n" +
            "    <documentPages>1</documentPages>\n" +
            "    <dType>{0}</dType>\n" +
            "    <irpNumber>123456789</irpNumber>\n" +
            "    <policeFileNo></policeFileNo>\n" +
            "    <ulNumber></ulNumber>\n" +
            "    <validationUser>VALIDATION_USER</validationUser>\n" +
            "    <vinoiNumber></vinoiNumber>\n" +
            "  </DocumentData>\n" +
            "</Data>";

    private OutputNotificationConsumer sut;

    @Mock
    private FileService fileServiceMock;

    @Mock
    private DocumentService documentServiceMock;

    @Mock
    private JAXBContext jaxbContextMock;

    @Mock
    private Unmarshaller unmarshallerMock;

    @BeforeEach
    public void setUp() throws JAXBException {

        MockitoAnnotations.initMocks(this);

        Mockito.doNothing().when(fileServiceMock).moveFilesToArchive(Mockito.any(FileInfo.class));
        Mockito.doNothing().when(fileServiceMock).moveFilesToError(Mockito.any(FileInfo.class));

        Mockito.doReturn(fakeContent(getMetadata(TYPE_CODE_SUCCESS))).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
        Mockito.doReturn(fakeContent("I'm a file")).when(fileServiceMock).getImageFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));

        Mockito.doThrow(DpsSftpException.class).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));

        Mockito.doReturn(fakeContent("Not an xml for sure!")).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_JAXB_EXCEPTION)));

        Mockito.doReturn(fakeContent(getMetadata(TYPE_CODE_ERROR))).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));
        Mockito.doReturn(fakeContent("I'm a file")).when(fileServiceMock).getImageFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        VipsDocumentResponse successResponse = VipsDocumentResponse.successResponse(DOCUMENT_ID, STATUS_CODE, STATUS_MESSAGE);
        Mockito.when(documentServiceMock.vipsDocument(Mockito.eq(TYPE_CODE_SUCCESS), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),Mockito.any())).thenReturn(successResponse);

        VipsDocumentResponse errorResponse = VipsDocumentResponse.errorResponse("error result");
        Mockito.when(documentServiceMock.vipsDocument(Mockito.eq(TYPE_CODE_ERROR), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),Mockito.any())).thenReturn(errorResponse);

        sut = new OutputNotificationConsumer(fileServiceMock, sftpProperties, documentServiceMock, jaxbContextMock);
    }

    @DisplayName("Success: test with valid message")
    @Test
    public void withAMessageShouldProcess() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_SUCCESS));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, CASE_1);
            sut.receiveMessage(message);
        });

        Mockito.verify(documentServiceMock, Mockito.times(1)).vipsDocument(
                Mockito.eq(TYPE_CODE_SUCCESS),
                Mockito.eq(Base64.encodeToString(getMetadata(TYPE_CODE_SUCCESS).getBytes(),false)),
                Mockito.eq("application"),
                Mockito.eq("pdf"),
                Mockito.anyString(),
                Mockito.any(File.class));

        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));

    }

    @DisplayName("Exception: test when fileService throws exception.")
    @Test
    public void withSftpErrorShouldStop() {

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, CASE_SFTP_EXCEPTION);
            sut.receiveMessage(message);
        });

        Mockito.verify(documentServiceMock, Mockito.never()).vipsDocument(
                Mockito.anyString(),
                Mockito.eq(Base64.encodeToString(METADATA.getBytes(),false)),
                Mockito.eq("application"),
                Mockito.eq("pdf"),
                Mockito.anyString(),
                Mockito.any(File.class));

        Mockito.verify(fileServiceMock, Mockito.never()).moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
    }

    @DisplayName("Exception: test serialization of xml failed.")
    @Test
    public void withJaxbExceptionShouldMoveFilesToError() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenThrow(JAXBException.class);

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, CASE_JAXB_EXCEPTION);
            sut.receiveMessage(message);
        });

        Mockito.verify(documentServiceMock, Mockito.never()).vipsDocument(
                Mockito.anyString(),
                Mockito.eq(Base64.encodeToString(METADATA.getBytes(),false)),
                Mockito.eq("application"),
                Mockito.eq("pdf"),
                Mockito.anyString(),
                Mockito.any(File.class));

        Mockito.verify(fileServiceMock, Mockito.never()).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));
    }

    @DisplayName("Error, when document service return an error code")
    @Test
    public void withApiException() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_ERROR));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.VIPS_VALUE, CASE_API_ERROR_CODE);
            sut.receiveMessage(message);
        });

        Mockito.verify(documentServiceMock, Mockito.times(1)).vipsDocument(
                Mockito.eq(TYPE_CODE_ERROR),
                Mockito.eq(Base64.encodeToString(getMetadata(TYPE_CODE_ERROR).getBytes(),false)),
                Mockito.eq("application"),
                Mockito.eq("pdf"),
                Mockito.anyString(),
                Mockito.any(File.class));

        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));

    }

    private Data getData(String dtype) {
        Data dataSuccess = new Data();
        dataSuccess.setDocumentData(new Data.DocumentData());
        dataSuccess.getDocumentData().setDType(dtype);
        return dataSuccess;
    }

    private ByteArrayInputStream fakeContent(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }

    private String getMetadata(String dtype) {
        return MessageFormat.format(METADATA, dtype);
    }

}

