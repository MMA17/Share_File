package view;
 
import control.UploadTask;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.JFilePicker;
import model.User;
import control.ClientCtr;
import control.UploadFunc;
import model.ObjectWrapper;
import model.file;
 

public class UploadFrm extends JFrame implements
        PropertyChangeListener {
    private JLabel labelURL = new JLabel("Upload URL: ");
    private JTextField fieldURL = new JTextField(30);
    private JFilePicker filePicker = new JFilePicker("Choose a file: ",
            "Browse");
    private JButton buttonUpload = new JButton("Upload");
    private JLabel labelProgress = new JLabel("Progress:");
    private JProgressBar progressBar = new JProgressBar(0, 100);
    User user;
    ClientCtr mySocket;
    
    public UploadFrm(User user, ClientCtr mySocket) {
        super("Upload File");
        this.user = user;
        this.mySocket = mySocket;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        filePicker.setMode(JFilePicker.MODE_OPEN);
 
        buttonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                buttonUploadActionPerformed(event);
            }
        });
 
        progressBar.setPreferredSize(new Dimension(200, 30));
        progressBar.setStringPainted(true);
 
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        add(filePicker, constraints);
 
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonUpload, constraints);
 
        constraints.gridx = 0;
        constraints.gridy = 3;
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
 
    private void buttonUploadActionPerformed(ActionEvent event) {
        String uploadURL = "http://localhost:8080/UploadFile/UploadServlet";
        String filePath = filePicker.getSelectedFilePath();
 
        // validate input first
        if (uploadURL.equals("")) {
            JOptionPane.showMessageDialog(this, "Invalid URL!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldURL.requestFocus();
            return;
        }
 
        if (filePath.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Choose a file", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        try {
            File uploadFile = new File(filePath);
            progressBar.setValue(0);
            UploadTask task = new UploadTask(uploadURL, uploadFile);
            task.addPropertyChangeListener(this);
            
            UploadFunc a = new UploadFunc(uploadURL, "UTF-8");
            file f = (file) a.getFileInfo(filePath, uploadFile, user);
            
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CHECK_VALID_FILE, f));
            mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_VALID_FILE, this));
            // Add File into database
//            task.execute();
//            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ADD_FILE,f));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void Upload(ObjectWrapper data) {
        if(data.getData().equals("false")) {
            System.out.println("Result: " + data.getData());
            JOptionPane.showMessageDialog(this, "File này đã tồn tại trên server!");
            return;
        }
        String uploadURL = "http://localhost:8080/UploadFile/UploadServlet";
        String filePath = filePicker.getSelectedFilePath();
 
        // validate input first
        if (uploadURL.equals("")) {
            JOptionPane.showMessageDialog(this, "Invalid URL!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            fieldURL.requestFocus();
            return;
        }
 
        if (filePath.equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Choose a file", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        try {
            File uploadFile = new File(filePath);
            progressBar.setValue(0);
            UploadTask task = new UploadTask(uploadURL, uploadFile);
            task.addPropertyChangeListener(this);
            
            UploadFunc a = new UploadFunc(uploadURL, "UTF-8");
            file f = (file) a.getFileInfo(filePath, uploadFile, user);
            task.execute();
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ADD_FILE,f));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
 
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }
 
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
    }
}