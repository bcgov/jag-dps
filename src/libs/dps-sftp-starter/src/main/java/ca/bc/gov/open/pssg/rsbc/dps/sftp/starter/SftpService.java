package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public interface SftpService {

    ByteArrayInputStream getContent(String remoteFilename);

    void moveFile(String remoteFileName, String destinationFilename);

    /**
     * @param inputStream the content to be uploaded
     * @param remoteFileName the remote filename
     */
    void put(InputStream inputStream, String remoteFileName);

    List<String> listFiles(String remoteDirectory);

}
