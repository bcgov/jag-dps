package ca.bc.gov.open.pssg.rsbc.dps.paymentservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PaymentserviceApplicationTests {
	
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
		System.out.println("Running application context tests....");
		assertThat(calculateSinglePayment).isNotNull();
		assertThat(bamboraConfiguration).isNotNull();
	}
	
	/**
	 * calculateSinglePaymentTestGood - Basic testing of the CalculateSinglePayment operation (good response expected). 
	 * 
	 * @throws Exception
	 */
	@Test
	void calculateSinglePaymentTestGood() throws Exception {
		System.out.println("Running basic http test for calculate single payment operation...");
		String request = "/getSinglePaymentURL?transType=P&invoiceNumber=9999&totalItemsAmount=10.05&approvedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&declinedPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&errorPage=http://localhost:8080/crc/beanstream/dpsProcessPayment.do&minutesToExpire=1440&ref1=0123456&ref2=ref2String&ref3=ref3String";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/paymentservice" + request,
		        String.class)
		).contains(PaymentServiceConstants.PAYMENT_SERVICE_RESP_MSG_OK);	
	}
	
	/**
	 * bamboraConfigurationTest - Basic request testing of the CalculateSinglePayment operation. 
	 * 
	 * @throws Exception
	 */
	@Test
	void bamboraConfigurationTest() throws Exception {
		System.out.println("Running basic http test for the bambora configuration operation...");
		//TODO - to be completed once Bambora configuration operation is complete. 
	}
}


