package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

/**
 * Collection of services for documents.
 *
 * @author carolcarpenterjustice
 */
public interface DocumentService {

    DpsDataIntoFigaroResponse dpsDataIntoFigaro(DpsDataIntoFigaroRequestBody request);

    DpsDocumentResponse dpsDocument(DpsDocumentRequestBody request);
}
