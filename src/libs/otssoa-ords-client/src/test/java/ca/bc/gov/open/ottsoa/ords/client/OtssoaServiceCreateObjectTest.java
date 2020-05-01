package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.CreateObjectPayload;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreateObjectRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OtssoaServiceCreateObjectTest {

    private static final String BUSINESS_AREA = "BusinessArea";
    private static final String FILENAME = "test.xml";
    private static final String FORMAT_TYPE = "1";
    private static final int PAGE_COUNT = 1;
    private static final String PROGRAM_TYPE = "program type";
    private static final String RECIPIENT = "recipient";
    private static final int RECORD_COUNT = 2;
    private static final String SOURCE = "source";
    public static final String ACTION_METHOD = "action method";
    public static final String ACTION_SYSTEM = "action system";
    public static final String ACTION_USER = "action user";
    public static final String CASE_RESULT = "Case Result";
    public static final String CASE_UPLOAD = "case upload";
    public static final String CLIENT_NAME = "client name";
    public static final String CLIENT_NUMBER = "client number";
    public static final String CONTENT_ID = "content id";
    public static final String CONTENT_TYPE = " ";
    public static final String IMAGE_UPLOAD = "image upload";
    public static final String PACKAGE_FORMAT_TYPE = "package format type";

    private OtssoaServiceImpl sut;

    @Mock
    private OtssoaApi otssoaApiMock;


    @BeforeAll
    public void setUp() throws ApiException {
        MockitoAnnotations.initMocks(this);

        DefaultResponse success = new DefaultResponse();
        success.setRegState("0");

        Mockito.when(otssoaApiMock.createObjectPost(Mockito.any(CreateObjectPayload.class))).thenReturn(success);

        sut = new OtssoaServiceImpl(otssoaApiMock);
    }


    @Test
    public void withValidPayloadItShouldReturnSuccess() throws ApiException {

        UUID expectedImportGuid = UUID.randomUUID();
        Date expectedCompletionDate = new Date();


        CreateObjectRequest request = new CreateObjectRequest
                .Builder()
                .withActionMethod(ACTION_METHOD)
                .withActionSystem(ACTION_SYSTEM)
                .withActionUser(ACTION_USER)
                .withCaseResults(CASE_RESULT)
                .withCaseUpdate(CASE_UPLOAD)
                .withClientName(CLIENT_NAME)
                .withClientNumber(CLIENT_NUMBER)
                .withCompletionDate(expectedCompletionDate)
                .withContentId(CONTENT_ID)
                .withContentType(CONTENT_TYPE)
                .withImageUpload(IMAGE_UPLOAD)
                .withImportGuid(expectedImportGuid.toString())
                .withPackageFormatType(PACKAGE_FORMAT_TYPE)
                .build();

        DefaultResponse actual = sut.createObject(request);

        Assertions.assertEquals("0", actual.getRegState());

    }


}
