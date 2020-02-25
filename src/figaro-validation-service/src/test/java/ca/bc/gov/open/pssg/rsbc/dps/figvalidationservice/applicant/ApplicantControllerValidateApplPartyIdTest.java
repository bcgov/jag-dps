package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.ApplicantService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateApplicantPartyIdResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author shaunmillargov
 * <p>
 * ValidateApplicantPartyId Controller test class.
 * <p>
 * Mocks underlying ORDS service class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantControllerValidateApplPartyIdTest {

    private static final String SURNAME = "surname";
    private static final String SECONDNAME = "secondname";
    private static final String GENDER = "gender";
    private static final String FIRSTNAME = "firstname";
    private static final String DRIVERLICENCE = "driverlicence";
    private static final String BIRTHPLACE = "birthplace";
    private static final String BIRTHDATE = "birthdate";
    private static final String GOOD_PARTY_ID = "11111";
    private static final String BAD_PARTY_ID = "20";
    private static final String EXCEPTION_PARTY_ID = "00000";
    private static final int GOOD_CONTROLLER_RESPCD = 0;
    private static final int BAD_CONTROLLER_RESPCD = -22;
    private static final int FAIL_CONTROLLER_RESPCD = -1;
    private static final String GOOD_SERVICE_RESPCD = "0";
    private static final String GOOD_SERVICE_RESPMSG = "Party ID successfully validated.";
    private static final String BAD_SERVICE_RESPCD = "-22";
    private static final String BAD_SERVICE_RESPMSG = "Validation Failure: Applicant Party ID 20 is not for an Individual";

    @Mock
    private ApplicantService applicantService;

    private ApplicantController sut;

    @BeforeAll
    public void SetUp() throws ApiException {

        ValidateApplicantPartyIdOrdsResponse goodServiceResp = new ValidateApplicantPartyIdOrdsResponse();
        goodServiceResp.setStatusCode(GOOD_SERVICE_RESPCD);
        goodServiceResp.setStatusMessage(GOOD_SERVICE_RESPMSG);
        goodServiceResp.setSurname(SURNAME);
        goodServiceResp.setSecondName(SECONDNAME);
        goodServiceResp.setGender(GENDER);
        goodServiceResp.setFirstName(FIRSTNAME);
        goodServiceResp.setDriversLicense(DRIVERLICENCE);
        goodServiceResp.setBirthPlace(BIRTHPLACE);
        goodServiceResp.setBirthDate(BIRTHDATE);

        ValidateApplicantPartyIdOrdsResponse badServiceResp = new ValidateApplicantPartyIdOrdsResponse();
        badServiceResp.setStatusCode(BAD_SERVICE_RESPCD);
        badServiceResp.setStatusMessage(BAD_SERVICE_RESPMSG);

        MockitoAnnotations.initMocks(this);

        Mockito.when(applicantService.validateApplicantPartyId(GOOD_PARTY_ID))
                .thenReturn(goodServiceResp);

        Mockito.when(applicantService.validateApplicantPartyId(BAD_PARTY_ID))
                .thenReturn(badServiceResp);

        Mockito.when(applicantService.validateApplicantPartyId(EXCEPTION_PARTY_ID)).thenThrow(ApiException.class);

        sut = new ApplicantController(applicantService);
    }

    /**
     * success
     */
    @Test
    public void ValidateFigaroControllerSuccess() throws ApiException {

        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(GOOD_PARTY_ID);
        Assertions.assertEquals(GOOD_CONTROLLER_RESPCD, response.getRespCode());
        Assertions.assertEquals("Party ID successfully validated.", response.getRespMsg());
        Assertions.assertEquals(SURNAME, response.getFoundSurname());
        Assertions.assertEquals(SECONDNAME, response.getFoundSecondName());
        Assertions.assertEquals(GENDER, response.getFoundGenderTxt());
        Assertions.assertEquals(FIRSTNAME, response.getFoundFirstName());
        Assertions.assertEquals(DRIVERLICENCE, response.getFoundDriversLicence());
        Assertions.assertEquals(BIRTHPLACE, response.getFoundBirthPlace());
        Assertions.assertEquals(BIRTHDATE, response.getFoundBirthDate());
    }

    /**
     * failure to find party id
     */
    @Test
    public void ValidateFigaroControllerFail() throws ApiException {

        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(BAD_PARTY_ID);
        Assertions.assertEquals(BAD_CONTROLLER_RESPCD, response.getRespCode());
        Assertions.assertEquals("Validation Failure: Applicant Party ID 20 is not for an Individual",
                response.getRespMsg());
    }

    /**
     * exception test
     */
    @Test
    public void ValidateFigaroControllerException() throws ApiException {

        ValidateApplicantPartyIdResponse response = sut.validateApplicantPartyId(EXCEPTION_PARTY_ID);
        Assertions.assertEquals(FAIL_CONTROLLER_RESPCD, response.getRespCode());
        Assertions.assertEquals(null, response.getRespMsg());
    }

}

