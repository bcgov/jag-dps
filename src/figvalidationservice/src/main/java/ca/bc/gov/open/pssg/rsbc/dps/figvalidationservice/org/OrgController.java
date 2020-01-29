package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

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
 * Expose endpoints for org related validations
 *
 * @author carolcarpenterjustice
 */
@RestController
public class OrgController {

    private final OrgService orgService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @RequestMapping(value = "/validateOrgDrawDownBalance",
            produces = { "application/xml" },
            method = RequestMethod.GET)
    @ApiOperation(value = "Validate Org Draw Down Balance", response = ValidateOrgDrawDownBalanceResponse.class, tags={ "Figaro Validation Services"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful operation", response = ValidateOrgDrawDownBalanceResponse.class) })
    public ValidateOrgDrawDownBalanceResponse ValidateOrgDrawDownBalance(
            @ApiParam(value = "jurisdictionType", required = false) @RequestParam(value="jurisdictionType", defaultValue="") String jurisdictionType,
            @ApiParam(value = "orgPartyId", required = false) @RequestParam(value="orgPartyId", defaultValue="")String orgPartyId,
            @ApiParam(value = "scheduleType", required = false) @RequestParam(value="scheduleType", defaultValue="")String scheduleType) {

        return this.orgService.validateOrgDrawDownBalance(new ValidateOrgDrawDownBalanceRequest(jurisdictionType, orgPartyId, scheduleType));

    }
}
