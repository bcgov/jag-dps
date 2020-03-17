package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 
 * LocateMatchingApplicantsResponse class
 * 
 * @author shaunmillargov
 *
 */
@JacksonXmlRootElement(localName = "locateMatchingApplicantsResponse")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuppressWarnings("java:S107")
//This is a legacy migration and requires this many parameters
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
	
	private LocateMatchingApplicantsResponse(int respCode, String respMsg) {
		this.respMsg = respMsg;
		this.respCode = respCode;
	}
	
	public LocateMatchingApplicantsResponse(int respCode, String respMsg, String foundPartyId, String foundSurname,
			String foundFirstName, String foundSecondName, String foundBirthDate, String foundDriversLicence,
			String foundBirthPlace, String foundGenderTxt) {
		this(respCode, respMsg);
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

	public static LocateMatchingApplicantsResponse errorResponse(String errorMessage) {

		return new LocateMatchingApplicantsResponse(
				FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
				errorMessage);
	}

	public static LocateMatchingApplicantsResponse successResponse(String respCode, String respMsg, String foundPartyId, String foundSurname,
																   String foundFirstName, String foundSecondName, String foundBirthDate, String foundDriversLicence,
																   String foundBirthPlace, String foundGenderTxt) {

		return new LocateMatchingApplicantsResponse(Integer.parseInt(respCode), respMsg, foundPartyId, foundSurname,
				foundFirstName, foundSecondName, foundBirthDate, foundDriversLicence,
				foundBirthPlace, foundGenderTxt);
	}
}


