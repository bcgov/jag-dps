package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document;

import ca.bc.gov.open.ords.vips.client.api.DocumentApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;
import ca.bc.gov.open.ords.vips.client.api.model.VipsDocumentOrdsResponse;
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
    public VipsDocumentResponse vipsDocument(String typeCode, String metadata, String mimeType, String mimeSubType, String authGuid, byte[] body) {

        try {
            VipsDocumentOrdsResponse response = this.documentApi.vipsDocumentPost(typeCode, metadata, mimeType, mimeSubType, authGuid, body);
            return  VipsDocumentResponse.SuccessResponse(response.getDocumentId(), response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {

            logger.error("Document Service did throw exception: " + ex.getMessage());
            ex.printStackTrace();

            return VipsDocumentResponse.ErrorResponse(ex.getMessage());
        }
    }

}
