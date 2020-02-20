package ca.bc.gov.open.pssg.rsbc.spd.notification.worker;

import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsRequestBody;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileInfo;
import ca.bc.gov.open.pssg.rsbc.dps.files.FileService;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.DpsSftpException;
import ca.bc.gov.open.pssg.rsbc.dps.sftp.starter.SftpProperties;
import ca.bc.gov.open.pssg.rsbc.dps.spd.notification.worker.generated.models.Data;
import ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
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
            "    <BatchID>1000120</BatchID>\n" +
            "    <CRCProgramArea>EMP</CRCProgramArea>\n" +
            "    <FaxReceivedDate>2019-03-14T18:39:06Z</FaxReceivedDate>\n" +
            "    <ImportDate>2019-03-25</ImportDate>\n" +
            "    <ImportID>4b36bb87-3105-43fe-a69c-c8f4b48f145b</ImportID>\n" +
            "    <OriginatingNumber></OriginatingNumber>\n" +
            "    <ProgramType>SPD-CRC</ProgramType>\n" +
            "  </BatchData>\n" +
            "  <DocumentData>\n" +
            "    <externalFile>\\\\pastry\\SecurityProgramsDivision\\CRC\\TEST\\release\\000F42DD.PDF</externalFile>\n" +
            "    <pnApplPartyId>425381</pnApplPartyId>\n" +
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
            "    <pvApplicationIncompleteReason>NO ORGANIZATION NAME OR PARTY ID WAS PROVIDED. PLEASE PROVIDE THE INFORMATION IN LINES BELOW, INCLUDING THE ADDRESS OF THE ORGANIZATION.^CREATE NO JURS LETTER FROM FIGARO.</pvApplicationIncompleteReason>\n" +
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

        Mockito.doReturn(fakeContent(getMetadata(TYPE_CODE_SUCCESS))).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
        Mockito.doReturn(fakeContent("I'm a file")).when(fileServiceMock).getImageFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));

        Mockito.doThrow(DpsSftpException.class).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));

        Mockito.doReturn(fakeContent("Not an xml for sure!")).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_JAXB_EXCEPTION)));

        Mockito.doReturn(fakeContent(getMetadata(TYPE_CODE_ERROR))).when(fileServiceMock).getMetadataFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));
        Mockito.doReturn(fakeContent("I'm a file")).when(fileServiceMock).getImageFileContent(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));

        SftpProperties sftpProperties = new SftpProperties();
        sftpProperties.setRemoteLocation(REMOTE_LOCATION);


//        DpsDocumentResponse successResponse1 = DpsDocumentResponse.SuccessResponse(DOCUMENT_ID, STATUS_CODE, STATUS_MESSAGE);
//        Mockito.doReturn(successResponse1).when(documentServiceMock).dpsDocument(ArgumentMatchers.argThat(x -> x.getFileName().equals(CASE_1)));
//
//        DpsDataIntoFigaroResponse successResponse2 = DpsDataIntoFigaroResponse.SuccessResponse(STATUS_CODE, STATUS_MESSAGE);
//        Mockito.doReturn(successResponse2).when(documentServiceMock).dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(CASE_1)));
//
//        DpsDocumentResponse errorResponse1 = DpsDocumentResponse.ErrorResponse("F");
//        Mockito.doReturn(errorResponse1).when(documentServiceMock).dpsDocument(ArgumentMatchers.argThat(x -> x.getFileName().equals(CASE_1)));
//
//        DpsDataIntoFigaroResponse errorResponse2 = DpsDataIntoFigaroResponse.ErrorResponse("F");
//        Mockito.doReturn(errorResponse2).when(documentServiceMock).dpsDataIntoFigaro(ArgumentMatchers.argThat(x -> x.getScheduleType().equals(CASE_1)));
//
//        DpsDocumentRequestBody successRequestBody1 = new DpsDocumentRequestBody(Mockito.anyString(), CASE_1);
//        Mockito.when(documentServiceMock.dpsDocument(successRequestBody1)).thenReturn(successResponse1);



        DpsDocumentResponse successResponse1 = DpsDocumentResponse.SuccessResponse(DOCUMENT_ID, STATUS_CODE, STATUS_MESSAGE);
        Mockito.when(documentServiceMock.dpsDocument(Mockito.any(DpsDocumentRequestBody.class))).thenReturn(successResponse1);

        DpsDataIntoFigaroResponse successResponse2 = DpsDataIntoFigaroResponse.SuccessResponse(STATUS_CODE, STATUS_MESSAGE);
        Mockito.when(documentServiceMock.dpsDataIntoFigaro(Mockito.any(DpsDataIntoFigaroRequestBody.class))).thenReturn(successResponse2);



