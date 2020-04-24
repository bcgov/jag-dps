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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SftpServiceImpl implements SftpService {

    interface SftpFunction {
        void exec(ChannelSftp channelSftp) throws SftpException;
    }

    public static final int BUFFER_SIZE = 8000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JschSessionProvider jschSessionProvider;

    public SftpServiceImpl(JschSessionProvider jschSessionProvider) {
        this.jschSessionProvider = jschSessionProvider;
    }

    public ByteArrayInputStream getContent(String remoteFilename) {

        ByteArrayInputStream result = null;
        byte[] buff = new byte[BUFFER_SIZE];

        Session session = null;

        try (ByteArrayOutputStream bao = new ByteArrayOutputStream()) {

            executeSftpFunction(channelSftp -> {
                try {

                    int bytesRead;

                    logger.debug("Attempting to get remote file [{}]", remoteFilename);
                    InputStream inputStream = channelSftp.get(remoteFilename);
                    logger.debug("Successfully get remote file [{}]", remoteFilename);

                    while ((bytesRead = inputStream.read(buff)) != -1) {
                        bao.write(buff, 0, bytesRead);
                    }

                } catch (IOException e) {
                    throw new DpsSftpException(e.getMessage(), e.getCause());
                }
            });

            byte[] data = bao.toByteArray();

            try (ByteArrayInputStream resultBao = new ByteArrayInputStream(data)) {
                result = resultBao;
            }

        } catch (IOException e) {
            throw new DpsSftpException(e.getMessage(), e.getCause());
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

        executeSftpFunction(channelSftp -> {
                channelSftp.rename(remoteFileName, destinationFilename);
                logger.debug("Successfully renamed files on the sftp server from {} to {}", remoteFileName,
                        destinationFilename);
        });

    }

    @Override
    public void put(InputStream inputStream, String remoteFileName) {

        executeSftpFunction(channelSftp -> {
                channelSftp.put(inputStream, remoteFileName);
                logger.debug("Successfully uploadeed file [{}]", remoteFileName);
        });
    }

    /**
     * Returns a list of file
     * @param remoteDirectory
     * @return
     */
    @Override
    public List<String> listFiles(String remoteDirectory) {

        List<String> result = new ArrayList<>();

        executeSftpFunction(channelSftp -> {
            Vector fileList = channelSftp.ls(remoteDirectory);

            for(int i = 0; i < fileList.size(); i++) {
                logger.debug("Attempting to list files in [{}]", remoteDirectory);
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry)fileList.get(i);
                logger.debug("Successfully to list files in [{}]", remoteDirectory);
                result.add(lsEntry.getFilename());
            }

        });

        return result;
    }

    private void executeSftpFunction(SftpFunction sftpFunction) {

        ChannelSftp channelSftp = null;
        Session session = null;

        try {

            session = jschSessionProvider.getSession();

            logger.debug("Attempting to open sftp channel");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.debug("Successfully connected to sftp server");

            sftpFunction.exec(channelSftp);

        } catch (JSchException | SftpException e) {
            throw new DpsSftpException(e.getMessage(), e.getCause());
        } finally {
            if (channelSftp != null && channelSftp.isConnected())
                channelSftp.disconnect();

            jschSessionProvider.closeSession(session);
        }
    }




}
