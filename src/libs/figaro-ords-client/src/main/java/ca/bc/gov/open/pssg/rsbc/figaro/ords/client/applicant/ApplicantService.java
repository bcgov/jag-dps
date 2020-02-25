package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant;

import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.LocateMatchingApplicantsResponse;
import ca.bc.gov.open.pssg.rsbc.figaro.ords.client.applicant.types.ValidateApplicantForSharingRequest;

public interface ApplicantService {

    ValidateApplicantForSharingOrdsResponse validateApplicantForSharing(ValidateApplicantForSharingRequest validateApplicantForSharingRequest)
            throws ApiException;

    ValidateApplicantPartyIdOrdsResponse validateApplicantPartyId(String applParyId)
            throws ApiException;

    LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr);

    ValidateApplicantServiceOrdsResponse validateApplicantService(String applPartyId, String orgPartyId)
            throws ApiException;
}
