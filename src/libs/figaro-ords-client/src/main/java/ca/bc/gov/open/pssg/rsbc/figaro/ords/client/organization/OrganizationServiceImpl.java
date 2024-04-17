package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.organization;

import ca.bc.gov.open.ords.figcr.client.api.OrgApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgDrawDownBalanceOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgPartyOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgPartyOrdsResponseContactPersonsInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Organization Service Implementation using ORDS services.
 *
 * @author carolcarpenterjustice
 */
public class OrganizationServiceImpl implements OrganizationService {

    private final OrgApi orgApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public OrganizationServiceImpl(OrgApi orgApi) {
        this.orgApi = orgApi;
    }

    @Override
    public ValidateOrgDrawDownBalanceResponse validateOrgDrawDownBalance(ValidateOrgDrawDownBalanceRequest request) {

        try {
            ValidateOrgDrawDownBalanceOrdsResponse response =
                    this.orgApi.validateOrgDrawDownBalance(request.getJurisdictionType(), request.getOrgPartyId(),
                            request.getScheduleType());
            return ValidateOrgDrawDownBalanceResponse.successResponse(response.getValidationResult(),
                    response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as Organization Service, validateOrgDrawDownBalance : " + ex.getMessage(), ex);
            return ValidateOrgDrawDownBalanceResponse.errorResponse(ex.getMessage());
        }
    }

    @Override
    public ValidateOrgPartyResponse validateOrgParty(ValidateOrgPartyRequest request) {

        try {
            ValidateOrgPartyOrdsResponse response = this.orgApi.validateOrgParty(request.getOrgCity(),
                    request.getOrgPartyId(), request.getOrgSubname1(), request.getOrgSubname2(),
                    request.getOrgSubname3(), request.getOrgSubname4(), request.getOrgSubname5());
            List<ValidateOrgPartyOrdsResponseContactPersonsInner> contactOrdsList = response.getContactPersons();

            List<ValidateOrgPartyContactPersonResponse> contactList =
                    new ArrayList<>();

            if (contactOrdsList != null) {
                for (ValidateOrgPartyOrdsResponseContactPersonsInner contact : contactOrdsList) {
                    contactList.add(ValidateOrgPartyContactPersonResponse.successResponse(contact.getContactPersonName(), contact.getContactPersonRole(), contact.getContactPersonPartyId()));
                }
            }

            return ValidateOrgPartyResponse.successResponse(response.getValidationResult(), response.getStatusCode(),
                    response.getStatusMessage(), response.getFoundOrgPartyId(), response.getFoundOrgName(),
                    response.getFoundOrgType(), contactList);

        } catch (ApiException ex) {
            logger.error("Exception caught as Organization Service, validateOrgParty : " + ex.getMessage(), ex);
            return ValidateOrgPartyResponse.errorResponse(ex.getMessage());
        }
    }
}
