package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.ApplicantApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applicant Service Implementation using ORDS api
 *
 * @author archanasudha
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
}
