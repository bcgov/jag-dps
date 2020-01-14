package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * LocateMatchingApplicantsResponse class
 * 
 * @author shaunmillargov
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocateMatchingApplicantsResponse {

	private String respMsg;
	private int respCode;
	private String foundPartyId;
	private String foundSurname; 
	private String foundFirstName;
	private String foundSecondName; 
	private String foundBirthDate; 
	private String foundDriversLicence; 
	private String foundBirthPlace; 
	private String foundGenderTxt;
	
	public LocateMatchingApplicantsResponse() {}; 
	
	public LocateMatchingApplicantsResponse(String respMsg, int respCode, String foundPartyId, String foundSurname,
			String foundFirstName, String foundSecondName, String foundBirthDate, String foundDriversLicence,
			String foundBirthPlace, String foundGenderTxt) {
		super();
		this.respMsg = respMsg;
		this.respCode = respCode;
		this.foundPartyId = foundPartyId;
		this.foundSurname = foundSurname;
		this.foundFirstName = foundFirstName;
		this.foundSecondName = foundSecondName;
		this.foundBirthDate = foundBirthDate;
		this.foundDriversLicence = foundDriversLicence;
		this.foundBirthPlace = foundBirthPlace;
		this.foundGenderTxt = foundGenderTxt;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public int getRespCode() {
		return respCode;
	}

	public String getFoundPartyId() {
		return foundPartyId;
	}

	public String getFoundSurname() {
		return foundSurname;
	}

	public String getFoundFirstName() {
		return foundFirstName;
	}

	public String getFoundSecondName() {
		return foundSecondName;
	}

	public String getFoundBirthDate() {
		return foundBirthDate;
	}

	public String getFoundDriversLicence() {
		return foundDriversLicence;
	}

	public String getFoundBirthPlace() {
		return foundBirthPlace;
	}

	public String getFoundGenderTxt() {
		return foundGenderTxt;
	} 
	
}


