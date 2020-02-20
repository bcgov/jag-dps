package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.files.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.DpsDataIntoFigaroRequestBodyAdapterToMapper;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.DpsDocumentRequestBody;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.DpsDocumentResponse;
import com.migcomponents.migbase64.Base64;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
    public static final String METADATA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<Data>\n" +
            "  <BatchData>\n" +
            "    <BatchId>str1234</BatchId>\n" +
            "    <FaxReceivedDate>str1234</FaxReceivedDate>\n" +
            "    <ImportDate>str1234</ImportDate>\n" +
            "    <ImportID>str1234</ImportID>\n" +
            "    <OriginatingNumber>str1234</OriginatingNumber>\n" +
            "    <ProgramType>str1234</ProgramType>\n" +
            "  </BatchData>\n" +
            "  <DocumentData>\n" +
            "    <externalFile>str1234</externalFile>\n" +
            "    <pvApplicationIncompleteReason>str1234</pvApplicationIncompleteReason>\n" +
            "    <documentType>str1234</documentType>\n" +
            "    <pnApplPartyId>str1234</pnApplPartyId>\n" +
            "    <pvApplPhoneNumber>str1234</pvApplPhoneNumber>\n" +
            "    <pnOrgFacilityPartyId>str1234</pnOrgFacilityPartyId>\n" +
            "    <pnOrgPartyId>str1234</pnOrgPartyId>\n" +
            "    <pvApplAddlFirstName1>str1234</pvApplAddlFirstName1>\n" +
            "    <pvApplAddlFirstName2>str1234</pvApplAddlFirstName2>\n" +
            "    <pvApplAddlFirstName3>str1234</pvApplAddlFirstName3>\n" +
            "    <pvApplAddlSecondName1>str1234</pvApplAddlSecondName1>\n" +
            "    <pvApplAddlSecondName2>str1234</pvApplAddlSecondName2>\n" +
            "    <pvApplAddlSecondName3>str1234</pvApplAddlSecondName3>\n" +
            "    <pvApplAddlSurname1>str1234</pvApplAddlSurname1>\n" +
            "    <pvApplAddlSurname2>str1234</pvApplAddlSurname2>\n" +
            "    <pvApplAddlSurname3>str1234</pvApplAddlSurname3>\n" +
            "    <pvApplBirthPlaceTxt>str1234</pvApplBirthPlaceTxt>\n" +
            "    <pvApplCityNm>str1234</pvApplCityNm>\n" +
            "    <pvApplCountryNm>str1234</pvApplCountryNm>\n" +
            "    <pvApplDriversLicence>str1234</pvApplDriversLicence>\n" +
            "    <pvApplEmailAddress>str1234</pvApplEmailAddress>\n" +
            "    <pvApplBirthDate>str1234</pvApplBirthDate>\n" +
            "    <pvApplFirstName>str1234</pvApplFirstName>\n" +
            "    <pvApplGenderTxt>str1234</pvApplGenderTxt>\n" +
            "    <pvApplicationCategory>str1234</pvApplicationCategory>\n" +
            "    <pvApplicationGuardianSignedYN>str1234</pvApplicationGuardianSignedYN>\n" +
            "    <pvApplicationNonFinRejectRsn>str1234</pvApplicationNonFinRejectRsn>\n" +
            "    <pvApplicationPaymentId>str1234</pvApplicationPaymentId>\n" +
            "    <pvApplicationPaymentMethod>str1234</pvApplicationPaymentMethod>\n" +
            "    <pvApplicationSignedDate>str1234</pvApplicationSignedDate>\n" +
            "    <pvApplicationSignedYN>str1234</pvApplicationSignedYN>\n" +
            "    <pvApplPostalCode>str1234</pvApplPostalCode>\n" +
            "    <pvApplProvinceNm>str1234</pvApplProvinceNm>\n" +
            "    <pvApplSecondName>str1234</pvApplSecondName>\n" +
            "    <pvApplStreetAddress>str1234</pvApplStreetAddress>\n" +
            "    <pvApplSurname>str1234</pvApplSurname>\n" +
            "    <pvDocumentType>str1234</pvDocumentType>\n" +
            "    <processingStream>str1234</processingStream>\n" +
            "    <pvJurisdictionType>str1234</pvJurisdictionType>\n" +
            "    <pvOrgFacilityName>str1234</pvOrgFacilityName>\n" +
            "    <pvProcessingStream>str1234</pvProcessingStream>\n" +
            "    <pvScheduleType>str1234</pvScheduleType>\n" +
            "    <pnOrgContactPartyId>str1234</pnOrgContactPartyId>\n" +
            "    <pvValidationUser>str1234</pvValidationUser>\n" +
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

    @Mock
    private DpsDataIntoFigaroRequestBodyAdapterToMapper mapperMock;

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

//        DpsDocumentResponse successResponse = DpsDocumentResponse.SuccessResponse(DOCUMENT_ID, STATUS_CODE, STATUS_MESSAGE);
//        DpsDocumentRequestBody documentSuccessRequestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_1);
//        Mockito.when(documentServiceMock.dpsDocument(documentSuccessRequestBody)).thenReturn(successResponse);
//
//        DpsDocumentResponse errorResponse = DpsDocumentResponse.ErrorResponse("error result");
//        DpsDocumentRequestBody documentErrorRequestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_SFTP_EXCEPTION);
//        Mockito.when(documentServiceMock.dpsDocument(documentErrorRequestBody)).thenReturn(errorResponse);

        sut = new OutputNotificationConsumer(mapperMock, fileServiceMock, sftpProperties, documentServiceMock, jaxbContextMock);
    }

    @DisplayName("Success: test with valid message")
    @Test
    public void withAMessageShouldProcess() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_SUCCESS));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_1);
            sut.receiveMessage(message);
        });

//        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDocument(
//                Mockito.eq(TYPE_CODE_SUCCESS),
//                Mockito.eq(Base64.encodeToString(getMetadata(TYPE_CODE_SUCCESS).getBytes(),false)),
//                Mockito.eq("application"),
//                Mockito.eq("pdf"),
//                Mockito.anyString(),
//                Mockito.any(File.class));
//
//        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));

    }
    /*
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
    */

    private Data getData(String scheduleType) {
        Data dataSuccess = new Data();
        dataSuccess.setDocumentData(new Data.DocumentData());
        dataSuccess.getDocumentData().setPvScheduleType(scheduleType);
        return dataSuccess;
    }

    private ByteArrayInputStream fakeContent(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }

    private String getMetadata(String dtype) {
        return MessageFormat.format(METADATA, dtype);
    }

}