//        DpsDocumentResponse errorResponse = DpsDocumentResponse.ErrorResponse("error result");
//        DpsDocumentRequestBody errorRequestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_SFTP_EXCEPTION);
//        Mockito.when(documentServiceMock.dpsDocument(errorRequestBody)).thenReturn(errorResponse);

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

//        DpsDocumentRequestBody requestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_1);
//        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDocument(requestBody);

        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDocument(Mockito.any(DpsDocumentRequestBody.class));

//        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
    }

    @DisplayName("Exception: test when fileService throws exception.")
    @Test
    public void withSftpErrorShouldStop() {

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_SFTP_EXCEPTION);
            sut.receiveMessage(message);
        });

//        DpsDocumentRequestBody requestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_SFTP_EXCEPTION);
//        Mockito.verify(documentServiceMock, Mockito.never()).dpsDocument(requestBody);
        Mockito.verify(documentServiceMock, Mockito.never()).dpsDocument(Mockito.any(DpsDocumentRequestBody.class));

//        Mockito.verify(fileServiceMock, Mockito.never()).moveFilesToArchive(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_1)));
    }

    @DisplayName("Exception: test serialization of xml failed.")
    @Test
    public void withJaxbExceptionShouldMoveFilesToError() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenThrow(JAXBException.class);

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_JAXB_EXCEPTION);
            sut.receiveMessage(message);
        });

//        DpsDocumentRequestBody requestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_JAXB_EXCEPTION);
//        Mockito.verify(documentServiceMock, Mockito.never()).dpsDocument(requestBody);
        Mockito.verify(documentServiceMock, Mockito.never()).dpsDocument(Mockito.any(DpsDocumentRequestBody.class));

//        Mockito.verify(fileServiceMock, Mockito.never()).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_SFTP_EXCEPTION)));
    }

    @DisplayName("Error, when document service - dpsDocument return an error code")
    @Test
    public void withApiException1() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_ERROR));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_API_ERROR_CODE);
            sut.receiveMessage(message);
        });

//        DpsDocumentRequestBody requestBody = new DpsDocumentRequestBody(Mockito.anyString(), CASE_API_ERROR_CODE);
//        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDocument(requestBody);
        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDocument(Mockito.any(DpsDocumentRequestBody.class));

//        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));
    }

    @DisplayName("Error, when document service - dpsDataIntoFigaro return an error code")
    @Test
    public void withApiException2() throws JAXBException {

        Mockito.when(jaxbContextMock.createUnmarshaller()).thenReturn(unmarshallerMock);
        Mockito.when(unmarshallerMock.unmarshal(Mockito.any(Reader.class))).thenReturn(getData(TYPE_CODE_ERROR));

        Assertions.assertDoesNotThrow(() -> {
            OutputNotificationMessage message = new OutputNotificationMessage(Keys.CRRP_VALUE, CASE_API_ERROR_CODE);
            sut.receiveMessage(message);
        });

//        DpsDataIntoFigaroRequestBody requestBody = new DpsDataIntoFigaroRequestBody.Builder().build();
//        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDataIntoFigaro(requestBody);

        Mockito.verify(documentServiceMock, Mockito.times(1)).dpsDataIntoFigaro(Mockito.any(DpsDataIntoFigaroRequestBody.class));

//        Mockito.verify(fileServiceMock, Mockito.times(1)).moveFilesToError(ArgumentMatchers.argThat(x -> x.getFileId().equals(CASE_API_ERROR_CODE)));
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

