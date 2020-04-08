package ca.bc.gov.open.pssg.rsbc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DpsFileInfo {

    private String id;
    private String name;
    private String contentType;

    @JsonCreator
    public DpsFileInfo(
            @JsonProperty("id")String id,
            @JsonProperty("name")String name,
            @JsonProperty("contentType")String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }
}
