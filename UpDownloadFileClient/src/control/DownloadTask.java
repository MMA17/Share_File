/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Hoang Viet
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import view.DownloadFrm;
 

public class DownloadTask extends SwingWorker<Void, Void> {
    private static final int BUFFER_SIZE = 4096;   
    private String downloadURL;
    private String saveDirectory;
    private DownloadFrm gui;
     
    public DownloadTask(DownloadFrm gui, String downloadURL, String saveDirectory) {
        this.gui = gui;
        this.downloadURL = downloadURL;
        this.saveDirectory = saveDirectory;
    }
     
    @Override
    protected Void doInBackground() throws Exception {
        try {
            DownloadFunc util = new DownloadFunc();
            util.downloadFile(downloadURL);
             
            // set file information on the GUI
            gui.setFileInfo(util.getFileName(), util.getContentLength());
             
            String saveFilePath = saveDirectory + File.separator + util.getFileName();
 
            InputStream inputStream = util.getInputStream();
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            long totalBytesRead = 0;
            int percentCompleted = 0;
            long fileSize = util.getContentLength();
 
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
 
                setProgress(percentCompleted);         
            }
 
            outputStream.close();
 
            util.disconnect();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Error downloading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);           
            ex.printStackTrace();
            setProgress(0);
            cancel(true);          
        }
        return null;
    }
 

    @Override
    protected void done() {
        if (!isCancelled()) {
            JOptionPane.showMessageDialog(gui,
                    "File has been downloaded successfully!", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }  
}
