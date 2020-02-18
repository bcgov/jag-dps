package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.org;

/**
 * Collection of validation services for orgs.
 *
 * @author carolcarpenterjustice
 */
public interface OrgService {

    ValidateOrgDrawDownBalanceResponse validateOrgDrawDownBalance(ValidateOrgDrawDownBalanceRequest request);

    ValidateOrgPartyResponse validateOrgParty(ValidateOrgPartyRequest request);
}