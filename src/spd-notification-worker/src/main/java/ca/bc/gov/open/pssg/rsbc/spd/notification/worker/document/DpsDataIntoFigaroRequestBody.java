package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

/**
 *  Represents the DPS Data Into Figaro Requests
 *
 * @author carolcarpenterjustice
 *
 */
public class DpsDataIntoFigaroRequestBody {

    private String scheduleType;
    private String jurisdictionType;
    private String processingStream;
    private String applicationCategory;
    private String paymentMethod;
    private String nonFinRejectReason;
    private String applicationSignedYn;
    private String applicationSignedDate;
    private String applicationGuardianSignedYn;
    private String applicationPaymentId;
    private String applicationIncompleteReason;
    private String applicationValidateUsername;
    private String applicationDocumentGuid;
    private String applPartyId;
    private String applSurname;
    private String applFirstName;
    private String applSecondName;
    private String applBirthDate;
    private String applGender;
    private String applBirthPlace;
    private String applAddlSurname1;
    private String applAddlFirstName1;
    private String applAddlSecondName1;
    private String applAddlSurname2;
    private String applAddlFirstName2;
    private String applAddlSecondName2;
    private String applAddlSurname3;
    private String applAddlFirstName3;
    private String applAddlSecondName3;
    private String applStreetAddress;
    private String applCity;
    private String applProvince;
    private String applCountry;
    private String applPostalCode;
    private String applDriversLicence;
    private String applPhoneNumber;
    private String applEmailAddress;
    private String applOrgPartyId;
    private String applOrgFacilityPartyId;
    private String applOrgFacilityName;
    private String applOrgContactPartyId;

