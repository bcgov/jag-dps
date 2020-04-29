package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import bcgov.reeks.dps_extensions_common_wsprovider.outputnotificationws.*;
import ca.bc.gov.open.pssg.rsbc.dps.notification.OutputNotificationMessage;
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

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals("OutputNotificationRequest is required", response.getRespMsg());

        }

        @Test
        public void withNullBusinessAreaCdShouldReturnError() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals("OutputNotificationRequest.getBusinessAreaCd is required", response.getRespMsg());

        }

        @Test
        public void withEmptyBusinessAreaCdShouldReturnError() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals("OutputNotificationRequest.getBusinessAreaCd is required", response.getRespMsg());

        }

        @Test
        public void withNullFileListCdShouldReturnError() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request2.setBusinessAreaCd(BUSINESS_AREA_CD);
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals("OutputNotificationRequest.FileList.FileId must contains a least 1 file", response.getRespMsg());

        }

        @Test
        public void withNoFileInListCdShouldReturnError() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request2.setBusinessAreaCd(BUSINESS_AREA_CD);
            FileList fileList = new FileList();
            request2.setFileList(fileList);
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals("OutputNotificationRequest.FileList.FileId must contains a least 1 file", response.getRespMsg());

        }

        @Test
        public void withFileInListCdShouldReturnSuccess() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request2.setBusinessAreaCd(BUSINESS_AREA_CD);
            FileList fileList = new FileList();
            fileList.getFileId().add(TEST_FILE);
            request2.setFileList(fileList);
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_CODE, response.getRespCode());
            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_SUCCESS_MESSAGE, response.getRespMsg());

        }


        @Test
        public void withExceptionShouldReturnError() {

            ObjectFactory factory = new ObjectFactory();

            OutputNotificationRequest request = factory.createOutputNotificationRequest();
            OutputNotificationRequest2 request2 = factory.createOutputNotificationRequest2();
            request2.setBusinessAreaCd(EXCEPTION_CASE);
            FileList fileList = new FileList();
            fileList.getFileId().add(TEST_FILE);
            request2.setFileList(fileList);
            request.setOutputNotificationRequest(request2);

            OutputNotificationResponse2 response = sut.outputNotification(request);

            Assertions.assertEquals(Keys.OUTPUT_NOTIFICATION_RESPONSE_ERROR_CODE, response.getRespCode());
            Assertions.assertEquals(AMQP_EXCEPTION, response.getRespMsg());

        }

}
