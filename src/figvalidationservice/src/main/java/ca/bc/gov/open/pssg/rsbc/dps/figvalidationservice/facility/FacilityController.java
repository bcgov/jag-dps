package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.facility;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose endpoints for facility related validations
 *
 * @author alexjoybc@github
 *
 */
@RestController
public class FacilityController {

    private final FacilityService facilityService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @RequestMapping(value = "/validateFacilityParty",
            produces = { "application/xml" },
            method = RequestMethod.GET)
    @ApiOperation(value = "Validate Facility Party", response = ValidateFacilityPartyResponse.class, tags={ "Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateFacilityPartyResponse.class) })
    public ValidateFacilityPartyResponse ValidateFacilityParty(
            @ApiParam(value = "facilityPartyId", required = false) @RequestParam(value="facilityPartyId", defaultValue="") String facilityPartyId,
            @ApiParam(value = "facilitySubname1", required = false) @RequestParam(value="facilitySubname1", defaultValue="")String facilitySubname1,
            @ApiParam(value = "facilitySubname2", required = false) @RequestParam(value="facilitySubname2", defaultValue="")String facilitySubname2,
            @ApiParam(value = "facilitySubname3", required = false) @RequestParam(value="facilitySubname3", defaultValue="")String facilitySubname3,
            @ApiParam(value = "facilitySubname4", required = false) @RequestParam(value="facilitySubname4", defaultValue="")String facilitySubname4,
            @ApiParam(value = "facilitySubname5", required = false) @RequestParam(value="facilitySubname5", defaultValue="")String facilitySubname5) {


        return this.facilityService.validateFacilityParty(new ValidateFacilityPartyRequest(facilityPartyId, facilitySubname1, facilitySubname2, facilitySubname3, facilitySubname4, facilitySubname5));

    }


}

