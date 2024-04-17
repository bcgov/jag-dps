package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import jakarta.xml.bind.annotation.XmlAttribute;

public class BatchField {

    protected BatchField() {}

    @XmlAttribute(name = "Name")
    public String name;

    @XmlAttribute(name = "Value")
    public String value;

    public BatchField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
