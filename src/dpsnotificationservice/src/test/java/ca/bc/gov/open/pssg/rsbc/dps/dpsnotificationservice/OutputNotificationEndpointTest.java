package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice.generated.models.*;
import ca.bc.gov.open.pssg.rsbc.dps.files.notification.OutputNotificationMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OutputNotificationEndpointTest {


    public static final String AMQP_EXCEPTION = "amqp exception";
    public static final String BUSINESS_AREA_CD = "BusinessAreaCd";
    public static final String EXCEPTION_CASE = "VIPS";
    public static final String TEST_FILE = "test.file";
    private OutputNotificationEndpoint sut;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.doNothing().when(rabbitTemplateMock).convertAndSend(Mockito.eq(BUSINESS_AREA_CD), Mockito.any(OutputNotificationMessage.class));
        Mockito.doThrow(new AmqpException(AMQP_EXCEPTION)).when(rabbitTemplateMock).convertAndSend(Mockito.eq(
                EXCEPTION_CASE), Mockito.any(OutputNotificationMessage.class));

        sut = new OutputNotificationEndpoint(rabbitTemplateMock);
    }

    @Test
    public void withNoOutputNotificationRequestShouldReturnError() {

        OutputNotificationResponse response = sut.outputNotificationNotification(new OutputNotificationRequest());

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest is required", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withNullBusinessAreaCdShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest.getBusinessAreaCd is required", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withEmptyBusinessAreaCdShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest.getBusinessAreaCd is required", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withNullFileListCdShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request2.setBusinessAreaCd(BusinessAreaCd.CRRP);
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest.FileList.FileId must contains a least 1 file", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withNullFileListListCdShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request2.setBusinessAreaCd(BusinessAreaCd.CRRP);
        FileList fileList = new FileList();
        request2.setFileList(fileList);
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest.FileList.FileId must contains a least 1 file", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withNoFileInListCdShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request2.setBusinessAreaCd(BusinessAreaCd.CRRP);
        FileList fileList = new FileList();
        request2.setFileList(fileList);
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals("OutputNotificationRequest.FileList.FileId must contains a least 1 file", response.getOutputNotificationResponse().getRespMsg());

    }

    @Test
    public void withFileInListCdShouldReturnSuccess() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request2.setBusinessAreaCd(BusinessAreaCd.CRRP);
        FileList fileList = new FileList();
        fileList.getFileId().add("test.file");
        request2.setFileList(fileList);
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE, response.getOutputNotificationResponse().getRespMsg());

    }


    @Test
    public void withExceptionShouldReturnError() {

        OutputNotificationRequest request = new OutputNotificationRequest();
        OutputNotificationRequest2 request2 = new OutputNotificationRequest2();
        request2.setBusinessAreaCd(BusinessAreaCd.VIPS);
        FileList fileList = new FileList();
        fileList.getFileId().add(TEST_FILE);
        request2.setFileList(fileList);
        request.setOutputNotificationRequest(request2);

        OutputNotificationResponse response = sut.outputNotificationNotification(request);

        Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getOutputNotificationResponse().getRespCode());
        Assertions.assertEquals(AMQP_EXCEPTION, response.getOutputNotificationResponse().getRespMsg());


    }

}
