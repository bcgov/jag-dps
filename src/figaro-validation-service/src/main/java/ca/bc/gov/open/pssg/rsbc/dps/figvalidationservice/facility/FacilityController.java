package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.FacilityService;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyRequest;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Expose endpoints for facility related validations
 *
 * @author alexjoybc@github
 *
 */
@RestController
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping(value = "/validateFacilityParty",
            produces = { MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Validate Facility Party", response = ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility.ValidateFacilityPartyResponse.class, tags={"Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateFacilityPartyResponse.class) })
    public ValidateFacilityPartyResponse validateFacilityParty(
            @ApiParam(value = "facilityPartyId", required = false) @RequestParam(value="facilityPartyId", defaultValue="") String facilityPartyId,
            @ApiParam(value = "facilitySubName1", required = false) @RequestParam(value="facilitySubName1", defaultValue="")String facilitySubname1,
            @ApiParam(value = "facilitySubName2", required = false) @RequestParam(value="facilitySubName2", defaultValue="")String facilitySubname2,
            @ApiParam(value = "facilitySubName3", required = false) @RequestParam(value="facilitySubName3", defaultValue="")String facilitySubname3,
            @ApiParam(value = "facilitySubName4", required = false) @RequestParam(value="facilitySubName4", defaultValue="")String facilitySubname4,
            @ApiParam(value = "facilitySubName5", required = false) @RequestParam(value="facilitySubName5", defaultValue="")String facilitySubname5) {


        return this.facilityService.validateFacilityParty(new ValidateFacilityPartyRequest(facilityPartyId, facilitySubname1, facilitySubname2, facilitySubname3, facilitySubname4, facilitySubname5));
    }
}

