package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.configuration.BamboraProperties;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BamboraClientImplTest {

    private BamboraClientImpl sut;

    @Test
    public void withValidParamShouldReturnAUrl() throws PaymentServiceException, MalformedURLException {

        BamboraProperties bamboraProperties = new BamboraProperties();
        bamboraProperties.setHashkey("12");
        bamboraProperties.setHostedPaymentEndpoint("https://www.beanstream.com/scripts/payment/payment.asp");
        bamboraProperties.setMerchantId("338830000");
        bamboraProperties.setTimezone("PST");

        sut = new BamboraClientImpl(bamboraProperties);


        URL result = sut.calculateSinglePaymentURL(
                new SinglePaymentRequest.Builder()
                        .withBamboraTransType(PaymentServiceConstants.BamboraTransType.P)
                        .withInvoiceNumber("5548")
                        .withTotalItemsAmount(50.50)
                        .withApprovedPage("http://test.com/approved")
                        .withDeclinedPage("http://test.com/declined")
                        .withErrorPage("http://test.com/error")
                        .withRef1("ref1")
                        .withRef2("ref2")
                        .withRef3("ref3").build(), 5);

        String expectedShort = "https://www.beanstream.com/scripts/payment/payment.asp?" +
                "merchant_id=338830000" +
                "&trnType=P" +
                "&trnOrderNumber=5548" +
                "&errorPage=http://test.com/error" +
                "&approvedPage=http://test.com/approved" +
                "&declinedPage=http://test.com/declined" +
                "&trnAmount=50.50" +
                "&ref1=ref1" +
                "&ref2=ref2" +
                "&ref3=ref3";

        Assertions.assertTrue(StringUtils.startsWith(result.toExternalForm(), expectedShort));


    }

    /**
     * calculateSinglePaymentAlgoTest - Basic testing of the Bambora Payment client, calculateSinglePaymentURL algorithm.
     *
     * Note: this test uses the Test property source (See test\java\resources\test.properties)
     *
     * @throws Exception
     */
    @Test
    void calculateSinglePaymentAlgoTest() throws Exception {


        BamboraProperties bamboraProperties = new BamboraProperties();

        bamboraProperties.setHashkey("hash");
        bamboraProperties.setHostedPaymentEndpoint("https://www.beanstream.com/scripts/payment/payment.asp");
        bamboraProperties.setMerchantId("123456");
        bamboraProperties.setTimezone("PST");

        PaymentClient paymentClient = new BamboraClientImpl(bamboraProperties);

        URL response = paymentClient.calculateSinglePaymentURL(
                new SinglePaymentRequest.Builder()
                        .withBamboraTransType(PaymentServiceConstants.BamboraTransType.P)
                        .withInvoiceNumber("01234")
                        .withTotalItemsAmount(10.56)
                        .withApprovedPage("http://somedomain/someapp/approved.do")
                        .withDeclinedPage("http://somedomain/someapp/declined.do")
                        .withErrorPage("http://somedomain/someapp/error.do")
                        .withRef1("ref1")
                        .withRef2("ref2").build(), 1);

        String expectedShort = "https://www.beanstream.com/scripts/payment/payment.asp?" +
                "merchant_id=338830000" +
                "&trnType=P" +
                "&trnOrderNumber=01234" +
                "&errorPage=http://test.com/error" +
                "&approvedPage=http://test.com/approved" +
                "&declinedPage=http://test.com/declined" +
                "&trnAmount=10.56" +
                "&ref1=ref1" +
                "&ref2=ref2";

    }

}
