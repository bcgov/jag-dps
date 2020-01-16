package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types;


/**
 * 
 * model class for request messages for /validateApplicantForSharing requests
 * 
 * @author archana
 *
 */
public class ValidateApplicantForSharingRequest {
	
	private String applPartyId; 
	
	private String jurisdictionType;
	
	public ValidateApplicantForSharingRequest(String applPartyId, String jurisdictionType) {
		super();
		this.applPartyId = applPartyId;
		this.jurisdictionType = jurisdictionType;
	}
	
	public String getApplPartyId() {
		return applPartyId;
	}

	public void setApplPartyId(String applPartyId) {
		this.applPartyId = applPartyId;
	}

	public String getJurisdictionType() {
		return jurisdictionType;
	}

	public void setJurisdictionType(String jurisdictionType) {
		this.jurisdictionType = jurisdictionType;
	}


}

