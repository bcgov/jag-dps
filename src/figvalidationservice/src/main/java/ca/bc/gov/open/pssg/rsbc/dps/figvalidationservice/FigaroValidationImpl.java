package ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice;

import ca.bc.gov.open.ords.figcr.client.api.FigvalidationsApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantForSharingOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantPartyIdOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.ValidateApplicantServiceOrdsResponse;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.applicant.types.ValidateApplicantForSharingRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.exception.FigaroValidationServiceException;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsRequest;
import ca.bc.gov.open.pssg.rsbc.dps.figvalidationservice.types.LocateMatchingApplicantsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FigaroValidationImpl class
 * <p>
 * Note: Calls ORDS clients
 *
 * @author shaunmillargov
 */
@Service
public class FigaroValidationImpl implements FigaroValidation {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FigvalidationsApi ordsapi;

    @Override
    public LocateMatchingApplicantsResponse locateMatchingApplicants(LocateMatchingApplicantsRequest lmr)
            throws FigaroValidationServiceException {

        // TODO - replace the following dummy response with a call to ORDS

        // Note: Call response code and response message comes from the ORDS call.

        LocateMatchingApplicantsResponse resp = new LocateMatchingApplicantsResponse(
                "Success - found Party Id 209",
                1,
                "209",
                "MOUSE",
                "MINNIE",
                null,
                "1950-01-10",
                "999999",
                "DISNEY",
                "F");

        return resp;

    }
  
    @Override
    /*
     * service method to get the response for /validateOrgApplicantServiceOrdsResponse requests
     */
	public ValidateApplicantServiceOrdsResponse validateApplicantServiceOrdsResponse(String applPartyId,
			String orgPartyId) throws FigaroValidationServiceException {

		try {
			return ordsapi.validateOrgApplicantService(applPartyId, orgPartyId);
		} catch (ApiException ex) {
			logger.error("An exception occurred while trying to invoke ORDS method validateOrgApplicantServiceOrdsResponse()  : "
					+ ex.getMessage());
			ex.printStackTrace();
			throw new FigaroValidationServiceException(ex.getMessage(), ex);
		}

	}

}

