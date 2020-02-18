package ca.bc.gov.open.pssg.rsbc.dps.files.notification;

/**
 * An outputNotification Message to allow communication between workers
 *
 * @author alexjoybc@github
 *
 */
public class OutputNotificationMessage {

    private String businessAreaCd;

    private String fileId;

    protected OutputNotificationMessage(){ }

    public OutputNotificationMessage(String businessAreaCd, String fileId) {
        this.businessAreaCd = businessAreaCd;
        this.fileId = fileId;
    }

    public String getBusinessAreaCd() {
        return businessAreaCd;
    }


    public String getFileId() {
        return fileId;
    }
}
