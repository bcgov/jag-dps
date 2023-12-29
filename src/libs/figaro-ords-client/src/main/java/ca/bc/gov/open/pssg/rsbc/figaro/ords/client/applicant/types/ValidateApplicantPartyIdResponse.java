package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.FigaroOrdsClientConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * 
 * ValidateApplicantPartyIdResponse class
 * 
 * @author shaunmillargov
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuppressWarnings("java:S107")
//This is a legacy migration and requires this many parameters
public class ValidateApplicantPartyIdResponse {

	private String respMsg;
	private int respCode;
	private String foundSurname; 
	private String foundFirstName;
	private String foundSecondName; 
	private String foundBirthDate; 
	private String foundDriversLicence; 
	private String foundBirthPlace; 
	private String foundGenderTxt;


	private ValidateApplicantPartyIdResponse(int respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public ValidateApplicantPartyIdResponse(String respMsg, int respCode, String foundSurname, String foundFirstName,
			String foundSecondName, String foundBirthDate, String foundDriversLicence, String foundBirthPlace,
			String foundGenderTxt) {
		this(respCode, respMsg);
		this.foundSurname = foundSurname;
		this.foundFirstName = foundFirstName;
		this.foundSecondName = foundSecondName;
		this.foundBirthDate = foundBirthDate;
		this.foundDriversLicence = foundDriversLicence;
		this.foundBirthPlace = foundBirthPlace;
		this.foundGenderTxt = foundGenderTxt;
	}
	
	public ValidateApplicantPartyIdResponse(String respMsg, int respCode) {
		this.respMsg = respMsg;
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public int getRespCode() {
		return respCode;
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

	public String getFoundGenderTxt() { return foundGenderTxt; }

	public static ValidateApplicantPartyIdResponse errorResponse(String errorMessage) {
		return new ValidateApplicantPartyIdResponse(
				FigaroOrdsClientConstants.SERVICE_FAILURE_CD,
				errorMessage);
	}

	public static ValidateApplicantPartyIdResponse successResponse(String respCodeStr, String respMsg, String foundSurname, String foundFirstName,
																   String foundSecondName, String foundBirthDate, String foundDriversLicence, String foundBirthPlace,
																   String foundGenderTxt) {

		return new ValidateApplicantPartyIdResponse(respMsg, Integer.parseInt(respCodeStr), foundSurname, foundFirstName,
				foundSecondName, foundBirthDate, foundDriversLicence, foundBirthPlace,
				foundGenderTxt);
	}
}


