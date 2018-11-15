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
    public void asuncLoadFiles(List<MultipartFileBuffer> files, String username) {
        FTPClient client = new FTPClient();
        try {
            client.connect(ftpHost, ftpPort);
            if (!client.login(ftpLogin, ftpPassword))
                return; // not connection

            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.changeWorkingDirectory(ftpPath);

            for (MultipartFileBuffer file : files) {
                client.storeFile(file.getName(), file.getInputStream());
                simpMessagingTemplate.convertAndSendToUser(username, "queue/fileLoaded", new SimpleMessage(file.getId()));
            }

            client.logout();
            client.disconnect();
        } catch (IOException e) {
            //FTP ERROR
        } finally {
            if (client.isConnected()) {
                try {
                    client.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
