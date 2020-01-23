package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

/**
 * 
 * @author shaunmillargov
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class FigValidationServiceAppTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired 
	private FigValidationServiceApp figValidationServiceApp; 

	/**
	 * contextLoaded - Test for Figaro validation service context.  
	 */
	@Test
	void contextLoaded() {
		assertThat(figValidationServiceApp).isNotNull();
	}
	
	
	/**
	 * locateMatchingApplicantsHttpResponseTest - Basic HTTP test of the locateMatchingApplicants validation operation endpoint.
	 * 
	 * This test is NOT expected to fetch and test the response against any real data, rather we just want an HTTP response from the 
	 * operation indicating it's working. 
	 * 
	 * TODO - This will require recoding once ORDS has been connected.
	 * 
	 * @throws Exception
	 */
	@Test
	void locateMatchingApplicantsHttpResponseTest() throws Exception {
		String request = "/locateMatchingApplicants?applSurname=abcdef99901";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/figvalidationservice" + request,
		        String.class)
		).contains("<respCode>1</respCode>");	
	}
	

	/**
	 * ValidateApplicantServiceHttpResponseTest - Basic HTTP test of the ValidateApplicantService validation operation endpoint.
	 * 
	 * This test is NOT expected to fetch and test the response against any real data, rather we just want an HTTP response from the 
	 * operation indicating it's working. 
	 * 
	 * TODO - This will require recoding once ORDS has been connected. 
	 * 
	 * @throws Exception
	 */
	@Test
	void validateApplicantServiceHttpResponseTest() throws Exception {
		String request = "/validateApplicantService?orgPartyId=abcdef99901&applPartyId=def9908";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/figvalidationservice" + request,
		        String.class)
		).contains("<respCode>0</respCode>");	
	}
	
	/**
	 * validateApplicantForSharingHttpResponseTest - Basic HTTP test of the validateApplicantForSharing validation operation endpoint.
	 * 
	 * This test is NOT expected to fetch and test the response against any real data, rather we just want an HTTP response from the 
	 * operation indicating it's working. 
	 * 
	 * TODO - This will require recoding once ORDS has been connected. 
	 * 
	 * @throws Exception
	 */
	@Test
	void validateApplicantForSharingHttpResponseTest() throws Exception {
		String request = "/validateApplicantForSharing?applPartyId=9988&jurisdictionType=WWCH";
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/figvalidationservice" + request,
		        String.class)
		).contains("<respCode>0</respCode>");	
	}
	
//	/**
//	 * validateApplicantPartyIdHttpBadResponseTest - Basic HTTP test of the validateApplicantPartyId validation operation endpoint.
//	 * 
//	 * This test is NOT expected to fetch and test the response against any real data, rather we just want an HTTP response from the 
//	 * operation indicating it's working. .  
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	void validateApplicantPartyIdHttpBadResponseTest() throws Exception {
//		String request = "/validateApplicantPartyId?applPartyId=11111";
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/figvalidationservice" + request,
//		        String.class)
//		).contains("<respCode>-21</respCode>");	
//	}
	
//	/**
//	 * validateApplicantPartyIdHttpGoodResponseTest - Basic HTTP test of the validateApplicantPartyId validation operation endpoint.
//	 * 
//	 * This test is NOT expected to fetch and test the response against any real data, rather we just want an HTTP response from the 
//	 * operation indicating it's working. 
//	 * 
//	 * TODO - This code is NOT suitable for running in anything but dev. Needs recoding for environment independence.  
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	void validateApplicantPartyIdHttpGoodResponseTest() throws Exception {
//		String request = "/validateApplicantPartyId?applPartyId=32432";
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/figvalidationservice" + request,
//		        String.class)
//		).contains("<respCode>0</respCode>");	
//	}
}