    public DpsDataIntoFigaroRequestBody(String scheduleType,
                                    String jurisdictionType,
                                    String processingStream,
                                    String applicationCategory,
                                    String paymentMethod,
                                    String nonFinRejectReason,
                                    String applicationSignedYn,
                                    String applicationSignedDate,
                                    String applicationGuardianSignedYn,
                                    String applicationPaymentId,
                                    String applicationIncompleteReason,
                                    String applicationValidateUsername,
                                    String applicationDocumentGuid,
                                    String applPartyId,
                                    String applSurname,
                                    String applFirstName,
                                    String applSecondName,
                                    String applBirthDate,
                                    String applGender,
                                    String applBirthPlace,
                                    String applAddlSurname1,
                                    String applAddlFirstName1,
                                    String applAddlSecondName1,
                                    String applAddlSurname2,
                                    String applAddlFirstName2,
                                    String applAddlSecondName2,
                                    String applAddlSurname3,
                                    String applAddlFirstName3,
                                    String applAddlSecondName3,
                                    String applStreetAddress,
                                    String applCity,
                                    String applProvince,
                                    String applCountry,
                                    String applPostalCode,
                                    String applDriversLicence,
                                    String applPhoneNumber,
                                    String applEmailAddress,
                                    String applOrgPartyId,
                                    String applOrgFacilityPartyId,
                                    String applOrgFacilityName,
                                    String applOrgContactPartyId) {
        this.scheduleType = scheduleType;
        this.jurisdictionType = jurisdictionType;
        this.processingStream = processingStream;
        this.applicationCategory = applicationCategory;
        this.paymentMethod = paymentMethod;
        this.nonFinRejectReason = nonFinRejectReason;
        this.applicationSignedYn = applicationSignedYn;
        this.applicationSignedDate = applicationSignedDate;
        this.applicationGuardianSignedYn = applicationGuardianSignedYn;
        this.applicationPaymentId = applicationPaymentId;
        this.applicationIncompleteReason = applicationIncompleteReason;
        this.applicationValidateUsername = applicationValidateUsername;
        this.applicationDocumentGuid = applicationDocumentGuid;
        this.applPartyId = applPartyId;
        this.applSurname = applSurname;
        this.applFirstName = applFirstName;
        this.applSecondName = applSecondName;
        this.applBirthDate = applBirthDate;
        this.applGender = applGender;
        this.applBirthPlace = applBirthPlace;
        this.applAddlSurname1 = applAddlSurname1;
        this.applAddlFirstName1 = applAddlFirstName1;
        this.applAddlSecondName1 = applAddlSecondName1;
        this.applAddlSurname2 = applAddlSurname2;
        this.applAddlFirstName2 = applAddlFirstName2;
        this.applAddlSecondName2 = applAddlSecondName2;
        this.applAddlSurname3 = applAddlSurname3;
        this.applAddlFirstName3 = applAddlFirstName3;
        this.applAddlSecondName3 = applAddlSecondName3;
        this.applStreetAddress = applStreetAddress;
        this.applCity = applCity;
        this.applProvince = applProvince;
        this.applCountry = applCountry;
        this.applPostalCode = applPostalCode;
        this.applDriversLicence = applDriversLicence;
        this.applPhoneNumber = applPhoneNumber;
        this.applEmailAddress = applEmailAddress;
        this.applOrgPartyId = applOrgPartyId;
        this.applOrgFacilityPartyId = applOrgFacilityPartyId;
        this.applOrgFacilityName = applOrgFacilityName;
        this.applOrgContactPartyId = applOrgContactPartyId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public String getJurisdictionType() {
        return jurisdictionType;
    }

    public String getProcessingStream() {
        return processingStream;
    }

    public String getApplicationCategory() {
        return applicationCategory;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getNonFinRejectReason() {
        return nonFinRejectReason;
    }

    public String getApplicationSignedYn() {
        return applicationSignedYn;
    }

    public String getApplicationSignedDate() {
        return applicationSignedDate;
    }

    public String getApplicationGuardianSignedYn() {
        return applicationGuardianSignedYn;
    }

    public String getApplicationPaymentId() {
        return applicationPaymentId;
    }

    public String getApplicationIncompleteReason() {
        return applicationIncompleteReason;
    }

    public String getApplicationValidateUsername() {
        return applicationValidateUsername;
    }

    public String getApplicationDocumentGuid() {
        return applicationDocumentGuid;
    }

    public String getApplPartyId() {
        return applPartyId;
    }

    public String getApplSurname() {
        return applSurname;
    }

    public String getApplFirstName() {
        return applFirstName;
    }

    public String getApplSecondName() {
        return applSecondName;
    }

    public String getApplBirthDate() {
        return applBirthDate;
    }

    public String getApplGender() {
        return applGender;
    }

    public String getApplBirthPlace() {
        return applBirthPlace;
    }

    public String getApplAddlSurname1() {
        return applAddlSurname1;
    }

    public String getApplAddlFirstName1() {
        return applAddlFirstName1;
    }

    public String getApplAddlSecondName1() {
        return applAddlSecondName1;
    }

    public String getApplAddlSurname2() {
        return applAddlSurname2;
    }

    public String getApplAddlFirstName2() {
        return applAddlFirstName2;
    }

    public String getApplAddlSecondName2() {
        return applAddlSecondName2;
    }

    public String getApplAddlSurname3() {
        return applAddlSurname3;
    }

    public String getApplAddlFirstName3() {
        return applAddlFirstName3;
    }

    public String getApplAddlSecondName3() {
        return applAddlSecondName3;
    }

    public String getApplStreetAddress() {
        return applStreetAddress;
    }

    public String getApplCity() {
        return applCity;
    }

    public String getApplProvince() {
        return applProvince;
    }

    public String getApplCountry() {
        return applCountry;
    }

    public String getApplPostalCode() {
        return applPostalCode;
    }

    public String getApplDriversLicence() {
        return applDriversLicence;
    }

    public String getApplPhoneNumber() {
        return applPhoneNumber;
    }

    public String getApplEmailAddress() {
        return applEmailAddress;
    }

    public String getApplOrgPartyId() {
        return applOrgPartyId;
    }

    public String getApplOrgFacilityPartyId() {
        return applOrgFacilityPartyId;
    }

    public String getApplOrgFacilityName() {
        return applOrgFacilityName;
    }

    public String getApplOrgContactPartyId() {
        return applOrgContactPartyId;
    }
}
