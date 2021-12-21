package view;
 
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
import model.User;
import control.ClientCtr;
import model.ObjectWrapper;
 
public class LoginFrm extends JFrame implements ActionListener{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ClientCtr mySocket;
     
    public LoginFrm(ClientCtr socket){
        super("TCP Login MVC");     
        mySocket = socket;
         
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
         
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(btnLogin);
        btnLogin.addActionListener(this);
                 
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, this));
    }
     
 
    public void actionPerformed(ActionEvent e) {
        if((e.getSource() instanceof JButton) && (((JButton)e.getSource()).equals(btnLogin))) {
            //pack the entity
            User user = new User();
            user.setUser_name(txtUsername.getText());
            user.setPass(txtPassword.getText());
             
            //sending data
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOGIN_USER, user));
            //receive data
            
        }
    }
    public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof User) {
            User user= (User) data.getData();
            System.out.println("User_Id: " + user.getUser_id());
            DashboardFrm main = new DashboardFrm(user, mySocket);
            main.setVisible(true);
//            System.out.println(user.getName());
//            JOptionPane.showMessageDialog(this, "thanh cong");
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, "ko thanh cong");
        }
    }
}