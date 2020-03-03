package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.MatchingApplicantsOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateOrgApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applicant Service Implementation using ORDS api
 *
 * @author archanasudha
 * @author shaunmillargov
 * @author alexjoybc
 *
 */
public class ApplicantServiceImpl implements ApplicantService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicantApi applicantApi;

    public ApplicantServiceImpl(ApplicantApi applicantApi) {
        this.applicantApi = applicantApi;
    }

    /**
     *
     * service method to get the response for /validateApplicantForSharing requests
     *
     * @param request
     * @return
     * @throws ApiException
     */
    @Override
    public ValidateApplicantForSharingResponse validateApplicantForSharing(ValidateApplicantForSharingRequest request) {

        try {
            ValidateApplicantForSharingOrdsResponse response = this.applicantApi.validateApplicantForSharing(
                    request.getApplPartyId(), request.getJurisdictionType());
            return ValidateApplicantForSharingResponse.successResponse(response.getValidationResult(),
                    response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as Applicant Service, validateApplicantForSharing : " + ex.getMessage(), ex);
            return ValidateApplicantForSharingResponse.errorResponse(ex.getMessage());
        }
    }

    @Override
    public ValidateApplicantPartyIdResponse validateApplicantPartyId(String applPartyId) {

        try {
            ValidateApplicantPartyIdOrdsResponse response = this.applicantApi.validateApplicantPartyId(applPartyId);
            return ValidateApplicantPartyIdResponse.successResponse(response.getStatusCode(), response.getStatusMessage(),
                    response.getSurname(), response.getFirstName(), response.getSecondName(), response.getBirthDate(),
                    response.getDriversLicense(), response.getBirthPlace(), response.getGender());

        } catch (ApiException ex) {
            logger.error("Exception caught as Applicant Service, validateApplicantPartyId : " + ex.getMessage(), ex);
            return ValidateApplicantPartyIdResponse.errorResponse(ex.getMessage());
        }
    }

    @Override
    public LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr) {

        try {
            MatchingApplicantsOrdsResponse response = this.applicantApi.matchingApplicants(
                    lmr.getApplAliasFirstName1(),
                    lmr.getApplAliasFirstName2(),
                    lmr.getApplAliasFirstName3(),
                    lmr.getApplAliasSecondInitial1(),
                    lmr.getApplAliasSecondInitial2(),
                    lmr.getApplAliasSecondInitial3(),
                    lmr.getApplAliasSurname1(),
                    lmr.getApplAliasSurname2(),
                    lmr.getApplAliasSurname3(),
                    lmr.getApplBirthDate(),
                    lmr.getApplBirthPlace(),
                    lmr.getApplDriversLicence(),
                    lmr.getApplFirstName(),
                    lmr.getApplGenderTxt(),
                    lmr.getApplSecondInitial(),
                    lmr.getApplSurname());

            return LocateMatchingApplicantsResponse.successResponse(
                    response.getStatusCode(),
                    response.getStatusMessage(),
                    response.getFoundPartyId(),
                    response.getFoundSurname(),
                    response.getFoundFirstName(),
                    response.getFoundSecondName(),
                    response.getFoundBirthDate(),
                    response.getFoundDriversLicense(),
                    response.getFoundBirthPlace(),
                    response.getFoundGender());

        } catch (ApiException ex) {
            logger.error("Exception caught as Applicant Service, locateMatchingApplicants : " + ex.getMessage(), ex);
            return LocateMatchingApplicantsResponse.errorResponse(ex.getMessage());
        }
    }

    @Override
    /*
     * service method to get the response for /validateApplicantService requests
     */
    public ValidateOrgApplicantServiceResponse validateOrgApplicantService(String applPartyId,
                                                                     String orgPartyId) {

        try {
            ValidateOrgApplicantServiceOrdsResponse response = this.applicantApi.validateOrgApplicantService(applPartyId, orgPartyId);
            return ValidateOrgApplicantServiceResponse.successResponse(response.getValidationResult(), response.getStatusCode(), response.getStatusMessage());
        } catch (ApiException ex) {
            logger.error("Exception caught as Applicant Service, validateOrgApplicantService : " + ex.getMessage(), ex);
            return ValidateOrgApplicantServiceResponse.errorResponse(ex.getMessage());
        }
    }
}
