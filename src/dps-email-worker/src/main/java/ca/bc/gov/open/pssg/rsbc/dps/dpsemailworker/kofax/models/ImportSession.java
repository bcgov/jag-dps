package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import jakarta.xml.bind.annotation.*;
import java.util.Optional;

@XmlRootElement(name="ImportSession")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportSession {

    @XmlAttribute(name = "UserID")
    private String userID;

    @XmlAttribute(name = "Password")
    private String password;

    @XmlAttribute(name = "ErrorCode")
    private String errorCode;

    @XmlAttribute(name = "ErrorMessage")
    private String errorMessage;

    @XmlElement(name="Batches")
    private Batches batches = new Batches();

    protected ImportSession() {}

    public ImportSession(String userID, String password, String errorCode, String errorMessage) {
        this.userID = userID;
        this.password = password;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getPassword() {
        return this.password;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Batches getBatches() {
        return batches;
    }

    public Optional<String> getBatchName() {

        Optional<Batch> fistBatch = this.batches.getBatches().stream().findFirst();

        if(!fistBatch.isPresent()) return Optional.empty();

        return Optional.of(fistBatch.get().getName());

    }

}
