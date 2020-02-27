package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.*;

public interface ApplicantService {

    ValidateApplicantForSharingResponse validateApplicantForSharing(ValidateApplicantForSharingRequest validateApplicantForSharingRequest)
            throws ApiException;

    ValidateApplicantPartyIdResponse validateApplicantPartyId(String applParyId)
            throws ApiException;

    LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr);

    ValidateOrgApplicantServiceResponse validateOrgApplicantService(String applPartyId, String orgPartyId)
            throws ApiException;
}
