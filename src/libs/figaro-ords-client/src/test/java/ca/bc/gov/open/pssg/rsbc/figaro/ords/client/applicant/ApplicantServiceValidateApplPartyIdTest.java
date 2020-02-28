package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateApplicantPartyIdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author shaunmillargov
 * <p>
 * validateApplicantPartyId service test class.
 * <p>
 * Mocks underlying ORDS service class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantServiceValidateApplPartyIdTest {

    private static final String API_EXCEPTION = "api exception";
    private static final String BIRTH_DATE = "birthDate";
    private static final String BIRTH_PLACE = "birthPlace";
    private static final String DRIVER_LICENSE = "DriverLicense";
    private static final String FIRST_NAME = "FirstName";
    private static final String GENDER = "Gender";
    private static final String SECOND_NAME = "SecondName";
    private static final String SURNAME = "Surname";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "StatusMessage";
    private static final String ERROR_STATUS_CODE = "-22";
    private static final String ERROR_STATUS_MESSAGE = "error";
    private static final String GOOD_PARTY_ID = "11111";
    private static final String BAD_PARTY_ID = "20";
    private static final String EXCEPTION_PARTY_ID = "00000";

    @Mock
    private ApplicantApi applicantApiMock;

    private ApplicantServiceImpl sut;

    @BeforeAll
    public void SetUp() throws ApiException {

        ValidateApplicantPartyIdOrdsResponse successResponse = new ValidateApplicantPartyIdOrdsResponse();
        successResponse.setBirthDate(BIRTH_DATE);
        successResponse.setBirthPlace(BIRTH_PLACE);
        successResponse.setDriversLicense(DRIVER_LICENSE);
        successResponse.setFirstName(FIRST_NAME);
        successResponse.setGender(GENDER);
        successResponse.setSecondName(SECOND_NAME);
        successResponse.setSurname(SURNAME);
        successResponse.setStatusCode(STATUS_CODE);
        successResponse.setStatusMessage(STATUS_MESSAGE);

        ValidateApplicantPartyIdOrdsResponse badServiceResp = new ValidateApplicantPartyIdOrdsResponse();
        badServiceResp.setStatusCode(ERROR_STATUS_CODE);
        badServiceResp.setStatusMessage(ERROR_STATUS_MESSAGE);

        MockitoAnnotations.initMocks(this);

        Mockito.when(applicantApiMock.validateApplicantPartyId(GOOD_PARTY_ID))
                .thenReturn(successResponse);

        Mockito.when(applicantApiMock.validateApplicantPartyId(BAD_PARTY_ID))
                .thenReturn(badServiceResp);

        // emulate internal exception
        Mockito.when(applicantApiMock.validateApplicantPartyId(EXCEPTION_PARTY_ID)).thenThrow(new ApiException(
                API_EXCEPTION));

        sut = new ApplicantServiceImpl(applicantApiMock);
    }

    /**
     * success
     */
    @Test
    public void withValidResponseShouldReturnValidResponse() throws ApiException {

        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(GOOD_PARTY_ID);

        Assertions.assertEquals(BIRTH_DATE, response.getFoundBirthDate());
        Assertions.assertEquals(BIRTH_PLACE, response.getFoundBirthPlace());
        Assertions.assertEquals(DRIVER_LICENSE, response.getFoundDriversLicence());
        Assertions.assertEquals(FIRST_NAME, response.getFoundFirstName());
        Assertions.assertEquals(GENDER, response.getFoundGenderTxt());
        Assertions.assertEquals(SECOND_NAME, response.getFoundSecondName());
        Assertions.assertEquals(SURNAME, response.getFoundSurname());
        Assertions.assertEquals(Integer.parseInt(STATUS_CODE), response.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getRespMsg());
    }

    /**
     * failure to find party id
     */
    @Test
    public void withInvalidResponseShouldReturnInvalidResponse() throws ApiException {
        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(BAD_PARTY_ID);
        Assertions.assertEquals(Integer.parseInt(ERROR_STATUS_CODE), response.getRespCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getRespMsg());
    }

    /**
     * exception test
     */
    @Test
    public void ValidateFigaroControllerException() throws ApiException {
        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(EXCEPTION_PARTY_ID);
        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(API_EXCEPTION, response.getRespMsg());
    }

}

