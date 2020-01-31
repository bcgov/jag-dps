package ca.bc.gov.open.pssg.rsbc.dps.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * An outputNotification Message to allow communication between workers
 *
 * @author alexjoybc@github
 *
 */
public class outputNotificationMessage {

    private String businessAreaCd;

    private List<String> fileList =  new ArrayList<String>();

    public outputNotificationMessage(String businessAreaCd) {
        this.businessAreaCd = businessAreaCd;
    }

    public String getBusinessAreaCd() {
        return businessAreaCd;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void AddFile(String fileName) {
        fileList.add(fileName);
    }

}
