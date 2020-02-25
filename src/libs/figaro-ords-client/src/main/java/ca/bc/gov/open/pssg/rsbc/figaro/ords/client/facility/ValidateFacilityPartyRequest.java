package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.facility;

/**
 *  Represents a Validate Facility Requests
 *
 * @author alexjoybc@github
 *
 */
public class ValidateFacilityPartyRequest {

    private String facilityPartyId;
    private String facilitySubname;
    private String facilitySubname2;
    private String facilitySubname3;
    private String facilitySubname4;
    private String facilitySubname5;

    public ValidateFacilityPartyRequest(String facilityPartyId, String facilitySubname, String facilitySubname2,
                                        String facilitySubname3, String facilitySubname4, String facilitySubname5) {
        this.facilityPartyId = facilityPartyId;
        this.facilitySubname = facilitySubname;
        this.facilitySubname2 = facilitySubname2;
        this.facilitySubname3 = facilitySubname3;
        this.facilitySubname4 = facilitySubname4;
        this.facilitySubname5 = facilitySubname5;
    }

    public String getFacilityPartyId() {
        return facilityPartyId;
    }

    public String getFacilitySubname() {
        return facilitySubname;
    }

    public String getFacilitySubname2() {
        return facilitySubname2;
    }

    public String getFacilitySubname3() {
        return facilitySubname3;
    }

    public String getFacilitySubname4() {
        return facilitySubname4;
    }

    public String getFacilitySubname5() {
        return facilitySubname5;
    }
}
