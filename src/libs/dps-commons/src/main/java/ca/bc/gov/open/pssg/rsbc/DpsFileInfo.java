package ca.bc.gov.open.pssg.rsbc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DpsFileInfo {

    private String Id;
    private String Name;
    private String contentType;

    @JsonCreator
    public DpsFileInfo(
            @JsonProperty("id")String id,
            @JsonProperty("name")String name,
            @JsonProperty("contentType")String contentType) {
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
