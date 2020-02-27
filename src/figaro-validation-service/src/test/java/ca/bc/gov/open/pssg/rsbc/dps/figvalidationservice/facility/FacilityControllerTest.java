package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.FacilityService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FacilityControllerTest {

    private static final String FACILITY_PARTY_ID_SUCCESS = "1";
    private static final String FACILITY_PARTY_ID_FAIL = "2";
    private static final String FACILITY_NAME = "FacilityName";
    private static final String FOUND_FACILITY_PARTY_ID = "123";
    private static final String STATUS_CODE = "0";
    private static final String STATUS_MESSAGE = "success";
    private static final String VALIDATION_RESULT = "valid";
    private static final String ERROR_VALIDATION_RESULT = "invalid";

    @Mock
    private FacilityService facilityServiceMock;

    private FacilityController sut;

    @BeforeAll
    public void setup() {

        MockitoAnnotations.initMocks(this);

        ValidateFacilityPartyResponse successResponse = ValidateFacilityPartyResponse.SuccessResponse(VALIDATION_RESULT, STATUS_CODE, STATUS_MESSAGE, FOUND_FACILITY_PARTY_ID, FACILITY_NAME);

        ValidateFacilityPartyResponse errorResponse = ValidateFacilityPartyResponse.ErrorResponse(ERROR_VALIDATION_RESULT);

        Mockito.doReturn(successResponse).when(facilityServiceMock).validateFacilityParty(ArgumentMatchers.argThat(x -> x.getFacilityPartyId().equals(FACILITY_PARTY_ID_SUCCESS)));
        Mockito.doReturn(errorResponse).when(facilityServiceMock).validateFacilityParty(ArgumentMatchers.argThat(x -> x.getFacilityPartyId().equals(FACILITY_PARTY_ID_FAIL)));

        sut = new FacilityController(facilityServiceMock);
    }

    @Test
    public void withValidResponseShouldReturnValid() {

        ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyResponse result = sut.ValidateFacilityParty(FACILITY_PARTY_ID_SUCCESS, "a", "b", "c", "d", "e");

        Assertions.assertEquals(FACILITY_NAME, result.getFoundFacilityName());
        Assertions.assertEquals(FOUND_FACILITY_PARTY_ID, result.getFoundFacilityPartyId());
        Assertions.assertEquals(0, result.getRespCode());
        Assertions.assertEquals(STATUS_MESSAGE, result.getRespMsg());
        Assertions.assertEquals(VALIDATION_RESULT, result.getValidationResult());
    }

    @Test
    public void withInvalidResponseShouldReturnValid() {

        ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyResponse result = sut.ValidateFacilityParty(FACILITY_PARTY_ID_FAIL, "a", "b", "c", "d", "e");
        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_FAILURE_CD, result.getRespCode());
        Assertions.assertEquals(FigaroOrdsClientConstants.SERVICE_BOOLEAN_FALSE, result.getValidationResult());
        Assertions.assertEquals(ERROR_VALIDATION_RESULT, result.getRespMsg());
    }

}
