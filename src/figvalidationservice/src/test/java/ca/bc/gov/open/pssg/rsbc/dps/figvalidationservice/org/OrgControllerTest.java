package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.FigaroValidationServiceConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgControllerTest {

    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String VALIDATION_RESULT = "valid";
    private static final String ERROR_VALIDATION_RESULT = "invalid";

    @Mock
    private OrgService orgServiceMock;

    private OrgController sut;

    @BeforeAll
    public void setup() {

        MockitoAnnotations.initMocks(this);

        ValidateOrgDrawDownBalanceResponse successResponse = ValidateOrgDrawDownBalanceResponse.SuccessResponse(VALIDATION_RESULT, STATUS_CODE, STATUS_MESSAGE);

        ValidateOrgDrawDownBalanceResponse errorResponse = ValidateOrgDrawDownBalanceResponse.ErrorResponse(ERROR_VALIDATION_RESULT);

        Mockito.doReturn(successResponse).when(orgServiceMock).validateOrgDrawDownBalance(ArgumentMatchers.argThat(x -> x.getJurisdictionType().equals("1")));
        Mockito.doReturn(errorResponse).when(orgServiceMock).validateOrgDrawDownBalance(ArgumentMatchers.argThat(x -> x.getJurisdictionType().equals("2")));

        sut = new OrgController(orgServiceMock);
    }

    @Test
    public void withValidResponseShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.ValidateOrgDrawDownBalance("1", "b", "c");

        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        ValidateOrgDrawDownBalanceResponse result = sut.ValidateOrgDrawDownBalance("2", "b", "c");
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroValidationServiceConstants.VALIDATION_SERVICE_BOOLEAN_FALSE, result.getRespMsg());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getValidationResult());
    }
}