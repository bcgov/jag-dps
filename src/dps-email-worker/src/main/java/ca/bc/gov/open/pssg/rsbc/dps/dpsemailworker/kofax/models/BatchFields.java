package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "BatchFields")
@XmlAccessorType(XmlAccessType.FIELD)
public class BatchFields {

    @XmlElement(name = "BatchField")
    private List<BatchField> batchFields = new ArrayList<>();

    protected BatchFields() { }

    public List<BatchField> getBatchFields() {
        return batchFields;
    }

    public void addBatchField(BatchField batchField) {
        this.batchFields.add(batchField);
    }

}
