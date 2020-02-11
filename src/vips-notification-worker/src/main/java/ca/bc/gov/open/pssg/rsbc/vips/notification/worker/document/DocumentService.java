package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.document;

/**
 * Collection of services for documents.
 *
 * @author carolcarpenterjustice
 */
public interface DocumentService {

     VipsDocumentResponse vipsDocument(String typeCode, String metadata, String mimeType, String mimeSubType, String authGuid, byte[] body);

}
