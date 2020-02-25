package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

/**
 * 
 * model class for request messages for /validateApplicantForSharing requests
 * 
 * @author archanasudha
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

	public String getJurisdictionType() {
		return jurisdictionType;
	}

}
