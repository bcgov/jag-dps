package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.exception.PaymentServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.types.SinglePaymentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BamboraClientImplTest {

    private BamboraClientImpl sut;

    @Test
    public void withValidParamShouldReturnAUrl() throws PaymentServiceException, MalformedURLException {


        sut = new BamboraClientImpl(new URL("https://test.com/payment.asp"), "1", "12", 5);


        URL result = sut.calculateSinglePaymentURL(
                new SinglePaymentRequest("1", PaymentServiceConstants.BamboraTransType.P, "1", 5.00, 12.1, 1.21, "http://test.com/approved", "http://test.com/declined", "http://test.com/error", "ref1", "ref2", "ref3"));


        Assertions.assertTrue(result.toExternalForm().matches(
                "https://test\\.com/payment\\.asp\\?merchant_id=1&trnType=P&trnOrderNumber=1&errorPage=http://test\\.com/error&declinedPage=http://test\\.com/declined&approvedPage=http://test\\.com/approved&ref1=ref1&ref2=ref2&ref3=ref3&trnAmount=18\\.31&hashValue=B75D0FF8F2CEAC5CD86BFC3238646169&hashExpiry=\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}"),
                "Url Generated does not matches expected.");

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

        PaymentClient paymentClient = new BamboraClientImpl(new URL("https://web.na.bambora.com/scripts/payment/payment.asp"), "123456", "hash", 1);

        URL response = paymentClient.calculateSinglePaymentURL(
                new SinglePaymentRequest("abcdef123",
                        PaymentServiceConstants.BamboraTransType.P,
                        "01234",
                        10.56,
                        null,
                        null,
                        "http://somedomain/someapp/approved.do",
                        "http://somedomain/someapp/declined.do",
                        "http://somedomain/someapp/error.do",
                        "ref1",
                        "ref2",
                        "ref3"));

        Assertions.assertTrue(response.toExternalForm().matches(
                "https://web\\.na\\.bambora\\.com/scripts/payment/payment\\.asp\\?merchant_id=123456&trnType=P&trnOrderNumber=01234&errorPage=http://somedomain/someapp/error\\.do&declinedPage=http://somedomain/someapp/declined\\.do&approvedPage=http://somedomain/someapp/approved\\.do&ref1=ref1&ref2=ref2&ref3=ref3&trnAmount=10\\.56&hashValue=B5E89C19D86B11C56BC65F6E3758EED1&hashExpiry=\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}"),
                "Url Generated does not matches expected.")
        ;
    }

}
