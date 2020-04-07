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

    public ByteArrayInputStream getContent(String remoteFilename) {

        ByteArrayInputStream result = null;
        ChannelSftp channelSftp = null;

        byte[] buff = new byte[BUFFER_SIZE];

        int bytesRead;

        try(ByteArrayOutputStream bao = new ByteArrayOutputStream()) {
            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            logger.debug("Attempting to get remote file [{}]", remoteFilename);
            InputStream inputStream = channelSftp.get(remoteFilename);
            logger.debug("Successfully get remote file [{}]", remoteFilename);

            while ((bytesRead = inputStream.read(buff)) != -1) {
                bao.write(buff, 0, bytesRead);
            }

            byte[] data = bao.toByteArray();

            try(ByteArrayInputStream resultBao = new ByteArrayInputStream(data)) {
                result = resultBao;
            }

        } catch (JSchException | SftpException | IOException e) {
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
    public void moveFile(String remoteFileName, String destinationFilename) {

        ChannelSftp channelSftp = null;

        try {
            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            channelSftp.rename(remoteFileName, destinationFilename);
            logger.debug("Successfully renamed files on the sftp server from {} to {}", remoteFileName, destinationFilename);

        } catch (JSchException | SftpException e) {
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if(channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();
        }
    }

    @Override
    public void put(InputStream inputStream, String remoteFileName) {


        ChannelSftp channelSftp = null;

        try {
            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            channelSftp.put(inputStream, remoteFileName);
            logger.debug("Successfully uploadeed file [{}]", remoteFileName );

        } catch (JSchException | SftpException e) {
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if(channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();
        }

    }
}
