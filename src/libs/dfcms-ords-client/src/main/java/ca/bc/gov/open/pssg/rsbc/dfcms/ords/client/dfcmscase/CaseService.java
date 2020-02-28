package ca.bc.gov.open.pssg.rsbc.dfcms.ords.client.dfcmscase;

/**
 * Collection of services for dfcms cases.
 *
 * @author carolcarpenterjustice
 */
public interface CaseService {

     CaseSequenceNumberResponse caseSequenceNumber(String driverLicenseNo, String surnameCode);

}
