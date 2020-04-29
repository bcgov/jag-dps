package ca.bc.gov.open.pssg.rsbc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class DpsFileInfo {

    private String id;
    private String name;
    private String contentType;

    @JsonCreator
    public DpsFileInfo(
            @JsonProperty("id")String id,
            @JsonProperty("name")String name,
            @JsonProperty("contentType")String contentType) {

        if(StringUtils.isEmpty(id)) throw new IllegalArgumentException("id");
        if(StringUtils.isEmpty(name)) throw new IllegalArgumentException("name");

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
