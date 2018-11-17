package Com.VSummary.domain;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AsuncDownloaderFTP {

    public static final String fullPath = "https://saintslife.000webhostapp.com/VSummary/";

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.login}")
    private String ftpLogin;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.workingdirectory}")
    private String ftpPath;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Async
    public void asuncDeleteFile(String filePath) {
        FTPClient client = new FTPClient();

        if (!openConnection(client) & !configureConnection(client))
            return; // not connection

        deleteFile(client, filePath);
        closeConnection(client);
    }

    @Async
    public void asuncLoadFiles(List<MultipartFileBuffer> files, String username) {
        FTPClient client = new FTPClient();

            if (!(openConnection(client)) & !(configureConnection(client)))
                return; // not connection

            for (MultipartFileBuffer file : files) {
                if (addFile(client, file))
                    simpMessagingTemplate.convertAndSendToUser(username, "queue/fileLoaded", new SimpleMessage(file.getId()));
                else
                    simpMessagingTemplate.convertAndSendToUser(username, "queue/fileError", new SimpleMessage(file.getId()));
            }

            closeConnection(client);
    }

    private boolean openConnection(FTPClient client) {
        try {
            client.connect(ftpHost, ftpPort);
            return client.login(ftpLogin, ftpPassword);
        } catch (IOException e) {
            closeConnection(client);
            return false;
        }
    }

    private boolean configureConnection(FTPClient client) {
        try {
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.changeWorkingDirectory(ftpPath);
            return true;
        } catch (IOException e) {
            closeConnection(client);
            return false;
        }
    }

    private boolean closeConnection(FTPClient client) {
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean deleteFile(FTPClient client, String filePath) {
        try {
            return client.deleteFile(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    private boolean addFile(FTPClient client, MultipartFileBuffer file) {
        try {
            return client.storeFile(file.getName(), file.getInputStream());
        } catch (IOException e) {
            return false;
        }
    }
}
