package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.OrganizationService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.ValidateOrgDrawDownBalanceResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.ValidateOrgPartyContactPersonResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.ValidateOrgPartyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrganizationControllerTest {

    private static final String CASE_SUCCESS = "1";
    private static final String CASE_FAIL = "2";
    private static final String FOUND_ORG_PARTY_ID = "1";
    private static final String FOUND_ORG_PARTY_NAME = "Name";
    private static final String FOUND_ORG_PARTY_TYPE = "Type";
    private static final String FOUND_ORG_CONTACT_NAME = "Contact Name";
    private static final String FOUND_ORG_CONTACT_PARTY_ID = "Contact Party ID";
    private static final String FOUND_ORG_CONTACT_ROLE = "Contact Role";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String VALIDATION_RESULT = "valid";
    private static final String ERROR_VALIDATION_RESULT = "invalid";

    @Mock
    private OrganizationService organizationServiceMock;

    private OrganizationController sut;

    @BeforeAll
    public void setup() {

        MockitoAnnotations.initMocks(this);

        ValidateOrgDrawDownBalanceResponse successResponse1 = ValidateOrgDrawDownBalanceResponse.SuccessResponse(VALIDATION_RESULT, STATUS_CODE, STATUS_MESSAGE);
        ValidateOrgDrawDownBalanceResponse errorResponse1 = ValidateOrgDrawDownBalanceResponse.ErrorResponse(ERROR_VALIDATION_RESULT);

        Mockito.doReturn(successResponse1).when(organizationServiceMock).validateOrgDrawDownBalance(ArgumentMatchers.argThat(x -> x.getJurisdictionType().equals("1")));
        Mockito.doReturn(errorResponse1).when(organizationServiceMock).validateOrgDrawDownBalance(ArgumentMatchers.argThat(x -> x.getJurisdictionType().equals("2")));

        List<ValidateOrgPartyContactPersonResponse> contactPersons = new ArrayList<ValidateOrgPartyContactPersonResponse>();
        contactPersons.add(ValidateOrgPartyContactPersonResponse.SuccessResponse(FOUND_ORG_CONTACT_NAME, FOUND_ORG_CONTACT_ROLE, FOUND_ORG_CONTACT_PARTY_ID));

        ValidateOrgPartyResponse successResponse2 = ValidateOrgPartyResponse.SuccessResponse(VALIDATION_RESULT, STATUS_CODE, STATUS_MESSAGE, FOUND_ORG_PARTY_ID, FOUND_ORG_PARTY_NAME, FOUND_ORG_PARTY_TYPE, contactPersons);
        ValidateOrgPartyResponse errorResponse2 = ValidateOrgPartyResponse.ErrorResponse(ERROR_VALIDATION_RESULT);

        Mockito.doReturn(successResponse2).when(organizationServiceMock).validateOrgParty(ArgumentMatchers.argThat(x -> x.getOrgCity().equals(CASE_SUCCESS)));
        Mockito.doReturn(errorResponse2).when(organizationServiceMock).validateOrgParty(ArgumentMatchers.argThat(x -> x.getOrgCity().equals(CASE_FAIL)));

        sut = new OrganizationController(organizationServiceMock);
    }

    @Test
    public void withValidResponseDdbShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.ValidateOrgDrawDownBalance(CASE_SUCCESS, "b", "c");

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponseDdbShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.ValidateOrgDrawDownBalance(CASE_FAIL, "b", "c");

        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withValidResponsePartyShouldReturnValid() {

        ValidateOrgPartyResponse result = sut.ValidateOrgParty(CASE_SUCCESS, "b", "c", "d", "e", "f", "g");

        Assertions.assertEquals(FOUND_ORG_PARTY_NAME, result.getFoundOrgName());
        Assertions.assertEquals(FOUND_ORG_PARTY_ID, result.getFoundOrgPartyId());
        Assertions.assertEquals(FOUND_ORG_PARTY_TYPE, result.getFoundOrgType());

        Assertions.assertNotNull(result.getContactPersons());
        Assertions.assertEquals(1, result.getContactPersons().size());
        Assertions.assertEquals(FOUND_ORG_CONTACT_NAME, result.getContactPersons().get(0).getContactPersonName());
        Assertions.assertEquals(FOUND_ORG_CONTACT_PARTY_ID, result.getContactPersons().get(0).getContactPersonPartyId());
        Assertions.assertEquals(FOUND_ORG_CONTACT_ROLE, result.getContactPersons().get(0).getContactPersonRole());

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponsePartyShouldReturnValid() {

        ValidateOrgPartyResponse result = sut.ValidateOrgParty(CASE_FAIL, "b", "c", "d", "e", "f", "g");

        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }
}