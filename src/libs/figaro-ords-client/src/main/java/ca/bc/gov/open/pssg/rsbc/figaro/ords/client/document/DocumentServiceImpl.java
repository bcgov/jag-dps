package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

import ca.bc.gov.open.ords.figcr.client.api.DocumentApi;
import ca.bc.gov.open.ords.figcr.client.api.handler.ApiException;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsRequestBody;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDataIntoFigaroOrdsResponse;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDocumentOrdsRequestBody;
import ca.bc.gov.open.ords.figcr.client.api.model.DpsDocumentOrdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Document Service Implementation using ORDS services.
 *
 * @author carolcarpenterjustice
 */
public class DocumentServiceImpl implements DocumentService {

    private final DocumentApi documentApi;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DocumentServiceImpl(DocumentApi documentApi) {
        this.documentApi = documentApi;
    }

    @Override
    public DpsDataIntoFigaroResponse dpsDataIntoFigaro(DpsDataIntoFigaroRequestBody request) {

        DpsDataIntoFigaroOrdsRequestBody ordsRequestBody = new DpsDataIntoFigaroOrdsRequestBody();
        ordsRequestBody.setScheduleType(request.getScheduleType());
        ordsRequestBody.setJurisdictionType(request.getJurisdictionType());
        ordsRequestBody.setProcessingStream(request.getProcessingStream());
        ordsRequestBody.setApplicationCategory(request.getApplicationCategory());
        ordsRequestBody.setPaymentMethod(request.getPaymentMethod());
        ordsRequestBody.setNonFinRejectReason(request.getNonFinRejectReason());
        ordsRequestBody.setApplicationSignedYn(request.getApplicationSignedYn());
        ordsRequestBody.setApplicationSignedDate(request.getApplicationSignedDate());
        ordsRequestBody.setApplicationGuardianSignedYn(request.getApplicationGuardianSignedYn());
        ordsRequestBody.setApplicationPaymentId(request.getApplicationPaymentId());
        ordsRequestBody.setApplicationIncompleteReason(request.getApplicationIncompleteReason());
        ordsRequestBody.setApplicationValidateUsername(request.getApplicationValidateUsername());
        ordsRequestBody.setApplicationDocumentGuid(request.getApplicationDocumentGuid());
        ordsRequestBody.setApplPartyId(request.getApplPartyId());
        ordsRequestBody.setApplSurname(request.getApplSurname());
        ordsRequestBody.setApplFirstName(request.getApplFirstName());
        ordsRequestBody.setApplSecondName(request.getApplSecondName());
        ordsRequestBody.setApplBirthDate(request.getApplBirthDate());
        ordsRequestBody.setApplGender(request.getApplGender());
        ordsRequestBody.setApplBirthPlace(request.getApplBirthPlace());
        ordsRequestBody.setApplAddlSurname1(request.getApplAddlSurname1());
        ordsRequestBody.setApplAddlFirstName1(request.getApplAddlFirstName1());
        ordsRequestBody.setApplAddlSecondName1(request.getApplAddlSecondName1());
        ordsRequestBody.setApplAddlSurname2(request.getApplAddlSurname2());
        ordsRequestBody.setApplAddlFirstName2(request.getApplAddlFirstName2());
        ordsRequestBody.setApplAddlSecondName2(request.getApplAddlSecondName2());
        ordsRequestBody.setApplAddlSurname3(request.getApplAddlSurname3());
        ordsRequestBody.setApplAddlFirstName3(request.getApplAddlFirstName3());
        ordsRequestBody.setApplAddlSecondName3(request.getApplAddlSecondName3());
        ordsRequestBody.setApplStreetAddress(request.getApplStreetAddress());
        ordsRequestBody.setApplCity(request.getApplCity());
        ordsRequestBody.setApplProvince(request.getApplProvince());
        ordsRequestBody.setApplCountry(request.getApplCountry());
        ordsRequestBody.setApplPostalCode(request.getApplPostalCode());
        ordsRequestBody.setApplDriversLicence(request.getApplDriversLicence());
        ordsRequestBody.setApplPhoneNumber(request.getApplPhoneNumber());
        ordsRequestBody.setApplEmailAddress(request.getApplEmailAddress());
        ordsRequestBody.setApplOrgPartyId(request.getApplOrgPartyId());
        ordsRequestBody.setApplOrgFacilityPartyId(request.getApplOrgFacilityPartyId());
        ordsRequestBody.setApplOrgFacilityName(request.getApplOrgFacilityName());
        ordsRequestBody.setApplOrgContactPartyId(request.getApplOrgContactPartyId());

        try {
            DpsDataIntoFigaroOrdsResponse response = this.documentApi.dpsDataIntoFigaroPost(ordsRequestBody);
            return DpsDataIntoFigaroResponse.SuccessResponse(response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as Document Service, dpsDataIntoFigaro : " + ex.getMessage(), ex);
            return DpsDataIntoFigaroResponse.ErrorResponse(ex.getMessage());
        }
    }

    @Override
    public DpsDocumentResponse dpsDocument(DpsDocumentRequestBody request) {

        DpsDocumentOrdsRequestBody ordsRequestBody = new DpsDocumentOrdsRequestBody();
        ordsRequestBody.setServerName(request.getServerName());
        ordsRequestBody.setFileName(request.getFileName());

        try {
            DpsDocumentOrdsResponse response = this.documentApi.dpsDocumentPost(ordsRequestBody);
            return DpsDocumentResponse.SuccessResponse(response.getGuid(), response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {
            logger.error("Exception caught as Document Service, dpsDocument : " + ex.getMessage(), ex);
            return DpsDocumentResponse.ErrorResponse(ex.getMessage());
        }
    }

}
