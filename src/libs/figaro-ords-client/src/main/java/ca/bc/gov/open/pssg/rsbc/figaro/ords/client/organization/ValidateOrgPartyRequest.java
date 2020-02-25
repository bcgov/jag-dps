package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

/**
 *  Represents a Validate Org Party Requests
 *
 * @author carolcarpenterjustice
 */
public class ValidateOrgPartyRequest {

    String orgCity;
    String orgPartyId;
    String orgSubname1;
    String orgSubname2;
    String orgSubname3;
    String orgSubname4;
    String orgSubname5;

    public ValidateOrgPartyRequest(String orgCity, String orgPartyId, String orgSubname1, String orgSubname2, String orgSubname3, String orgSubname4, String orgSubname5) {
        this.orgCity = orgCity;
        this.orgPartyId = orgPartyId;
        this.orgSubname1 = orgSubname1;
        this.orgSubname2 = orgSubname2;
        this.orgSubname3 = orgSubname3;
        this.orgSubname4 = orgSubname4;
        this.orgSubname5 = orgSubname5;
    }

    public String getOrgCity() { return orgCity; }

    public String getOrgPartyId() { return orgPartyId; }

    public String getOrgSubname1() { return orgSubname1; }

    public String getOrgSubname2() { return orgSubname2; }

    public String getOrgSubname3() { return orgSubname3; }

    public String getOrgSubname4() { return orgSubname4; }

    public String getOrgSubname5() { return orgSubname5; }
}
