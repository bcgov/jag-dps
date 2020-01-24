package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantPartyIdResponse;

/**
 * 
 * @author shaunmillargov
 * 
 * ValidateApplicantPartyId Controller test class. 
 * 
 * Mocks underlying ORDS service class. 
 *   
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidateApplPartyIdControllerTest {
	
	@Mock
	private FigaroValidationImpl figservice; 
	
	private ValidateApplicantPartyIdController controller;
	
	private final String GOOD_PARTY_ID = "11111";
	private final String BAD_PARTY_ID = "20";
	private final String EXCEPTION_PARTY_ID = "00000";
	
	private final int GOOD_CONTROLLER_RESPCD = 0;
	private final int BAD_CONTROLLER_RESPCD = -22;
	private final int FAIL_CONTROLLER_RESPCD = -1;
	
	private final String GOOD_SERVICE_RESPCD = "0";
	private final String GOOD_SERVICE_RESPMSG = "Party ID successfully validated.";
	
	private final String BAD_SERVICE_RESPCD = "-22";
	private final String BAD_SERVICE_RESPMSG = "Validation Failure: Applicant Party ID 20 is not for an Individual";
	
	@BeforeAll
    public void SetUp()  {
		
		ValidateApplicantPartyIdOrdsResponse goodServiceResp = new ValidateApplicantPartyIdOrdsResponse();
		goodServiceResp.setStatusCode(GOOD_SERVICE_RESPCD);
		goodServiceResp.setStatusMessage(GOOD_SERVICE_RESPMSG);
		
		ValidateApplicantPartyIdOrdsResponse badServiceResp = new ValidateApplicantPartyIdOrdsResponse();
		badServiceResp.setStatusCode(BAD_SERVICE_RESPCD);
		badServiceResp.setStatusMessage(BAD_SERVICE_RESPMSG);

        MockitoAnnotations.initMocks(this);
        try {
        	
            Mockito.when(figservice.validateApplicantPartyId(GOOD_PARTY_ID))
                    .thenReturn(goodServiceResp);
            
            Mockito.when(figservice.validateApplicantPartyId(BAD_PARTY_ID))
            		.thenReturn(badServiceResp);

            // emulate internal exception
            Mockito.when(figservice.validateApplicantPartyId(EXCEPTION_PARTY_ID)).thenThrow(FigaroValidationServiceException.class);

        } catch (FigaroValidationServiceException ex) {
            ex.printStackTrace();
        }

        controller = new ValidateApplicantPartyIdController(figservice);

    }
	
    /** 
     * success
     */
	@Test
    public void ValidateFigaroControllerSuccess() {
		
		ValidateApplicantPartyIdResponse response;
		try {
			response = controller.validateApplicantPartyId(GOOD_PARTY_ID);
			Assert.assertEquals(GOOD_CONTROLLER_RESPCD, response.getRespCode());
		    Assert.assertEquals("Party ID successfully validated.", response.getRespMsg());
		    
		} catch (FigaroValidationServiceException e) {
			e.printStackTrace();
			assert(false);
		}
		
    }
	
	/**
	 * failure to find party id
	 */
	@Test
    public void ValidateFigaroControllerFail() {
		
		ValidateApplicantPartyIdResponse response;
		try {
			response = controller.validateApplicantPartyId(BAD_PARTY_ID);
			Assert.assertEquals(BAD_CONTROLLER_RESPCD, response.getRespCode());
		    Assert.assertEquals("Validation Failure: Applicant Party ID 20 is not for an Individual", response.getRespMsg());
		    
		} catch (FigaroValidationServiceException e) {
			e.printStackTrace();
			assert(false);
		}
		
    }
	
	/**
	 * exception test
	 */
	@Test
    public void ValidateFigaroControllerException() {
		
		ValidateApplicantPartyIdResponse response;
		try {
			response = controller.validateApplicantPartyId(EXCEPTION_PARTY_ID);
			Assert.assertEquals(FAIL_CONTROLLER_RESPCD, response.getRespCode());
		    Assert.assertEquals(null, response.getRespMsg());
		    
		} catch (FigaroValidationServiceException e) {
			e.printStackTrace();
			assert(false);
		}
		
    }

}

