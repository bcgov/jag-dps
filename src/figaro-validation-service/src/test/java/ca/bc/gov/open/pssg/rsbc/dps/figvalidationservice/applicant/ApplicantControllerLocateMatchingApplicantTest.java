package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.LocateMatchingApplicantsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicantControllerLocateMatchingApplicantTest {


    private static final String RESP_CODE = "0";
    private static final String RESP_MSG = "respMsg";
    private static final String FOUND_PARTY_ID = "foundPartyId";
    private static final String FOUND_SURNAME = "foundSurname";
    private static final String FOUND_FIRST_NAME = "foundFirstName";
    private static final String FOUND_SECOND_NAME = "foundSecondName";
    private static final String FOUND_BIRTH_DATE = "foundBirthDate";
    private static final String FOUND_DRIVERS_LICENCE = "foundDriversLicence";
    private static final String FOUND_BIRTH_PLACE = "foundBirthPlace";
    private static final String FOUND_GENDER_TXT = "foundGenderTxt";
    private ApplicantController sut;

    @Mock
    private ApplicantServiceImpl applicantServiceMock;

    @BeforeAll
    public void setup() {

        MockitoAnnotations.initMocks(this);

        LocateMatchingApplicantsResponse successResponse = LocateMatchingApplicantsResponse.SuccessResponse(
                RESP_CODE,
                RESP_MSG,
                FOUND_PARTY_ID,
                FOUND_SURNAME,
                FOUND_FIRST_NAME,
                FOUND_SECOND_NAME,
                FOUND_BIRTH_DATE,
                FOUND_DRIVERS_LICENCE,
                FOUND_BIRTH_PLACE,
                FOUND_GENDER_TXT);

        LocateMatchingApplicantsResponse errorResponse = LocateMatchingApplicantsResponse.ErrorResponse();

        Mockito.doReturn(successResponse).when(applicantServiceMock).locateMatchingApplicants(ArgumentMatchers.argThat(x -> x.getApplAliasFirstName1().equals("1")));
        Mockito.doReturn(errorResponse).when(applicantServiceMock).locateMatchingApplicants(ArgumentMatchers.argThat(x -> x.getApplAliasFirstName1().equals("2")));

        sut = new ApplicantController(applicantServiceMock);

    }

    @Test
    public void WithValidResponseShouldReturnResponse() {

        LocateMatchingApplicantsResponse response = sut.locateMatchingApplicants("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1");

        Assertions.assertEquals(0, response.getRespCode());
        Assertions.assertEquals(RESP_MSG, response.getRespMsg());
        Assertions.assertEquals(FOUND_PARTY_ID, response.getFoundPartyId());
        Assertions.assertEquals(FOUND_SURNAME, response.getFoundSurname());
        Assertions.assertEquals(FOUND_FIRST_NAME, response.getFoundFirstName());
        Assertions.assertEquals(FOUND_SECOND_NAME, response.getFoundSecondName());
        Assertions.assertEquals(FOUND_BIRTH_DATE, response.getFoundBirthDate());
        Assertions.assertEquals(FOUND_DRIVERS_LICENCE, response.getFoundDriversLicence());
        Assertions.assertEquals(FOUND_BIRTH_PLACE, response.getFoundBirthPlace());
        Assertions.assertEquals(FOUND_GENDER_TXT, response.getFoundGenderTxt());

    }

    @Test
    public void WithErrorResponseShouldReturnResponse() {

        LocateMatchingApplicantsResponse response = sut.locateMatchingApplicants("2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2");

        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, response.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, response.getRespMsg());

    }



}
