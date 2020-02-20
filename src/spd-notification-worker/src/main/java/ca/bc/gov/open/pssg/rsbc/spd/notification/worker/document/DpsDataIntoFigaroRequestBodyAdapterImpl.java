package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

public class DpsDataIntoFigaroRequestBodyAdapterImpl implements DpsDataIntoFigaroRequestBodyAdapter {

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

        public DpsDataIntoFigaroRequestBodyAdapterImpl build() {
            DpsDataIntoFigaroRequestBodyAdapterImpl result = new DpsDataIntoFigaroRequestBodyAdapterImpl();

            result.scheduleType = this.scheduleType;
            result.jurisdictionType = this.jurisdictionType;
            result.processingStream = this.processingStream;
            result.applicationCategory = this.applicationCategory;
            result.paymentMethod = this.paymentMethod;
            result.nonFinRejectReason = this.nonFinRejectReason;
            result.applicationSignedYn = this.applicationSignedYn;
            result.applicationSignedDate = this.applicationSignedDate;
            result.applicationGuardianSignedYn = this.applicationGuardianSignedYn;
            result.applicationPaymentId = this.applicationPaymentId;
            result.applicationIncompleteReason = this.applicationIncompleteReason;
            result.applicationValidateUsername = this.applicationValidateUsername;
            result.applicationDocumentGuid = this.applicationDocumentGuid;
            result.applPartyId = this.applPartyId;
            result.applSurname = this.applSurname;
            result.applFirstName = this.applFirstName;
            result.applSecondName = this.applSecondName;
            result.applBirthDate = this.applBirthDate;
            result.applGender = this.applGender;
            result.applBirthPlace = this.applBirthPlace;
            result.applAddlSurname1 = this.applAddlSurname1;
            result.applAddlFirstName1 = this.applAddlFirstName1;
            result.applAddlSecondName1 = this.applAddlSecondName1;
            result.applAddlSurname2 = this.applAddlSurname2;
            result.applAddlFirstName2 = this.applAddlFirstName2;
            result.applAddlSecondName2 = this.applAddlSecondName2;
            result.applAddlSurname3 = this.applAddlSurname3;
            result.applAddlFirstName3 = this.applAddlFirstName3;
            result.applAddlSecondName3 = this.applAddlSecondName3;
            result.applStreetAddress = this.applStreetAddress;
            result.applCity = this.applCity;
            result.applProvince = this.applProvince;
            result.applCountry = this.applCountry;
            result.applPostalCode = this.applPostalCode;
            result.applDriversLicence = this.applDriversLicence;
            result.applPhoneNumber = this.applPhoneNumber;
            result.applEmailAddress = this.applEmailAddress;
            result.applOrgPartyId = this.applOrgPartyId;
            result.applOrgFacilityPartyId = this.applOrgFacilityPartyId;
            result.applOrgFacilityName = this.applOrgFacilityName;
            result.applOrgContactPartyId = this.applOrgContactPartyId;

            return result;
        }
    }

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

    @Override
    public String getScheduleType() {
        return this.scheduleType;
    }

    @Override
    public String getJurisdictionType() {
        return this.jurisdictionType;
    }

    @Override
    public String getProcessingStream() {
        return this.processingStream;
    }

    @Override
    public String getApplicationCategory() {
        return this.applicationCategory;
    }

    @Override
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    @Override
    public String getNonFinRejectReason() {
        return this.nonFinRejectReason;
    }

    @Override
    public String getApplicationSignedYn() {
        return this.applicationSignedYn;
    }

    @Override
    public String getApplicationSignedDate() {
        return this.applicationSignedDate;
    }

    @Override
    public String getApplicationGuardianSignedYn() {
        return this.applicationGuardianSignedYn;
    }

    @Override
    public String getApplicationPaymentId() {
        return this.applicationPaymentId;
    }

    @Override
    public String getApplicationIncompleteReason() {
        return this.applicationIncompleteReason;
    }

    @Override
    public String getApplicationValidateUsername() {
        return this.applicationValidateUsername;
    }

    @Override
    public String getApplicationDocumentGuid() {
        return this.applicationDocumentGuid;
    }

    @Override
    public String getApplPartyId() {
        return this.applPartyId;
    }

    @Override
    public String getApplSurname() {
        return this.applSurname;
    }

    @Override
    public String getApplFirstName() {
        return this.applFirstName;
    }

    @Override
    public String getApplSecondName() {
        return this.applSecondName;
    }

    @Override
    public String getApplBirthDate() {
        return this.applBirthDate;
    }

    @Override
    public String getApplGender() {
        return this.applGender;
    }

    @Override
    public String getApplBirthPlace() {
        return this.applBirthPlace;
    }

    @Override
    public String getApplAddlSurname1() {
        return this.applAddlSurname1;
    }

    @Override
    public String getApplAddlFirstName1() {
        return this.applAddlFirstName1;
    }

    @Override
    public String getApplAddlSecondName1() {
        return this.applAddlSecondName1;
    }

    @Override
    public String getApplAddlSurname2() {
        return this.applAddlSurname2;
    }

    @Override
    public String getApplAddlFirstName2() {
        return this.applAddlFirstName2;
    }

    @Override
    public String getApplAddlSecondName2() {
        return this.applAddlSecondName2;
    }

    @Override
    public String getApplAddlSurname3() {
        return this.applAddlSurname3;
    }

    @Override
    public String getApplAddlFirstName3() {
        return this.applAddlFirstName3;
    }

    @Override
    public String getApplAddlSecondName3() {
        return this.applAddlSecondName3;
    }

    @Override
    public String getApplStreetAddress() {
        return this.applStreetAddress;
    }

    @Override
    public String getApplCity() {
        return this.applCity;
    }

    @Override
    public String getApplProvince() {
        return this.applProvince;
    }

    @Override
    public String getApplCountry() {
        return this.applCountry;
    }

    @Override
    public String getApplPostalCode() {
        return this.applPostalCode;
    }

    @Override
    public String getApplDriversLicence() {
        return this.applDriversLicence;
    }

    @Override
    public String getApplPhoneNumber() {
        return this.applPhoneNumber;
    }

    @Override
    public String getApplEmailAddress() {
        return this.applEmailAddress;
    }

    @Override
    public String getApplOrgPartyId() {
        return this.applOrgPartyId;
    }

    @Override
    public String getApplOrgFacilityPartyId() {
        return this.applOrgFacilityPartyId;
    }

    @Override
    public String getApplOrgFacilityName() {
        return this.applOrgFacilityName;
    }

    @Override
    public String getApplOrgContactPartyId() {
        return this.applOrgContactPartyId;
    }

}
