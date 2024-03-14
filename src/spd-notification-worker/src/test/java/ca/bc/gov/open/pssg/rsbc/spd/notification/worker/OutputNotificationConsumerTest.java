package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.DocumentService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.DpsDataIntoFigaroResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document.DpsDocumentResponse;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.text.MessageFormat;

@DisplayName("OutputNotificationConsumer test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationConsumerTest {

    public static final String CASE_1 = "case1";
    public static final String CASE_SFTP_EXCEPTION = "case2";
    public static final String CASE_JAXB_EXCEPTION = "case3";
    public static final String CASE_1API_ERROR_CODE = "case4";
    public static final String CASE_2API_ERROR_CODE = "case5";

    public static final String DOCUMENT_GUID = "38400000-8cf0-11bd-b23e-10b96e4ef00d";

    private static final String TYPE_CODE_SUCCESS = "1";
    private static final String TYPE_CODE_ERROR = "2";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_CODE_ERROR = "-1";
    private static final String STATUS_MESSAGE = "success";

    public static final String REMOTE_LOCATION = "remote";
    public static final String METADATA = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
            "<Data>\n" +
            "  <BatchData>\n" +
            "    <BatchID>1000120</BatchID>\n" +
            "    <CRCProgramArea>EMP</CRCProgramArea>\n" +
            "    <FaxReceivedDate>2019-03-14T18:39:06Z</FaxReceivedDate>\n" +
            "    <ImportDate>2019-03-25</ImportDate>\n" +
            "    <ImportID>4b36bb87-3105-43fe-a69c-c8f4b48f145b</ImportID>\n" +
            "    <OriginatingNumber></OriginatingNumber>\n" +
            "    <ProgramType>SPD-CRC</ProgramType>\n" +
            "  </BatchData>\n" +
            "  <DocumentData>\n" +
            "    <externalFile>\\\\pastry\\SecurityProgramsDivision\\CRC\\TEST\\release\\000F42DD" +
            ".PDF</externalFile>\n" +
            "    <pnApplPartyId>{0}</pnApplPartyId>\n" +
            "    <pnOrgContactPartyId></pnOrgContactPartyId>\n" +
            "    <pnOrgFacilityPartyId></pnOrgFacilityPartyId>\n" +
            "    <pnOrgPartyId></pnOrgPartyId>\n" +
            "    <pvApplAddlFirstName1>MAQOWAN</pvApplAddlFirstName1>\n" +
            "    <pvApplAddlFirstName2>OXE</pvApplAddlFirstName2>\n" +
            "    <pvApplAddlFirstName3></pvApplAddlFirstName3>\n" +
            "    <pvApplAddlSecondName1>ROBERT</pvApplAddlSecondName1>\n" +
            "    <pvApplAddlSecondName2>TANIA</pvApplAddlSecondName2>\n" +
            "    <pvApplAddlSecondName3></pvApplAddlSecondName3>\n" +
            "    <pvApplAddlSurname1>ORODANE</pvApplAddlSurname1>\n" +
            "    <pvApplAddlSurname2>CHILDES</pvApplAddlSurname2>\n" +
            "    <pvApplAddlSurname3></pvApplAddlSurname3>\n" +
            "    <pvApplBirthDate>1992-12-13</pvApplBirthDate>\n" +
            "    <pvApplBirthPlaceTxt>LEDUC</pvApplBirthPlaceTxt>\n" +
            "    <pvApplCityNm>BLACKFALDS</pvApplCityNm>\n" +
            "    <pvApplCountryNm>CANADA</pvApplCountryNm>\n" +
            "    <pvApplDriversLicence>584905</pvApplDriversLicence>\n" +
            "    <pvApplEmailAddress>JAMES.BRADBURY@GOV.BC.CA</pvApplEmailAddress>\n" +
            "    <pvApplFirstName>SALAIDH</pvApplFirstName>\n" +
            "    <pvApplGenderTxt>F</pvApplGenderTxt>\n" +
            "    <pvApplicationCategory>INCOMPLETE</pvApplicationCategory>\n" +
            "    <pvApplicationGuardianSignedYN>N</pvApplicationGuardianSignedYN>\n" +
            "    <pvApplicationIncompleteReason>NO ORGANIZATION NAME OR PARTY ID WAS PROVIDED. PLEASE PROVIDE THE " +
            "INFORMATION IN LINES BELOW, INCLUDING THE ADDRESS OF THE ORGANIZATION.^CREATE NO JURS LETTER FROM FIGARO" +
            ".</pvApplicationIncompleteReason>\n" +
            "    <pvApplicationNonFinRejectRsn>INVALID_JURISDICTION</pvApplicationNonFinRejectRsn>\n" +
            "    <pvApplicationPaymentId></pvApplicationPaymentId>\n" +
            "    <pvApplicationPaymentMethod>EMAIL</pvApplicationPaymentMethod>\n" +
            "    <pvApplicationSignedDate>2018-12-09</pvApplicationSignedDate>\n" +
            "    <pvApplicationSignedYN>Y</pvApplicationSignedYN>\n" +
            "    <pvApplPhoneNumber>303-863-4704</pvApplPhoneNumber>\n" +
            "    <pvApplPostalCode>V1V T9E</pvApplPostalCode>\n" +
            "    <pvApplProvinceNm>BC</pvApplProvinceNm>\n" +
            "    <pvApplSecondName>HONORIA</pvApplSecondName>\n" +
            "    <pvApplStreetAddress>5449 MCGUIRE ALLEY</pvApplStreetAddress>\n" +
            "    <pvApplSurname>MCLEISH</pvApplSurname>\n" +
            "    <pvDocumentType>CRR010</pvDocumentType>\n" +
            "    <pvJurisdictionType></pvJurisdictionType>\n" +
            "    <pvOrgFacilityName></pvOrgFacilityName>\n" +
            "    <pvProcessingStream>EMAIL_PAYMENT</pvProcessingStream>\n" +
            "    <pvScheduleType>A</pvScheduleType>\n" +
            "    <pvValidationUser>ABELLOSO</pvValidationUser>\n" +
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

        // Sucess Configuration

        Mockito
                .doReturn(fakeContent(getMetadata(TYPE_CODE_SUCCESS)))
                .when(fileServiceMock)
                .getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));

        Mockito
                .doReturn(DpsDocumentResponse.successResponse(DOCUMENT_GUID, STATUS_CODE, STATUS_MESSAGE))
                .when(documentServiceMock)
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_1)));

        Mockito
                .doReturn(DpsDataIntoFigaroResponse.successResponse(STATUS_CODE, STATUS_MESSAGE))
                .when(documentServiceMock)
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(DOCUMENT_GUID)));

        // dpsDocument api ERROR

        Mockito
                .doReturn(fakeContent(getMetadata(TYPE_CODE_SUCCESS)))
                .when(fileServiceMock)
                .getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1API_ERROR_CODE)));

        Mockito
                .doReturn(DpsDocumentResponse.errorResponse(STATUS_CODE_ERROR))
                .when(documentServiceMock)
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_1API_ERROR_CODE)));

        // dpsDataIntoFigaro api ERROR

        Mockito
                .doReturn(fakeContent(getMetadata(TYPE_CODE_SUCCESS)))
                .when(fileServiceMock)
                .getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_2API_ERROR_CODE)));

        Mockito
                .doReturn(DpsDocumentResponse.successResponse(CASE_2API_ERROR_CODE, STATUS_CODE, STATUS_MESSAGE))
                .when(documentServiceMock)
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_2API_ERROR_CODE)));

        Mockito
                .doReturn(DpsDataIntoFigaroResponse.errorResponse(STATUS_CODE_ERROR))
                .when(documentServiceMock)
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(CASE_2API_ERROR_CODE)));


        // sftp ERROR

        Mockito
                .doThrow(DpsSftpException.class)
                .when(fileServiceMock)
                .getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));

        // JAXB ERROR

        Mockito
                .doReturn(fakeContent("Not an xml for sure!"))
                .when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_JAXB_EXCEPTION)));

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);

        sut = new OutputNotificationConsumer(fileServiceMock, sftpProperties, documentServiceMock, jaxbContextMock);
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

        Mockito
                .verify(documentServiceMock, Mockito.times(1))
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_1)));

        Mockito
                .verify(documentServiceMock, Mockito.times(1))
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(DOCUMENT_GUID)));

        Mockito
                .verify(fileServiceMock, Mockito.times(1))
                .moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
    }

    @DisplayName("Exception: test when fileService throws exception.")
    @Test
    public void withSftpErrorShouldStop() {

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_SFTP_EXCEPTION);
            sut.receiveMessage(message);
        });

        Mockito
                .verify(documentServiceMock, Mockito.never())
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_SFTP_EXCEPTION)));

        Mockito
                .verify(documentServiceMock, Mockito.never())
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(DOCUMENT_GUID)));

        Mockito
                .verify(fileServiceMock, Mockito.never())
                .moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));
    }

    @DisplayName("Exception: test serialization of xml failed.")
    @Test
    public void withJaxbExceptionShouldMoveFilesToError() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenThrow(JAXBException.class);

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_JAXB_EXCEPTION);
            sut.receiveMessage(message);
        });

        Mockito
                .verify(documentServiceMock, Mockito.never())
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(getFileInfo(CASE_JAXB_EXCEPTION).getImageReleaseFileName())));

        Mockito
                .verify(documentServiceMock, Mockito.never())
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(DOCUMENT_GUID)));

        Mockito
                .verify(fileServiceMock, Mockito.never())
                .moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_JAXB_EXCEPTION)));

        Mockito
                .verify(fileServiceMock, Mockito.times(1))
                .moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_JAXB_EXCEPTION)));
    }

    @DisplayName("Error, when dpsDocument return an error code")
    @Test
    public void withApiException() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_SUCCESS));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_1API_ERROR_CODE);
            sut.receiveMessage(message);
        });

        Mockito
                .verify(documentServiceMock, Mockito.times(1))
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_1API_ERROR_CODE)));

        Mockito
                .verify(documentServiceMock, Mockito.never())
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(DOCUMENT_GUID)));

        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1API_ERROR_CODE)));
    }

    @DisplayName("Error, when dpsDataIntoFigaro return an error code")
    @Test
    public void withDpsDataIntoFigaroApiException() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_SUCCESS));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_2API_ERROR_CODE);
            sut.receiveMessage(message);
        });

        Mockito
                .verify(documentServiceMock, Mockito.times(1))
                .storeDocument(ArgumentMatchers.argThat(x -> x.getFileName().startsWith(CASE_2API_ERROR_CODE)));

        Mockito
                .verify(documentServiceMock, Mockito.times(1))
                .dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getApplicationDocumentGuid().equals(CASE_2API_ERROR_CODE)));

        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_2API_ERROR_CODE)));
    }

    private FileInfo getFileInfo(String fileId) {
        return new FileInfo(fileId, "PDF", REMOTE_LOCATION, "errorhold");
    }

    private Data getData(String scheduleType) {
        Data dataSuccess = new Data();
        dataSuccess.setDocumentData(new Data.DocumentData());
        dataSuccess.getDocumentData().setPvScheduleType(scheduleType);
        return dataSuccess;
    }

    private ByteArrayInputStream fakeContent(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }

    private String getMetadata(String pvScheduleType) {
        return MessageFormat.format(METADATA, pvScheduleType);
    }

}

