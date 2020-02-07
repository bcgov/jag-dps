package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

/**
 *  Represents a Validate Org Requests
 *
 * @author carolcarpenterjustice
 */
public class ValidateOrgDrawDownBalanceRequest {

    private String orgPartyId;
    private String scheduleType;
    private String jurisdictionType;

    public ValidateOrgDrawDownBalanceRequest(String jurisdictionType, String orgPartyId, String scheduleType) {
        this.orgPartyId = orgPartyId;
        this.scheduleType = scheduleType;
        this.jurisdictionType = jurisdictionType;
    }

    public String getOrgPartyId() { return orgPartyId; }

    public String getScheduleType() { return scheduleType; }

    public String getJurisdictionType() { return jurisdictionType; }
}
