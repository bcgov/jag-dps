package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.controller.CalculateSinglePaymentController;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentResponse;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URL;

@DisplayName("CalculateSinglePaymentController test suite")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculateSinglePaymentControllerTest {


    private static final String CASE_1 = "CASE_1";
    private static final String CASE_2 = "CASE_2";
    public static final String HTTP_WATHEVER_COM = "http://wathever.com";

    @Mock
    private PaymentClient paymentClientMock;

    private CalculateSinglePaymentController sut;

    @BeforeAll
    public void setUp() throws MalformedURLException {


        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(new URL(HTTP_WATHEVER_COM)).when(paymentClientMock).calculateSinglePaymentURL(ArgumentMatchers.argThat(x -> x.getRef1().equals(CASE_1)), Mockito.any(Integer.class));
        Mockito.doThrow(new MalformedURLException("TEST MalformedURLException")).when(paymentClientMock).calculateSinglePaymentURL(ArgumentMatchers.argThat(x -> x.getRef1().equals(CASE_2)), Mockito.any(Integer.class));

        sut = new CalculateSinglePaymentController(paymentClientMock);

    }



    @Test
    @DisplayName("Success - valid url generation")
    public void withValidParamsShouldReturnUrl() {

        SinglePaymentResponse result = sut.singlepaymenturl("P", "1425", "http://approved.com", "http://declined.com", "http://error.com", "14.5", CASE_1, "ref2", "ref3", "5");
        Assertions.assertEquals(result.getRespCode(), PaymentServiceConstants.PAYMENT_SERVICE_SUCCESS_CD);
        Assertions.assertEquals(result.getRespMsg(), PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK);
        Assertions.assertEquals(result.getRespValue(), HTTP_WATHEVER_COM);

    }

    @Test
    @DisplayName("Success - valid url generation")
    public void withNumberFormatExceptionAmountShouldValidParamsShouldReturnError() {

        SinglePaymentResponse result = sut.singlepaymenturl("P", "1425", "http://approved.com", "http://declined.com", "http://error.com", "NOT AN AMOUNT", CASE_1, "ref2", "ref3", "5");

        Assertions.assertEquals(result.getRespCode(), PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
        Assertions.assertEquals(result.getRespMsg(), PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL);
        Assertions.assertEquals(result.getRespValue(), "For input string: \"NOT AN AMOUNT\"");

    }

    @Test
    @DisplayName("Success - valid url generation")
    public void withNumberFormatExceptionExpiryTimetShouldValidParamsShouldReturnError() {

        SinglePaymentResponse result = sut.singlepaymenturl("P", "1425", "http://approved.com", "http://declined.com", "http://error.com", "12.12", CASE_1, "ref2", "ref3", "NOT A TIME");

        Assertions.assertEquals(result.getRespCode(), PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
        Assertions.assertEquals(result.getRespMsg(), PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL);
        Assertions.assertEquals(result.getRespValue(), "For input string: \"NOT A TIME\"");

    }

    @Test
    @DisplayName("Success - valid url generation")
    public void withUrlMalformedExceptionShouldValidParamsShouldReturnError() {

        SinglePaymentResponse result = sut.singlepaymenturl("P", "1425", "http://approved.com", "http://declined.com", "http://error.com", "12.12", CASE_2, "ref2", "ref3", "5");

        Assertions.assertEquals(result.getRespCode(), PaymentServiceConstants.PAYMENT_SERVICE_FAILURE_CD);
        Assertions.assertEquals(result.getRespMsg(), PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_FAIL);
        Assertions.assertEquals(result.getRespValue(), "TEST MalformedURLException");

    }


}
