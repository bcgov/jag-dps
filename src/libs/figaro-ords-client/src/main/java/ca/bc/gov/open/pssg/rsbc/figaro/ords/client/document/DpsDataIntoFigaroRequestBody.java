package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

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

    public static class Builder {

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

        public Builder withScheduleType(String scheduleType) {
            this.scheduleType = scheduleType;
            return this;
        }

        public Builder withJurisdictionType(String jurisdictionType) {
            this.jurisdictionType = jurisdictionType;
            return this; }

        public Builder withProcessingStream(String processingStream) {
            this.processingStream = processingStream;
            return this; }


        public Builder withApplicationCategory(String applicationCategory) {
            this.applicationCategory = applicationCategory;
            return this;
        }

        public Builder withPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder withNonFinRejectReason(String nonFinRejectReason) {
            this.nonFinRejectReason = nonFinRejectReason;
            return this;
        }

        public Builder withApplicationSignedYn(String applicationSignedYn) {
            this.applicationSignedYn = applicationSignedYn;
            return this;
        }

        public Builder withApplicationSignedDate(String applicationSignedDate) {
            this.applicationSignedDate = applicationSignedDate;
            return this;
        }

        public Builder withApplicationGuardianSignedYn(String applicationGuardianSignedYn) {
            this.applicationGuardianSignedYn = applicationGuardianSignedYn;
            return this;
        }

        public Builder withApplicationPaymentId(String applicationPaymentId) {
            this.applicationPaymentId = applicationPaymentId;
            return this;
        }

        public Builder withApplicationIncompleteReason(String applicationIncompleteReason) {
            this.applicationIncompleteReason = applicationIncompleteReason;
            return this;
        }

        public Builder withApplicationValidateUsername(String applicationValidateUsername) {
            this.applicationValidateUsername = applicationValidateUsername;
            return this;
        }

        public Builder withApplicationDocumentGuid(String applicationDocumentGuid) {
            this.applicationDocumentGuid = applicationDocumentGuid;
            return this;
        }

        public Builder withApplPartyId(String applPartyId) {
            this.applPartyId = applPartyId;
            return this;
        }

        public Builder withApplSurname(String applSurname) {
            this.applSurname = applSurname;
            return this;
        }

        public Builder withApplFirstName(String applFirstName) {
            this.applFirstName = applFirstName;
            return this;
        }

        public Builder withApplSecondName(String applSecondName) {
            this.applSecondName = applSecondName;
            return this;
        }

        public Builder withApplBirthDate(String applBirthDate) {
            this.applBirthDate = applBirthDate;
            return this;
        }

        public Builder withApplGender(String applGender) {
            this.applGender = applGender;
            return this;
        }

        public Builder withApplBirthPlace(String applBirthPlace) {
            this.applBirthPlace = applBirthPlace;
            return this;
        }

        public Builder withApplAddlSurname1(String applAddlSurname1) {
            this.applAddlSurname1 = applAddlSurname1;
            return this;
        }

        public Builder withApplAddlFirstName1(String applAddlFirstName1) {
            this.applAddlFirstName1 = applAddlFirstName1;
            return this;
        }

        public Builder withApplAddlSecondName1(String applAddlSecondName1) {
            this.applAddlSecondName1 = applAddlSecondName1;
            return this;
        }

        public Builder withApplAddlSurname2(String applAddlSurname2) {
            this.applAddlSurname2 = applAddlSurname2;
            return this;
        }

        public Builder withApplAddlFirstName2(String applAddlFirstName2) {
            this.applAddlFirstName2 = applAddlFirstName2;
            return this;
        }

        public Builder withApplAddlSecondName2(String applAddlSecondName2) {
            this.applAddlSecondName2 = applAddlSecondName2;
            return this;
        }

        public Builder withApplAddlSurname3(String applAddlSurname3) {
            this.applAddlSurname3 = applAddlSurname3;
            return this;
        }

        public Builder withApplAddlFirstName3(String applAddlFirstName3) {
            this.applAddlFirstName3 = applAddlFirstName3;
            return this;
        }

        public Builder withApplAddlSecondName3(String applAddlSecondName3) {
            this.applAddlSecondName3 = applAddlSecondName3;
            return this;
        }

        public Builder withApplStreetAddress(String applStreetAddress) {
            this.applStreetAddress = applStreetAddress;
            return this;
        }

        public Builder withApplCity(String applCity) {
            this.applCity = applCity;
            return this;
        }

        public Builder withApplProvince(String applProvince) {
            this.applProvince = applProvince;
            return this;
        }

        public Builder withApplCountry(String applCountry) {
            this.applCountry = applCountry;
            return this;
        }

        public Builder withApplPostalCode(String applPostalCode) {
            this.applPostalCode = applPostalCode;
            return this;
        }

        public Builder withApplDriversLicence(String applDriversLicence) {
            this.applDriversLicence = applDriversLicence;
            return this;
        }

        public Builder withApplPhoneNumber(String applPhoneNumber) {
            this.applPhoneNumber = applPhoneNumber;
            return this;
        }

        public Builder withApplEmailAddress(String applEmailAddress) {
            this.applEmailAddress = applEmailAddress;
            return this;
        }

        public Builder withApplOrgPartyId(String applOrgPartyId) {
            this.applOrgPartyId = applOrgPartyId;
            return this;
        }

        public Builder withApplOrgFacilityPartyId(String applOrgFacilityPartyId) {
            this.applOrgFacilityPartyId = applOrgFacilityPartyId;
            return this;
        }

        public Builder withApplOrgFacilityName(String applOrgFacilityName) {
            this.applOrgFacilityName = applOrgFacilityName;
            return this;
        }

        public Builder withApplOrgContactPartyId(String applOrgContactPartyId) {
            this.applOrgContactPartyId = applOrgContactPartyId;
            return this;
        }

        public DpsDataIntoFigaroRequestBody build() {
            DpsDataIntoFigaroRequestBody dpsDataIntoFigaroRequestBody = new DpsDataIntoFigaroRequestBody();


            dpsDataIntoFigaroRequestBody.scheduleType = this.scheduleType;
            dpsDataIntoFigaroRequestBody.jurisdictionType = this.jurisdictionType;
            dpsDataIntoFigaroRequestBody.processingStream = this.processingStream;
            dpsDataIntoFigaroRequestBody.applicationCategory = this.applicationCategory;
            dpsDataIntoFigaroRequestBody.paymentMethod = this.paymentMethod;
            dpsDataIntoFigaroRequestBody.nonFinRejectReason = this.nonFinRejectReason;
            dpsDataIntoFigaroRequestBody.applicationSignedYn = this.applicationSignedYn;
            dpsDataIntoFigaroRequestBody.applicationSignedDate = this.applicationSignedDate;
            dpsDataIntoFigaroRequestBody.applicationGuardianSignedYn = this.applicationGuardianSignedYn;
            dpsDataIntoFigaroRequestBody.applicationPaymentId = this.applicationPaymentId;
            dpsDataIntoFigaroRequestBody.applicationIncompleteReason = this.applicationIncompleteReason;
            dpsDataIntoFigaroRequestBody.applicationValidateUsername = this.applicationValidateUsername;
            dpsDataIntoFigaroRequestBody.applicationDocumentGuid = this.applicationDocumentGuid;
            dpsDataIntoFigaroRequestBody.applPartyId = this.applPartyId;
            dpsDataIntoFigaroRequestBody.applSurname = this.applSurname;
            dpsDataIntoFigaroRequestBody.applFirstName = this.applFirstName;
            dpsDataIntoFigaroRequestBody.applSecondName = this.applSecondName;
            dpsDataIntoFigaroRequestBody.applBirthDate = this.applBirthDate;
            dpsDataIntoFigaroRequestBody.applGender = this.applGender;
            dpsDataIntoFigaroRequestBody.applBirthPlace = this.applBirthPlace;
            dpsDataIntoFigaroRequestBody.applAddlSurname1 = this.applAddlSurname1;
            dpsDataIntoFigaroRequestBody.applAddlFirstName1 = this.applAddlFirstName1;
            dpsDataIntoFigaroRequestBody.applAddlSecondName1 = this.applAddlSecondName1;
            dpsDataIntoFigaroRequestBody.applAddlSurname2 = this.applAddlSurname2;
            dpsDataIntoFigaroRequestBody.applAddlFirstName2 = this.applAddlFirstName2;
            dpsDataIntoFigaroRequestBody.applAddlSecondName2 = this.applAddlSecondName2;
            dpsDataIntoFigaroRequestBody.applAddlSurname3 = this.applAddlSurname3;
            dpsDataIntoFigaroRequestBody.applAddlFirstName3 = this.applAddlFirstName3;
            dpsDataIntoFigaroRequestBody.applAddlSecondName3 = this.applAddlSecondName3;
            dpsDataIntoFigaroRequestBody.applStreetAddress = this.applStreetAddress;
            dpsDataIntoFigaroRequestBody.applCity = this.applCity;
            dpsDataIntoFigaroRequestBody.applProvince = this.applProvince;
            dpsDataIntoFigaroRequestBody.applCountry = this.applCountry;
            dpsDataIntoFigaroRequestBody.applPostalCode = this.applPostalCode;
            dpsDataIntoFigaroRequestBody.applDriversLicence = this.applDriversLicence;
            dpsDataIntoFigaroRequestBody.applPhoneNumber = this.applPhoneNumber;
            dpsDataIntoFigaroRequestBody.applEmailAddress = this.applEmailAddress;
            dpsDataIntoFigaroRequestBody.applOrgPartyId = this.applOrgPartyId;
            dpsDataIntoFigaroRequestBody.applOrgFacilityPartyId = this.applOrgFacilityPartyId;
            dpsDataIntoFigaroRequestBody.applOrgFacilityName = this.applOrgFacilityName;
            dpsDataIntoFigaroRequestBody.applOrgContactPartyId = this.applOrgContactPartyId;

            return dpsDataIntoFigaroRequestBody;

        }

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
