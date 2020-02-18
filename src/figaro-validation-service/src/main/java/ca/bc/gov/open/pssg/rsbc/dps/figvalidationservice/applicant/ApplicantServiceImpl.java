package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.MatchingApplicantsOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.LocateMatchingApplicantsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
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
     * @param validateApplicantForSharingRequest
     * @return
     * @throws FigaroValidationServiceException
     */
    @Override
    public ValidateApplicantForSharingOrdsResponse validateApplicantForSharing(
            ValidateApplicantForSharingRequest validateApplicantForSharingRequest)
            throws FigaroValidationServiceException {

        try {
            return applicantApi.validateApplicantForSharing(validateApplicantForSharingRequest.getApplPartyId(), validateApplicantForSharingRequest.getJurisdictionType());
        } catch (ApiException ex) {
            logger.error("Exception caught as Figaro Validator Service, ValidatePartyId : " + ex.getMessage());
            ex.printStackTrace();
            throw new FigaroValidationServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public ValidateApplicantPartyIdOrdsResponse validateApplicantPartyId(String applPartyId)
            throws FigaroValidationServiceException {

        try {
            return applicantApi.validateApplicantPartyId(applPartyId);
        } catch (ApiException ex) {
            logger.error("Exception caught as Figaro Validator Service, ValidatePartyId : " + ex.getMessage());
            ex.printStackTrace();
            throw new FigaroValidationServiceException(ex.getMessage(), ex);
        }

    }


    @Override
    public LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr) {

        try {

            MatchingApplicantsOrdsResponse response = applicantApi.matchingApplicants(
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

            return LocateMatchingApplicantsResponse.SuccessResponse(
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
            logger.error("ORDS client exception: " + ex.getMessage());
            ex.printStackTrace();
            return LocateMatchingApplicantsResponse.ErrorResponse();
        }

    }

    @Override
    /*
     * service method to get the response for /validateOrgApplicantServiceOrdsResponse requests
     */
    public ValidateApplicantServiceOrdsResponse validateApplicantService(String applPartyId,
                                                                                     String orgPartyId) throws FigaroValidationServiceException {

        try {
            return applicantApi.validateOrgApplicantService(applPartyId, orgPartyId);
        } catch (ApiException ex) {
            logger.error("An exception occurred while trying to invoke ORDS method validateOrgApplicantServiceOrdsResponse()  : "
                    + ex.getMessage());
            ex.printStackTrace();
            throw new FigaroValidationServiceException(ex.getMessage(), ex);
        }

    }

}
