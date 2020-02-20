package ca.bc.gov.open.pssg.rsbc.spd.notification.worker.document;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DpsDocumentRequestBody {\n");

        sb.append("    serverName: ").append(serverName).append("\n");
        sb.append("    fileName: ").append(fileName).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
