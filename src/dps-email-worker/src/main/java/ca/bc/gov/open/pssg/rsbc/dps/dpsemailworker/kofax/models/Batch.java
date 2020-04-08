package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Batch")
@XmlAccessorType(XmlAccessType.FIELD)
public class Batch {

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "BatchClassName")
    private String batchClassName;

    @XmlAttribute(name = "EnableAutomaticSeparationAndFormID")
    private String enableAutomaticSeparationAndFormID;

    @XmlAttribute(name = "RelativeImageFilePath")
    private String relativeImageFilePath;

    @XmlElement(name = "BatchFields")
    private BatchFields batchFields = new BatchFields();

    @XmlElement(name = "Pages")
    private Pages pages = new Pages();

    protected Batch() { }

    public Batch(String name, String batchClassName, String enableAutomaticSeparationAndFormID,
                 String relativeImageFilePath) {
        this.name = name;
        this.batchClassName = batchClassName;
        this.enableAutomaticSeparationAndFormID = enableAutomaticSeparationAndFormID;
        this.relativeImageFilePath = relativeImageFilePath;
    }

    public String getName() {

        return name;
    }

    public String getBatchClassName() {

        return batchClassName;
    }

    public String getEnableAutomaticSeparationAndFormID() {

        return enableAutomaticSeparationAndFormID;
    }

    public String getRelativeImageFilePath() {

        return relativeImageFilePath;
    }

    public BatchFields getBatchFields() {

        return batchFields;
    }

    public Pages getPages() {
        return pages;
    }
}
