package ca.bc.gov.open.pssg.rsbc.dps.sftp.starter;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SftpServiceImpl implements SftpService {

    public static final int BUFFER_SIZE = 8000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Session session;

    public SftpServiceImpl(Session session) {
        this.session = session;
    }

    public ByteArrayInputStream getContent(String remoteFilename) throws DpsSftpException {

        ByteArrayInputStream result = null;
        ChannelSftp channelSftp = null;

        byte[] buff = new byte[BUFFER_SIZE];

        int bytesRead;

        try {
            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            logger.debug("Attempting to get remote file [{}]", remoteFilename);
            InputStream inputStream = channelSftp.get(remoteFilename);
            logger.debug("Successfully get remote file [{}]", remoteFilename);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(buff)) != -1) {
                bao.write(buff, 0, bytesRead);
            }

            byte[] data = bao.toByteArray();
            result = new ByteArrayInputStream(data);

        } catch (JSchException | SftpException | IOException e) {
            logger.error("{} while trying to get file from sftp server {}", e.getClass().getSimpleName(), e.getMessage(), e);
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if(channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();
        }

        return result;
    }

    /**
     * Move the file to a destination
     *
     * @param remoteFileName
     * @param destinationFilename
     * @throws DpsSftpException
     */
    public void moveFile(String remoteFileName, String destinationFilename) throws DpsSftpException {

        ChannelSftp channelSftp = null;

        try {
            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            channelSftp.rename(remoteFileName, destinationFilename);
            logger.debug("Successfully renamed files on the sftp server from {} to {}", remoteFileName, destinationFilename);

        } catch (JSchException | SftpException e) {
            logger.error("{} while trying to get file from sftp server {}", e.getClass().getSimpleName(), e.getMessage(), e);
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if(channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();
        }
    }
}
