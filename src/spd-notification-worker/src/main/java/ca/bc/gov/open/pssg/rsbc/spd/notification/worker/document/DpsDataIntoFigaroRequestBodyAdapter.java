package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

public interface DpsDataIntoFigaroRequestBodyAdapter {

    String getScheduleType();

    String getJurisdictionType();

    String getProcessingStream();

    String getApplicationCategory();

    String getPaymentMethod();

    String getNonFinRejectReason();

    String getApplicationSignedYn();

    String getApplicationSignedDate();

    String getApplicationGuardianSignedYn();

    String getApplicationPaymentId();

    String getApplicationIncompleteReason();

    String getApplicationValidateUsername();

    String getApplicationDocumentGuid();

    String getApplPartyId();

    String getApplSurname();

    String getApplFirstName();

    String getApplSecondName();

    String getApplBirthDate();

    String getApplGender();

    String getApplBirthPlace();

    String getApplAddlSurname1();

    String getApplAddlFirstName1();

    String getApplAddlSecondName1();

    String getApplAddlSurname2();

    String getApplAddlFirstName2();

    String getApplAddlSecondName2();

    String getApplAddlSurname3();

    String getApplAddlFirstName3();

    String getApplAddlSecondName3();

    String getApplStreetAddress();

    String getApplCity();

    String getApplProvince();

    String getApplCountry();

    String getApplPostalCode();

    String getApplDriversLicence();

    String getApplPhoneNumber();

    String getApplEmailAddress();

    String getApplOrgPartyId();

    String getApplOrgFacilityPartyId();

    String getApplOrgFacilityName();

    String getApplOrgContactPartyId();
}
