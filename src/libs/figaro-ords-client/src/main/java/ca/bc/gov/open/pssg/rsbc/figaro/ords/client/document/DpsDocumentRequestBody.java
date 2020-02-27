package ca.bc.gov.open.pssg.rsbc.figaro.ords.client.document;

/**
 *  Represents the DPS Document Requests
 *
 * @author carolcarpenterjustice
 *
 */
public class DpsDocumentRequestBody {

    private String serverName;
    private String fileName;

    public DpsDocumentRequestBody(String serverName,
                                  String fileName) {
        this.serverName = serverName;
        this.fileName = fileName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getFileName() {
        return fileName;
    }
}
