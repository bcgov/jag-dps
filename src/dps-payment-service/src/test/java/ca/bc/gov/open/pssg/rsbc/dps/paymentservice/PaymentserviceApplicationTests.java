package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.controller.CalculateSinglePaymentController;
import ca.bc.gov.open.pssg.rsbc.dps.paymentservice.controller.CrcController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations="classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentserviceApplicationTests {
	
	@LocalServerPort
	private int port;

	@Mock
	private CalculateSinglePaymentController calculateSinglePayment;

	@Mock
	private CrcController paymentServiceController;
	
	@Mock
	private TestRestTemplate restTemplate;


	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * contextLoaded - Test for bean context first. If no beans, other tests will fail. 
	 */
	@Test
	void contextLoaded() {
		assertThat(calculateSinglePayment).isNotNull();
		assertThat(paymentServiceController).isNotNull();
	}
	
	
	/**
	 * calculateSinglePaymentHttpResponseTest - Basic testing of the CalculateSinglePayment operation (good response expected). 
	 * 
	 * @throws Exception
	 */
	@Test
	void calculateSinglePaymentHttpResponseTest() throws Exception {

		String request = "/getSinglePaymentURL?transType=P&invoiceNumber=9999&totalItemsAmount=10.05&approvedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&declinedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&errorPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&minutesToExpire=1440&ref1=0123456&ref2=ref2String&ref3=ref3String";

		String result = this.restTemplate.getForObject("http://localhost:" + port + "/paymentservice" + request,
				String.class);

	///	Assertions.assertTrue(result.matches("<singlePaymentResponse><respMsg>success</respMsg><respCode>0</respCode><respValue>https://web\\.na\\.bambora\\.com/scripts/payment/payment\\.asp\\?merchant_id=123456&amp;trnType=P&amp;trnOrderNumber=9999&amp;errorPage=http://localhost:8080/crc/beanstream/dpsProcessPayment\\.do&amp;declinedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment\\.do&amp;approvedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment\\.do&amp;ref1=0123456&amp;ref2=ref2String&amp;ref3=ref3String&amp;trnAmount=10\\.05&amp;hashValue=C621FB2F7CB420FD6123D770EEB2B867&amp;hashExpiry=\\d{12}</respValue></singlePaymentResponse>"));

	}

	@Test
	public void withValidParamAppShouldReturnConfiguration() {
		String request = "/getBeanstreamEndpoints";

		String result = this.restTemplate.getForObject("http://localhost:" + port + "/paymentservice" + request,
				String.class);

		// Assertions.assertEquals("<beanstreamEndpointResponse><approved>http://myendpoint/approved</approved><declined>http://myendpoint/declined</declined><error>http://myendpoint/error</error><respMsg>success</respMsg><respCode>0</respCode></beanstreamEndpointResponse>", result);

	}

}


