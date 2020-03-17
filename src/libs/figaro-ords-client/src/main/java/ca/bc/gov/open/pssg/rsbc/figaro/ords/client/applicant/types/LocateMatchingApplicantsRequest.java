package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types;

/**
 * 
 * LocateMatchingApplicantsRequest class
 * 
 * @author shaunmillargov
 *
 */
public class LocateMatchingApplicantsRequest {
	
	private String applSurname; 
	private String applFirstName;
	private String applSecondInitial; 
	private String applBirthDate; 
	private String applDriversLicence; 
	private String applBirthPlace; 
	private String applGenderTxt;
	private String applAliasSurname1;
	private String applAliasFirstName1;
	private String applAliasSecondInitial1;
	private String applAliasSurname2;
	private String applAliasFirstName2;
	private String applAliasSecondInitial2;
	private String applAliasSurname3;
	private String applAliasFirstName3;
	private String applAliasSecondInitial3;

	@SuppressWarnings("java:S107")
	//This is a legacy migration and requires this many parameters
	public LocateMatchingApplicantsRequest(String applSurname, String applFirstName, String applSecondInitial,
			String applBirthDate, String applDriversLicence, String applBirthPlace, String applGenderTxt,
			String applAliasSurname1, String applAliasFirstName1, String applAliasSecondInitial1,
			String applAliasSurname2, String applAliasFirstName2, String applAliasSecondInitial2,
			String applAliasSurname3, String applAliasFirstName3, String applAliasSecondInitial3) {
		super();
		this.applSurname = applSurname;
		this.applFirstName = applFirstName;
		this.applSecondInitial = applSecondInitial;
		this.applBirthDate = applBirthDate;
		this.applDriversLicence = applDriversLicence;
		this.applBirthPlace = applBirthPlace;
		this.applGenderTxt = applGenderTxt;
		this.applAliasSurname1 = applAliasSurname1;
		this.applAliasFirstName1 = applAliasFirstName1;
		this.applAliasSecondInitial1 = applAliasSecondInitial1;
		this.applAliasSurname2 = applAliasSurname2;
		this.applAliasFirstName2 = applAliasFirstName2;
		this.applAliasSecondInitial2 = applAliasSecondInitial2;
		this.applAliasSurname3 = applAliasSurname3;
		this.applAliasFirstName3 = applAliasFirstName3;
		this.applAliasSecondInitial3 = applAliasSecondInitial3;
	}

	public String getApplSurname() {
		return applSurname;
	}

	public String getApplFirstName() {
		return applFirstName;
	}

	public String getApplSecondInitial() {
		return applSecondInitial;
	}

	public String getApplBirthDate() {
		return applBirthDate;
	}

	public String getApplDriversLicence() {
		return applDriversLicence;
	}

	public String getApplBirthPlace() {
		return applBirthPlace;
	}

	public String getApplGenderTxt() {
		return applGenderTxt;
	}

	public String getApplAliasSurname1() {
		return applAliasSurname1;
	}

	public String getApplAliasFirstName1() {
		return applAliasFirstName1;
	}

	public String getApplAliasSecondInitial1() {
		return applAliasSecondInitial1;
	}

	public String getApplAliasSurname2() {
		return applAliasSurname2;
	}

	public String getApplAliasFirstName2() {
		return applAliasFirstName2;
	}

	public String getApplAliasSecondInitial2() {
		return applAliasSecondInitial2;
	}

	public String getApplAliasSurname3() {
		return applAliasSurname3;
	}

	public String getApplAliasFirstName3() {
		return applAliasFirstName3;
	}

	public String getApplAliasSecondInitial3() {
		return applAliasSecondInitial3;
	}
	
}

