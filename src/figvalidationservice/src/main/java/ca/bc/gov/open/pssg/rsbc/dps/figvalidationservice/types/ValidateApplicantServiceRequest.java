package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types;

/**
 * 
 * ValidateApplicantServiceRequest class
 * 
 * @author shaunmillargov
 *
 */
public class ValidateApplicantServiceRequest {
	
	private String orgPartyId; 
	private String applPartyId;
	
	public ValidateApplicantServiceRequest(String orgPartyId, String applPartyId) {
		super();
		this.orgPartyId = orgPartyId;
		this.applPartyId = applPartyId;
	}

	public String getOrgPartyId() {
		return orgPartyId;
	}

	public String getApplPartyId() {
		return applPartyId;
	}
	
}

