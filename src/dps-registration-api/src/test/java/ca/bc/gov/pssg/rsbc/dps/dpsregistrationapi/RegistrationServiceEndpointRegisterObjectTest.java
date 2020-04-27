package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.open.ottsoa.ords.client.OtssoaService;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.pssg.rsbc.dps.dps_registrationservices_wsprovider.dpsdocumentstatusregws.ObjectFactory;
import ca.bc.gov.pssg.rsbc.dps.dps_registrationservices_wsprovider.dpsdocumentstatusregws.SetRegisterObjectRequest;
import ca.bc.gov.pssg.rsbc.dps.dps_registrationservices_wsprovider.dpsdocumentstatusregws.SetRegisterObjectResponse2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationServiceEndpointRegisterObjectTest {

    public static final String REG_STATE = "0";
    public static final String ERROR_MESSAGE = "";
    public static final String CASE_1 = "CASE_1";
    public static final String CASE_2 = "CASE_2";
    public static final String ACTION_METHOD = "action method";
    public static final String ACTION_SYSTEM = "action system";
    public static final String USER = "user";
    public static final String CASE_RESULT = "case result";
    public static final String CASE_UPDATE = "case update";
    public static final String CLIENT_NUMBER = "1";
    public static final String CONTENT_ID = "content id";
    public static final String IMAGE_PDF = "image.pdf";
    public static final String PACKAGE_FORMAT_TYPE = "packageFormatType";
    public static final String TYPE = "type";
    private RegistrationServiceEndpoint sut;

    @Mock
    private OtssoaService otssoaServiceMock;

    @BeforeAll
    public void setUp() throws ApiException {

        MockitoAnnotations.initMocks(this);

        DefaultResponse success = new DefaultResponse();
        success.setRegState(REG_STATE);
        success.setErrorMessage(ERROR_MESSAGE);

        Mockito.doReturn(success).when(otssoaServiceMock).createObject(ArgumentMatchers.argThat(x -> x.getClientName().equals(CASE_1)));
        Mockito.doThrow(new ApiException(500, "internal server error")).when(otssoaServiceMock).createObject(ArgumentMatchers.argThat(x -> x.getClientName().equals(CASE_2)));

        sut = new RegistrationServiceEndpoint(otssoaServiceMock);

    }


    @Test
    public void withValidPayloadShouldRegisterObject() {

        UUID expectedId = UUID.randomUUID();
        ObjectFactory factory = new ObjectFactory();

        SetRegisterObjectRequest request = factory.createSetRegisterObjectRequest();
        request.setActionMethod(factory.createSetRegisterObjectRequestActionMethod(ACTION_METHOD));
        request.setActionSystem(factory.createSetRegisterObjectRequestActionSystem(ACTION_SYSTEM));
        request.setActionUser(factory.createSetRegisterObjectRequestActionUser(USER));
        request.setCaseResult(factory.createSetRegisterObjectRequestCaseResult(CASE_RESULT));
        request.setCaseUpdate(factory.createSetRegisterObjectRequestCaseUpdate(CASE_UPDATE));
        request.setClientName(factory.createSetRegisterObjectRequestClientName(CASE_1));
        request.setClientNum(factory.createSetRegisterObjectRequestClientNum(CLIENT_NUMBER));
        request.setCompletionDTM(factory.createSetRegisterObjectRequestCompletionDTM(DateUtils.toXMLGregorianCalendar(new Date())));
        request.setContentID(factory.createSetRegisterObjectRequestContentID(CONTENT_ID));
        request.setImageUpload(factory.createSetRegisterObjectRequestImageUpload(IMAGE_PDF));
        request.setPackage(factory.createSetRegisterObjectRequestPackage(expectedId.toString()));
        request.setPackageFormatType(PACKAGE_FORMAT_TYPE);
        request.setType(TYPE);

        SetRegisterObjectResponse2 actual = sut.setRegisterObject(request);

        Assertions.assertEquals(REG_STATE, actual.getResponseCd());
        Assertions.assertEquals(ERROR_MESSAGE, actual.getResponseMsg());

    }

    @Test
    public void withApiExceptionShouldReturnError() {

        UUID expectedId = UUID.randomUUID();
        ObjectFactory factory = new ObjectFactory();

        SetRegisterObjectRequest request = new SetRegisterObjectRequest();
        request.setActionMethod(factory.createSetRegisterObjectRequestActionMethod(ACTION_METHOD));
        request.setActionSystem(factory.createSetRegisterObjectRequestActionSystem(ACTION_SYSTEM));
        request.setActionUser(factory.createSetRegisterObjectRequestActionUser(USER));
        request.setCaseResult(factory.createSetRegisterObjectRequestCaseResult(CASE_RESULT));
        request.setCaseUpdate(factory.createSetRegisterObjectRequestCaseUpdate(CASE_UPDATE));
        request.setClientName(factory.createSetRegisterObjectRequestClientName(CASE_2));
        request.setClientNum(factory.createSetRegisterObjectRequestClientNum(CLIENT_NUMBER));
        request.setCompletionDTM(factory.createSetRegisterObjectRequestCompletionDTM(DateUtils.toXMLGregorianCalendar(new Date())));
        request.setContentID(factory.createSetRegisterObjectRequestContentID(CONTENT_ID));
        request.setImageUpload(factory.createSetRegisterObjectRequestImageUpload(IMAGE_PDF));
        request.setPackage(factory.createSetRegisterObjectRequestPackage(expectedId.toString()));
        request.setPackageFormatType(PACKAGE_FORMAT_TYPE);
        request.setType(TYPE);

        SetRegisterObjectResponse2 actual = sut.setRegisterObject(request);

        Assertions.assertEquals(Integer.toString(Keys.ERROR_STATUS_CODE), actual.getResponseCd());
        Assertions.assertEquals("Status code: 500, error internal server error", actual.getResponseMsg());


    }

}
