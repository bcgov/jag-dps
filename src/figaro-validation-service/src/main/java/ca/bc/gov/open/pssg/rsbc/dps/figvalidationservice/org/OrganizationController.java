package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expose endpoints for org related validations
 *
 * @author carolcarpenterjustice
 */
@RestController
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping(value = "/validateOrgDrawDownBalance",
            produces = { MediaType.APPLICATION_XML_VALUE },
            method = RequestMethod.GET)
    @ApiOperation(value = "Validate Org Draw Down Balance", response = ValidateOrgDrawDownBalanceResponse.class, tags={"Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateOrgDrawDownBalanceResponse.class) })
    public ValidateOrgDrawDownBalanceResponse ValidateOrgDrawDownBalance(
            @ApiParam(value = "jurisdictionType", required = false) @RequestParam(value="jurisdictionType", defaultValue="") String jurisdictionType,
            @ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="") String orgPartyId,
            @ApiParam(value = "scheduleType", required = false) @RequestParam(value="scheduleType", defaultValue="") String scheduleType) {

        return this.organizationService.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest(jurisdictionType, orgPartyId, scheduleType));
    }

    @RequestMapping(value = "/validateOrgParty",
            produces = { MediaType.APPLICATION_XML_VALUE },
            method = RequestMethod.GET)
    @ApiOperation(value = "Validate Org Party", response = ValidateOrgPartyResponse.class, tags={"Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateOrgPartyResponse.class) })
    public ValidateOrgPartyResponse ValidateOrgParty(
            @ApiParam(value = "orgCity", required = false) @RequestParam(value="orgCity", defaultValue="") String orgCity,
            @ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="") String orgPartyId,
            @ApiParam(value = "orgSubName1", required = false) @RequestParam(value="orgSubName1", defaultValue="") String orgSubname1,
            @ApiParam(value = "orgSubName2", required = false) @RequestParam(value="orgSubName2", defaultValue="") String orgSubname2,
            @ApiParam(value = "orgSubName3", required = false) @RequestParam(value="orgSubName3", defaultValue="") String orgSubname3,
            @ApiParam(value = "orgSubName4", required = false) @RequestParam(value="orgSubName4", defaultValue="") String orgSubname4,
            @ApiParam(value = "orgSubName5", required = false) @RequestParam(value="orgSubName5", defaultValue="") String orgSubname5) {

        return this.organizationService.validateOrgParty(new ValidateOrgPartyRequest(orgCity, orgPartyId, orgSubname1, orgSubname2, orgSubname3, orgSubname4, orgSubname5));
    }
}
