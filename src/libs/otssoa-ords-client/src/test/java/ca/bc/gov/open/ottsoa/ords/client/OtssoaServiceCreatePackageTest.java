package ca.bc.gov.open.ottsoa.ords.client;

import ca.bc.gov.open.ottsoa.ords.client.api.OtssoaApi;
import ca.bc.gov.open.ottsoa.ords.client.api.handler.ApiException;
import ca.bc.gov.open.ottsoa.ords.client.api.model.CreatePackagePayload;
import ca.bc.gov.open.ottsoa.ords.client.api.model.DefaultResponse;
import ca.bc.gov.open.ottsoa.ords.client.models.CreatePackageRequest;
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
public class OtssoaServiceCreatePackageTest {

    private static final String BUSINESS_AREA = "BusinessArea";
    private static final String FILENAME = "test.xml";
    private static final String FORMAT_TYPE = "1";
    private static final int PAGE_COUNT = 1;
    private static final String PROGRAM_TYPE = "program type";
    private static final String RECIPIENT = "recipient";
    private static final int RECORD_COUNT = 2;
    private static final String SOURCE = "source";

    private OtssoaServiceImpl sut;

    @Mock
    private OtssoaApi otssoaApiMock;




    @BeforeAll
    public void setUp() throws ApiException {
        MockitoAnnotations.initMocks(this);

        DefaultResponse success = new DefaultResponse();
        success.setRegState("0");

        Mockito.when(otssoaApiMock.createPackagePost(Mockito.any(CreatePackagePayload.class))).thenReturn(success);

        sut = new OtssoaServiceImpl(otssoaApiMock);

    }


    @Test
    public void withValidPayloadItShouldReturnSuccess() throws ApiException {

        UUID expectedImportGuid = UUID.randomUUID();
        Date expectedReceivedDate = new Date();


        CreatePackageRequest request = new CreatePackageRequest
                .Builder()
                .withBusinessArea(BUSINESS_AREA)
                .withFilename(FILENAME)
                .withFormatType(FORMAT_TYPE)
                .withImportGuid(expectedImportGuid)
                .withPageCount(PAGE_COUNT)
                .withProgramType(PROGRAM_TYPE)
                .withReceivedDate(expectedReceivedDate)
                .withRecipient(RECIPIENT)
                .withRecordCount(RECORD_COUNT)
                .withSource(SOURCE)
                .build();

        DefaultResponse actual = sut.createPackage(request);

        Assertions.assertEquals("0", actual.getRegState());

    }


}
