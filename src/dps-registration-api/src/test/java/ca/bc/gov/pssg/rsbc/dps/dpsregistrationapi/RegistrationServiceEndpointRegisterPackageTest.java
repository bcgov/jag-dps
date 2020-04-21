package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.ObjectFactory;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterPackageRequest;
import ca.bc.gov.open.pssg.rsbc.dps.dpsregistrationapi.generated.models.SetRegisterPackageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceEndpointRegisterPackageTest {

    private static final String CASE_1 = "case1";
    private static final String CASE_2 = "case2";

    private static final String FILENAME_XML = "filename.xml";
    private static final String PACKAGE_FORMAT_TYPE = "1";
    private static final String PAGE_COUNT = "1";
    private static final String PROGRAM_TYPE = "program type";
    private static final String RECIPIENT = "recipient";
    private static final String RECORD_COUNT = "2";
    private static final String SOURCE = "source";
    public static final String EXCEPTION_MESSAGE = "exception message";
    private RegistrationServiceEndpoint sut;

    public static final String REG_STATE = "0";
    public static final String EMPTY_STRING = "";


    @Mock
    private OtssoaService otssoaServiceMock;

    @BeforeAll
    public void setUp() throws ApiException {

        MockitoAnnotations.initMocks(this);

        DefaultResponse success = new DefaultResponse();
        success.setRegState(REG_STATE);
        success.setErrorMessage(EMPTY_STRING);

        Mockito.doReturn(success).when(otssoaServiceMock).createPackage(ArgumentMatchers.argThat(x -> x.getBusinessArea().equals(CASE_1)));
        Mockito.doThrow(new ApiException(404, EXCEPTION_MESSAGE)).when(otssoaServiceMock).createPackage(ArgumentMatchers.argThat(x -> x.getBusinessArea().equals(CASE_2)));



        sut = new RegistrationServiceEndpoint(otssoaServiceMock);
    }

    private XMLGregorianCalendar getGregorianCalendar() {
        return DateUtils.toXMLGregorianCalendar(new Date());
    }

    @Test
    public void withValidPayloadShouldReturnExepcted() {

        UUID expectedId = UUID.randomUUID();
        ObjectFactory factory = new ObjectFactory();

        SetRegisterPackageRequest request = new SetRegisterPackageRequest();

        request.setBusinessAreaCD(CASE_1);
        request.setFilename(FILENAME_XML);
        request.setImportGUID(expectedId.toString());
        request.setPackageFormatType(PACKAGE_FORMAT_TYPE);
        request.setPageCount(factory.createSetRegisterPackageRequestPageCount(PAGE_COUNT));
        request.setProgramType(factory.createSetRegisterPackageRequestProgramType(PROGRAM_TYPE));
        request.setReceivedDTM(getGregorianCalendar());
        request.setRecipient(factory.createSetRegisterPackageRequestRecipient(RECIPIENT));
        request.setRecordCount(factory.createSetRegisterPackageRequestRecordCount(RECORD_COUNT));
        request.setSource(factory.createSetRegisterPackageRequestSource(SOURCE));

        SetRegisterPackageResponse actual = sut.registerPackage(request);

        Assertions.assertEquals(REG_STATE, actual.getSetRegisterPackageResponse().getResponseCd());
        Assertions.assertEquals(EMPTY_STRING, actual.getSetRegisterPackageResponse().getResponseMsg());

    }

    @Test
    public void withApiExceptionShouldReturnExepcted() {

        UUID expectedId = UUID.randomUUID();
        ObjectFactory factory = new ObjectFactory();

        SetRegisterPackageRequest request = new SetRegisterPackageRequest();

        request.setBusinessAreaCD(CASE_2);
        request.setFilename(FILENAME_XML);
        request.setImportGUID(expectedId.toString());
        request.setPackageFormatType(PACKAGE_FORMAT_TYPE);
        request.setPageCount(factory.createSetRegisterPackageRequestPageCount(PAGE_COUNT));
        request.setProgramType(factory.createSetRegisterPackageRequestProgramType(PROGRAM_TYPE));
        request.setReceivedDTM(getGregorianCalendar());
        request.setRecipient(factory.createSetRegisterPackageRequestRecipient(RECIPIENT));
        request.setRecordCount(factory.createSetRegisterPackageRequestRecordCount(RECORD_COUNT));
        request.setSource(factory.createSetRegisterPackageRequestSource(SOURCE));

        SetRegisterPackageResponse actual = sut.registerPackage(request);

        Assertions.assertEquals(Integer.toString(Keys.ERROR_STATUS_CODE), actual.getSetRegisterPackageResponse().getResponseCd());

        Assertions.assertEquals("Status code: 404, error exception message", actual.getSetRegisterPackageResponse().getResponseMsg());

    }




}
