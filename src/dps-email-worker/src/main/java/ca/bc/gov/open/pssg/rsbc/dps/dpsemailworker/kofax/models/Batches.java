package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
