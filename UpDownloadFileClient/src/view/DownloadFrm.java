/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Hoang Viet
 */
import control.DownloadTask;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.User;
import control.ClientCtr;
import java.util.ArrayList;
import model.FilePerms;
import model.JFilePicker;
import model.ObjectWrapper;
import model.file;
 

public class DownloadFrm extends JFrame implements
        PropertyChangeListener {
    private JLabel labelURL = new JLabel("File name: ");
    private JTextField fieldURL = new JTextField(30);
 
    private JFilePicker filePicker = new JFilePicker("Save in directory: ",
            "Browse...");
 
    private JButton buttonDownload = new JButton("Download");
 
    private JLabel labelFileName = new JLabel("File name: ");
    private JTextField fieldFileName = new JTextField(20);
     
    private JLabel labelFileSize = new JLabel("File size (bytes): ");
    private JTextField fieldFileSize = new JTextField(20);
     
    private JLabel labelProgress = new JLabel("Progress:");
    private JProgressBar progressBar = new JProgressBar(0, 100);
    private User user;
    private ClientCtr mySocket;
    public DownloadFrm(User user, ClientCtr mySocket) {
        super("Download File");
        this.user = user;
        this.mySocket = mySocket;
        // set up layout
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
 
        // set up components
        filePicker.setMode(JFilePicker.MODE_SAVE);
        filePicker.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 
        buttonDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                buttonDownloadActionPerformed(event);
            }
        });
 
        fieldFileName.setEditable(false);
        fieldFileSize.setEditable(false);
         
        progressBar.setPreferredSize(new Dimension(200, 30));
        progressBar.setStringPainted(true);
 
        // add components to the frame
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(labelURL, constraints);
 
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        add(fieldURL, constraints);
 
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        add(filePicker, constraints);
 
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonDownload, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(labelFileName, constraints);
         
        constraints.gridx = 1;
        add(fieldFileName, constraints);
         
        constraints.gridy = 4;
        constraints.gridx = 0;
        add(labelFileSize, constraints);
         
        constraints.gridx = 1;
        add(fieldFileSize, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(labelProgress, constraints);
 
        constraints.gridx = 1;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(progressBar, constraints);
 
        pack();
        setLocationRelativeTo(null);    // center on screen
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
    }
 
    private void buttonDownloadActionPerformed(ActionEvent event) {
        String downloadURL = "http://localhost:8080/UploadFile/download?file=" + fieldURL.getText();
        String saveDir = filePicker.getSelectedFilePath();
 
        // validate input first
        if (downloadURL.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter file name!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldURL.requestFocus();
            return;
        }
 
        if (saveDir.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please choose a directory save file!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FilePerms fp = new FilePerms();
            fp.setUser(user);
            file f = new file();
            f.setFile_name(fieldURL.getText());
            fp.setFile(f);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CHECK_PERMS_FILE, fp));
            mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_PERMS_FILE, this));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error executing download task: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }
    
    public void checkPermFile (ObjectWrapper data) {
        if(data.getData().equals("false")) {
            System.out.println("Result: " + data.getData());
            JOptionPane.showMessageDialog(this, "Bạn không có quyền tải file này!");
            return;
        }
        String downloadURL = "http://localhost:8080/UploadFile/download?file=" + fieldURL.getText();
        String saveDir = filePicker.getSelectedFilePath();
 
        // validate input first
        if (downloadURL.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter file name!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldURL.requestFocus();
            return;
        }
 
        if (saveDir.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Please choose a directory save file!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            progressBar.setValue(0);
            DownloadTask task = new DownloadTask(this, downloadURL, saveDir);
            task.addPropertyChangeListener(this);
            task.execute();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error executing download task: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
 
    public void setFileInfo(String name, int size) {
        fieldFileName.setText(name);
        fieldFileSize.setText(String.valueOf(size));
    }
     
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress")) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }      
    }
     

    public static void main(String[] args) {
        try {
            // set look and feel to system dependent
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new SwingFileDownloadHTTP().setVisible(true);
            }
        });
    }  
}
