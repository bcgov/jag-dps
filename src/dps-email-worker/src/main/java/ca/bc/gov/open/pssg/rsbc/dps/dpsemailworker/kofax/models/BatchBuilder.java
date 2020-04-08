package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BatchBuilder {

    private String inputChannel;
    private String batchClassName;
    private String enableAutomaticSeparationAndFormID;
    private String relativeImageFilePath;

    public BatchBuilder() {
    }

    public BatchBuilder withInputChannel(String inputChannel) {
        this.inputChannel = inputChannel;
        return this;
    }

    public BatchBuilder withBatchClassName(String batchClassName) {
        this.batchClassName = batchClassName;
        return this;
    }

    public BatchBuilder withEnableAutomaticSeparationAndFormID(String enableAutomaticSeparationAndFormID) {
        this.enableAutomaticSeparationAndFormID = enableAutomaticSeparationAndFormID;
        return this;
    }

    public BatchBuilder withRelativeImageFilePath(String relativeImageFilePath) {
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
        Batch result = new Batch(getName(), this.batchClassName, this.enableAutomaticSeparationAndFormID,
                this.relativeImageFilePath);
        return result;
    }

}

