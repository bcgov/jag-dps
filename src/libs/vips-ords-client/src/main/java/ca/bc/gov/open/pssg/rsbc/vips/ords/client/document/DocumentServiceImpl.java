package ca.bc.gov.open.pssg.rsbc.vips.ords.client.document;

import ca.bc.gov.open.ords.vips.client.api.DocumentApi;
import ca.bc.gov.open.ords.vips.client.api.handler.ApiException;
import ca.bc.gov.open.ords.vips.client.api.model.VipsDocumentOrdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


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
    public VipsDocumentResponse vipsDocument(String typeCode, String metadata, String mimeType, String mimeSubType, String authGuid, File body) {

        try {
            VipsDocumentOrdsResponse response = this.documentApi.vipsDocumentPost(typeCode, SanitizeBase64(metadata), mimeType, mimeSubType, authGuid, body);
            return  VipsDocumentResponse.successResponse(response.getDocumentId(), response.getStatusCode(), response.getStatusMessage());

        } catch (ApiException ex) {

            logger.error("Document Service did throw exception: " + ex.getMessage(), ex);
            return VipsDocumentResponse.errorResponse(ex.getMessage());
        }
    }

    private String SanitizeBase64(String value) {
        return value
                .replace('/', '_')
                .replace('+', '-')
                .replaceAll("\r\n", "");
    }

}
