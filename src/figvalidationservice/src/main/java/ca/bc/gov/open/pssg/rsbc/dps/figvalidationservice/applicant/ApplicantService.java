package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant;

import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;

public interface ApplicantService {

    ValidateApplicantForSharingOrdsResponse validateApplicantForSharing(ValidateApplicantForSharingRequest validateApplicantForSharingRequest)
            throws FigaroValidationServiceException;
}
