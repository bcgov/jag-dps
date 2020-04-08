package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Batches")
@XmlAccessorType(XmlAccessType.FIELD)
public class Batches {

    @XmlElement(name="Batch")
    private List<Batch> batches = new ArrayList<>();

    protected Batches() {
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void addBatch(Batch batch) {
        batches.add(batch);
    }
}
