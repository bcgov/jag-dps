package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
class PaymentserviceApplicationTests {
	
	@Value("${bambora.hostedpaymentendpoint}")
	private String hostedPaymentEndpoint;

	@Value("${bambora.merchantid}")
	private String merchantId;

	@Value("${bambora.hashkey}")
	private String hashKey;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private CalculateSinglePayment calculateSinglePayment;
	
	@Autowired
	private BamboraConfiguration bamboraConfiguration;
	
	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * contextLoaded - Test for bean context first. If no beans, other tests will fail. 
	 */
	@Test
	void contextLoaded() {
		assertThat(calculateSinglePayment).isNotNull();
		assertThat(bamboraConfiguration).isNotNull();
	}
	
	
	/**
	 * calculateSinglePaymentHttpResponseTest - Basic testing of the CalculateSinglePayment operation (good response expected). 
	 * 
	 * @throws Exception
	 */
	@Test
	void calculateSinglePaymentHttpResponseTest() throws Exception {
		String request = "/getSinglePaymentURL?transType=P&invoiceNumber=9999&totalItemsAmount=10.05&approvedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&declinedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&errorPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&minutesToExpire=1440&ref1=0123456&ref2=ref2String&ref3=ref3String";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/paymentservice" + request,
		        String.class)
		).contains(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK);	
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
		
		PaymentClient paymentClient = new BamboraClientImpl(new URL(hostedPaymentEndpoint), merchantId, hashKey, 1);
		
		URL response = paymentClient.calculateSinglePaymentURL("abcdef123",
							PaymentServiceConstants.BamboraTransType.P, 
							"01234", 
							10.56,
							0L,
							0L,
							"http://somedomain/someapp/approved.do", 
							"http://somedomain/someapp/declined.do", 
							"http://somedomain/someapp/error.do",  
							"ref1",
							"ref2", 
							"ref3");
		
		assertThat(response.toExternalForm()).contains("hashValue=3837E45A548CC2366730BFB69C77F5DA");	
	}
	
	/**
	 * bamboraConfigurationTest - Basic request testing of the CalculateSinglePayment operation. 
	 * 
	 * @throws Exception
	 */
	@Test
	void bamboraConfigurationTest() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/paymentservice" + "/bamboraconfiguration",
				String.class)
		).contains(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK);
	}
}


