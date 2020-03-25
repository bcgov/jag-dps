package ca.bc.gov.open.pssg.rsbc;

public class DpsFileInfo {

    private String Id;
    private String Name;
    private String contentType;

    public DpsFileInfo(String id, String name, String contentType) {
        Id = id;
        Name = name;
        this.contentType = contentType;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getContentType() {
        return contentType;
    }
}
