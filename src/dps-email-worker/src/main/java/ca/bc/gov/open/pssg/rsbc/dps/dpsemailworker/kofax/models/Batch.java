package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import jakarta.xml.bind.annotation.*;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@XmlRootElement(name = "Batch")
@XmlAccessorType(XmlAccessType.FIELD)
public class Batch {

    public static class Builder {

        private String inputChannel;
        private String batchClassName;
        private String enableAutomaticSeparationAndFormID;
        private String relativeImageFilePath;

        public Builder withInputChannel(String inputChannel) {
            this.inputChannel = inputChannel;
            return this;
        }

        public Builder withBatchClassName(String batchClassName) {
            this.batchClassName = batchClassName;
            return this;
        }

        public Builder withEnableAutomaticSeparationAndFormID(String enableAutomaticSeparationAndFormID) {
            this.enableAutomaticSeparationAndFormID = enableAutomaticSeparationAndFormID;
            return this;
        }

        public Builder withRelativeImageFilePath(String relativeImageFilePath) {
            this.relativeImageFilePath = relativeImageFilePath;
            return this;
        }

        private String getTimeStamp() {

            DateFormat df = new SimpleDateFormat("YYYY-MM-dd-HH-mm-ss-SSS");
            Calendar calendar = Calendar.getInstance();
            return df.format(calendar.getTime());

        }

        private String getName() {
            return MessageFormat.format("{0}-{1}", this.inputChannel, getTimeStamp());
        }

        public Batch build() {
            Batch result = new Batch();
            result.name = getName();
            result.batchClassName = this.batchClassName;
            result.enableAutomaticSeparationAndFormID = this.enableAutomaticSeparationAndFormID;
            result.relativeImageFilePath = relativeImageFilePath;
            return result;
        }
    }


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
