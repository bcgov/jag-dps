package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

/**
 * Collection of validation services for orgs.
 *
 * @author carolcarpenterjustice
 */
public interface OrganizationService {

    ValidateOrgDrawDownBalanceResponse validateOrgDrawDownBalance(ValidateOrgDrawDownBalanceRequest request);

    ValidateOrgPartyResponse validateOrgParty(ValidateOrgPartyRequest request);
}