package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.MatchingApplicantsOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.LocateMatchingApplicantsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantServiceLocateMatchingApplicantTest {

    private static final String FOUND_BIRTH_DATE = "birtdate";
    private static final String FOUND_BIRTH_PLACE = "birthplace";
    private static final String FOUND_DRIVERS_LICENSE = "driverlicence";
    private static final String FOUND_FIRST_NAME = "firstname";
    private static final String FOUND_GENDER = "gender";
    private static final String FOUND_PARTY_ID = "partyid";
    private static final String FOUND_SECOND_NAME = "secondname";
    private static final String FOUND_SURNAME = "surename";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "statusmessage";
    private static final String ERROR_STATUS_CODE = "-22";
    private static final String ERROR_STATUS_MESSAGE = "ERROR";
    private static final String APPL_SURNAME = "applSurname";
    private static final String APPL_FIRST_NAME = "applFirstName";
    private static final String APPL_SECOND_INITIAL = "applSecondInitial";
    private static final String APLL_BIRTH_DATE = "apllBirthDate";
    private static final String APPL_DRIVERS_LICENCE = "applDriversLicence";
    private static final String APPL_BIRTH_PLACE = "applBirthPlace";
    private static final String APPL_GENDER_TXT = "applGenderTxt";
    private static final String APPL_SURNAME_1 = "applSurname1";
    private static final String APPL_FIRSTNAME_1 = "applFirstname1";
    private static final String APPL_ALIAS_SECOND_INITIAL_1 = "applAliasSecondInitial1";
    private static final String APPL_ALIAS_SURNAME_2 = "applAliasSurname2";
    private static final String APPL_ALIAS_FIRSTNAME_2 = "applAliasFirstname2";
    private static final String APPL_ALIAS_SECOND_INITIAL_2 = "applAliasSecondInitial2";
    private static final String APPL_ALIAS_SURNAME_3 = "applAliasSurname3";
    private static final String APPL_ALIAS_FIRST_NAME_3 = "applAliasFirstName3";
    private static final String APPL_ALIAS_SECOND_INITIAL_3 = "applAliasSecondInitial3";

    private ApplicantServiceImpl sut;

    @Mock
    private ApplicantApi applicantApiMock;

    @BeforeAll
    public void setup() throws ApiException {

        MockitoAnnotations.initMocks(this);

        MatchingApplicantsOrdsResponse successResponse = new MatchingApplicantsOrdsResponse();
        successResponse.setFoundBirthDate(FOUND_BIRTH_DATE);
        successResponse.setFoundBirthPlace(FOUND_BIRTH_PLACE);
        successResponse.setFoundDriversLicense(FOUND_DRIVERS_LICENSE);
        successResponse.setFoundFirstName(FOUND_FIRST_NAME);
        successResponse.setFoundGender(FOUND_GENDER);
        successResponse.setFoundPartyId(FOUND_PARTY_ID);
        successResponse.setFoundSecondName(FOUND_SECOND_NAME);
        successResponse.setFoundSurname(FOUND_SURNAME);
        successResponse.setStatusCode(STATUS_CODE);
        successResponse.setStatusMessage(STATUS_MESSAGE);

        MatchingApplicantsOrdsResponse errorResponse = new MatchingApplicantsOrdsResponse();
        errorResponse.setStatusCode(ERROR_STATUS_CODE);
        errorResponse.setStatusMessage(ERROR_STATUS_MESSAGE);

        Mockito.when(applicantApiMock.matchingApplicants(
                Mockito.eq(APPL_FIRSTNAME_1),
                Mockito.eq(APPL_ALIAS_FIRSTNAME_2),
                Mockito.eq(APPL_ALIAS_FIRST_NAME_3),
                Mockito.eq(APPL_ALIAS_SECOND_INITIAL_1),
                Mockito.eq(APPL_ALIAS_SECOND_INITIAL_2),
                Mockito.eq(APPL_ALIAS_SECOND_INITIAL_3),
                Mockito.eq(APPL_SURNAME_1),
                Mockito.eq(APPL_ALIAS_SURNAME_2),
                Mockito.eq(APPL_ALIAS_SURNAME_3),
                Mockito.eq(APLL_BIRTH_DATE),
                Mockito.eq(APPL_BIRTH_PLACE),
                Mockito.eq(APPL_DRIVERS_LICENCE),
                Mockito.eq(APPL_FIRST_NAME),
                Mockito.eq(APPL_GENDER_TXT),
                Mockito.eq(APPL_SECOND_INITIAL),
                Mockito.eq(APPL_SURNAME))).thenReturn(successResponse);

        Mockito.when(applicantApiMock.matchingApplicants(
                Mockito.eq("2"),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString())).thenReturn(errorResponse);


        Mockito.when(applicantApiMock.matchingApplicants(
                Mockito.eq("3"),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString())).thenThrow(ApiException.class);

        sut = new ApplicantServiceImpl(applicantApiMock);

    }

    @Test
    public void WithValidResponseShouldReturnValidResponse() {

        LocateMatchingApplicantsResponse response = sut.locateMatchingApplicants(
                new LocateMatchingApplicantsRequest(
                        APPL_SURNAME,
                        APPL_FIRST_NAME,
                        APPL_SECOND_INITIAL,
                        APLL_BIRTH_DATE,
                        APPL_DRIVERS_LICENCE,
                        APPL_BIRTH_PLACE,
                        APPL_GENDER_TXT,
                        APPL_SURNAME_1,
                        APPL_FIRSTNAME_1,
                        APPL_ALIAS_SECOND_INITIAL_1,
                        APPL_ALIAS_SURNAME_2,
                        APPL_ALIAS_FIRSTNAME_2,
                        APPL_ALIAS_SECOND_INITIAL_2,
                        APPL_ALIAS_SURNAME_3,
                        APPL_ALIAS_FIRST_NAME_3,
                        APPL_ALIAS_SECOND_INITIAL_3));

        Assertions.assertEquals(FOUND_BIRTH_DATE, response.getFoundBirthDate());
        Assertions.assertEquals(FOUND_BIRTH_PLACE, response.getFoundBirthPlace());
        Assertions.assertEquals(FOUND_DRIVERS_LICENSE, response.getFoundDriversLicence());
        Assertions.assertEquals(FOUND_FIRST_NAME, response.getFoundFirstName());
        Assertions.assertEquals(FOUND_GENDER, response.getFoundGenderTxt());
        Assertions.assertEquals(FOUND_PARTY_ID, response.getFoundPartyId());
        Assertions.assertEquals(FOUND_SECOND_NAME, response.getFoundSecondName());
        Assertions.assertEquals(FOUND_SURNAME, response.getFoundSurname());
        Assertions.assertEquals(0, response.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, response.getRespMsg());
    }

    @Test
    public void WithErrorResponseShouldReturnErrorResponse() {

        LocateMatchingApplicantsResponse response = sut.locateMatchingApplicants(
                new LocateMatchingApplicantsRequest(
                        APPL_SURNAME,
                        APPL_FIRST_NAME,
                        APPL_SECOND_INITIAL,
                        APLL_BIRTH_DATE,
                        APPL_DRIVERS_LICENCE,
                        APPL_BIRTH_PLACE,
                        APPL_GENDER_TXT,
                        APPL_SURNAME_1,
                        "2",
                        APPL_ALIAS_SECOND_INITIAL_1,
                        APPL_ALIAS_SURNAME_2,
                        APPL_ALIAS_FIRSTNAME_2,
                        APPL_ALIAS_SECOND_INITIAL_2,
                        APPL_ALIAS_SURNAME_3,
                        APPL_ALIAS_FIRST_NAME_3,
                        APPL_ALIAS_SECOND_INITIAL_3));

        Assertions.assertEquals(-22, response.getRespCode());
        Assertions.assertEquals(ERROR_STATUS_MESSAGE, response.getRespMsg());
    }

    @Test
    public void WithExceptionShouldReturnErrorResponse() {

        LocateMatchingApplicantsResponse response = sut.locateMatchingApplicants(
                new LocateMatchingApplicantsRequest(
                        APPL_SURNAME,
                        APPL_FIRST_NAME,
                        APPL_SECOND_INITIAL,
                        APLL_BIRTH_DATE,
                        APPL_DRIVERS_LICENCE,
                        APPL_BIRTH_PLACE,
                        APPL_GENDER_TXT,
                        APPL_SURNAME_1,
                        "3",
                        APPL_ALIAS_SECOND_INITIAL_1,
                        APPL_ALIAS_SURNAME_2,
                        APPL_ALIAS_FIRSTNAME_2,
                        APPL_ALIAS_SECOND_INITIAL_2,
                        APPL_ALIAS_SURNAME_3,
                        APPL_ALIAS_FIRST_NAME_3,
                        APPL_ALIAS_SECOND_INITIAL_3));

        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE, response.getRespMsg());
    }

}
