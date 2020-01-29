package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.controller.ValidateApplicantServiceController;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.ValidateApplicantServiceResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidateApplicantServiceControllerTest {

	// test class for ValidateApplicantService methods.

	@Mock
	private FigaroValidationImpl figservice;

	private ValidateApplicantServiceController validateApplicantServiceController;
	private ValidateApplicantServiceResponse validateApplicantServiceResponse;

	/*
	 * Valid input arguments
	 */

	private final String VALID_ORG_PARTY_ID = "442";
	private final String VALID_APPL_PARTY_ID = "32432";

	/*
	 * In Valid input arguments
	 */

	private final String INVALID_ORG_PARTY_ID = "4423";
	private final String INVALID_APPL_PARTY_ID = "3243";

	/**
	 * Exception Input argument
	 *
	 */
	private final String EXCEPTION_ORG_PARTY_ID = "00000";
	private final String EXCEPTION_APPL_PARTY_ID = "00000";

	/*
	 * Valid controller response
	 */

	private final String VALID_RESPONSE_MESSAGE = "Validation passed.";
	private final int VALID_RESPONSE_CODE = 0;
	private final String VALID_VALIDATION_RESULT = "P";

	/*
	 * In Valid org_party_id response
	 */

	private final String INVALID_ORG_PARTY_ID_RESPONSE_MESSAGE = "Validation Failure: Organization Party ID 4423 was not found.";
	private final int INVALID_ORG_PARTY_ID_RESPONSE_CODE = -11;
	private final String INVALID_VALIDATION_RESULT = "F";

	/*
	 * In Valid appl_party_id response
	 */

	private final String INVALID_APPL_PARTY_ID_RESPONSE_MESSAGE = "Validation Failure: Applicant Party ID 3243 was not found.";
	private final int INVALID_APPL_PARTY_ID_RESPONSE_CODE = -21;

	/*
	 * Exception controller response
	 */
	private final int EXCEPTION_CONTROLLER_RESPCD = -1;

	/*
	 * service response
	 * 
	 */

	private final String VALID_SERVICE_STATUS_CODE = "0";
	private final String VALID_SERVICE_STATUS_MESSAGE = "Validation passed.";
	private final String VALID_SERVICE_VALIDATION_RESULT = "P";

	private final String INVALID_ORG_ID_SERVICE_STATUS_CODE = "-11";
	private final String INVALID_ORG_ID_SERVICE_STATUS_MESSAGE = "Validation Failure: Organization Party ID 4423 was not found.";

	private final String INVALID_APPL_ID_SERVICE_STATUS_CODE = "-21";
	private final String INVALID_APPL_ID_SERVICE_STATUS_MESSAGE = "Validation Failure: Applicant Party ID 3243 was not found.";

	private final String INVALID_SERVICE_VALIDATION_RESULT = "F";

	@BeforeAll
	public void SetUp() {

		ValidateApplicantServiceOrdsResponse validOrdsServiceResp = new ValidateApplicantServiceOrdsResponse();
		validOrdsServiceResp.setStatusCode(VALID_SERVICE_STATUS_CODE);
		validOrdsServiceResp.setStatusMessage(VALID_SERVICE_STATUS_MESSAGE);
		validOrdsServiceResp.setValidationResult(VALID_SERVICE_VALIDATION_RESULT);

		ValidateApplicantServiceOrdsResponse inValidOrgIdOrdsServiceResp = new ValidateApplicantServiceOrdsResponse();
		inValidOrgIdOrdsServiceResp.setStatusCode(INVALID_ORG_ID_SERVICE_STATUS_CODE);
		inValidOrgIdOrdsServiceResp.setStatusMessage(INVALID_ORG_ID_SERVICE_STATUS_MESSAGE);
		inValidOrgIdOrdsServiceResp.setValidationResult(INVALID_SERVICE_VALIDATION_RESULT);

		ValidateApplicantServiceOrdsResponse inValidApplIdOrdsServiceResp = new ValidateApplicantServiceOrdsResponse();
		inValidApplIdOrdsServiceResp.setStatusCode(INVALID_APPL_ID_SERVICE_STATUS_CODE);
		inValidApplIdOrdsServiceResp.setStatusMessage(INVALID_APPL_ID_SERVICE_STATUS_MESSAGE);
		inValidApplIdOrdsServiceResp.setValidationResult(INVALID_SERVICE_VALIDATION_RESULT);

		MockitoAnnotations.initMocks(this);

		try {

			Mockito.when(figservice.validateApplicantServiceOrdsResponse(VALID_APPL_PARTY_ID, VALID_ORG_PARTY_ID))
					.thenReturn(validOrdsServiceResp);

			Mockito.when(figservice.validateApplicantServiceOrdsResponse(VALID_APPL_PARTY_ID, INVALID_ORG_PARTY_ID))
					.thenReturn(inValidOrgIdOrdsServiceResp);

			Mockito.when(figservice.validateApplicantServiceOrdsResponse(INVALID_APPL_PARTY_ID, VALID_ORG_PARTY_ID))
					.thenReturn(inValidApplIdOrdsServiceResp);

			Mockito.when(
					figservice.validateApplicantServiceOrdsResponse(EXCEPTION_ORG_PARTY_ID, EXCEPTION_APPL_PARTY_ID))
					.thenThrow(FigaroValidationServiceException.class);

		} catch (FigaroValidationServiceException ex) {
			ex.printStackTrace();
		}

		validateApplicantServiceController = new ValidateApplicantServiceController(figservice);

	}

	/**
	 * success
	 */
	@Test
	public void ValidateApplicantServiceControllerSuccess() throws FigaroValidationServiceException {

		validateApplicantServiceResponse = validateApplicantServiceController
				.validateApplicantService(VALID_ORG_PARTY_ID, VALID_APPL_PARTY_ID);
		Assert.assertEquals(VALID_VALIDATION_RESULT, validateApplicantServiceResponse.getValidationResult());
		Assert.assertEquals(VALID_RESPONSE_MESSAGE, validateApplicantServiceResponse.getRespMsg());
		Assert.assertEquals(VALID_RESPONSE_CODE, validateApplicantServiceResponse.getRespCode());

	}

	/**
	 * failure response when Invalid Org_Party_Id is passed as input argument
	 */
	@Test
	public void InvalidOrgPartyIdFail() throws FigaroValidationServiceException {

		validateApplicantServiceResponse = validateApplicantServiceController
				.validateApplicantService(INVALID_ORG_PARTY_ID, VALID_APPL_PARTY_ID);
		Assert.assertEquals(INVALID_VALIDATION_RESULT, validateApplicantServiceResponse.getValidationResult());
		Assert.assertEquals(INVALID_ORG_PARTY_ID_RESPONSE_MESSAGE, validateApplicantServiceResponse.getRespMsg());
		Assert.assertEquals(INVALID_ORG_PARTY_ID_RESPONSE_CODE, validateApplicantServiceResponse.getRespCode());

	}

	/**
	 * failure response when Invalid Appl_Party_Id input argument
	 */
	@Test
	public void InvalidApplPartyIdFail() throws FigaroValidationServiceException {

		validateApplicantServiceResponse = validateApplicantServiceController
				.validateApplicantService(VALID_ORG_PARTY_ID, INVALID_APPL_PARTY_ID);
		Assert.assertEquals(INVALID_VALIDATION_RESULT, validateApplicantServiceResponse.getValidationResult());
		Assert.assertEquals(INVALID_APPL_PARTY_ID_RESPONSE_MESSAGE, validateApplicantServiceResponse.getRespMsg());
		Assert.assertEquals(INVALID_APPL_PARTY_ID_RESPONSE_CODE, validateApplicantServiceResponse.getRespCode());

	}

	/**
	 * exception test
	 */
	@Test
	public void ValidateApplicantServiceControllerException() throws FigaroValidationServiceException {

		validateApplicantServiceResponse = validateApplicantServiceController
				.validateApplicantService(EXCEPTION_ORG_PARTY_ID, EXCEPTION_APPL_PARTY_ID);
		Assert.assertEquals(EXCEPTION_CONTROLLER_RESPCD, validateApplicantServiceResponse.getRespCode());
		Assert.assertEquals(null, validateApplicantServiceResponse.getRespMsg());
		Assert.assertEquals(INVALID_VALIDATION_RESULT, validateApplicantServiceResponse.getValidationResult());

	}

}

