package ca.bc.gov.open.pssg.rsbc.vips.notification.worker.sftp;

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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Session session;

    public SftpServiceImpl(Session session) {
        this.session = session;
    }

    public ByteArrayInputStream getContent(String remoteFilename) throws DpsSftpException {

        ByteArrayInputStream result = null;
        ChannelSftp channelSftp = null;

        byte[] buff = new byte[8000];

        int bytesRead = 0;

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

        } catch (JSchException e) {
            logger.error("JSchException while trying to get file from sftp server {}", e.getMessage());
            e.printStackTrace();
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } catch (SftpException e) {
            logger.error("SftpException while trying to get file from sftp server {}", e.getMessage());
            e.printStackTrace();
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } catch (IOException e) {
            logger.error("IOException while trying to get file from sftp server {}", e.getMessage());
            e.printStackTrace();
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if(channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();
        }

        return result;

    }


}
