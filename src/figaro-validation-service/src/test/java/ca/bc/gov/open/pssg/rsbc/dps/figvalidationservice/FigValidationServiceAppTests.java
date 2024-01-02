package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * @author shaunmillargov
 *
 */
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations="classpath:test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FigValidationServiceAppTests {
	
	@LocalServerPort
	private int port;
	
	@Mock
	private TestRestTemplate restTemplate;
	
	@Mock
	private FigValidationServiceApp figValidationServiceApp;

	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * contextLoaded - Test for Figaro validation service context.  
	 */
	@Test
	void contextLoaded() {
		assertThat(figValidationServiceApp).isNotNull();
	}
}

