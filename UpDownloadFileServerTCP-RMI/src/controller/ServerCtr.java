package controller;
 

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import model.FilePerms;
import model.Group;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import model.file;
import model.usertogroup;
import rmi.general.CustomerInterface;
import rmi.general.FileInterface;
import rmi.general.GroupInterface;
import rmi.general.UserInterface;
import view.ServerMainFrm;
 
public class ServerCtr {
    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost",8888);  //default server host and port
    private UserInterface userRO;
    private FileInterface fileRO;
    private GroupInterface groupRO;
    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";
     
    public ServerCtr(ServerMainFrm view){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();
    }
     
    public ServerCtr(ServerMainFrm view, int serverPort){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();
    }
    
    public boolean init(){
        try{
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            userRO = (UserInterface) (registry.lookup(rmiService));
//            view.setServerHost(serverAddress.getHost(), serverAddress.getPort()+"", rmiService);
            view.showMessage("Found the remote objects at the host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        }catch(Exception e){
            e.printStackTrace();
            view.showMessage("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }
    
//    public void setServerHost(String serverHost, String serverPort, String service) {
//        txtServerHost.setText(serverHost);
//        txtServerPort.setText(serverPort);
//        txtService.setText(service);
//    } 
     
    private void openServer(){
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            //System.out.println("server started!");
            view.showMessage("TCP server is running at the port " + myAddress.getPort() +"...");
        }catch(Exception e) {
            e.printStackTrace();;
        }
    }
     
    public void stopServer() {
        try {
            for(ServerProcessing sp:myProcess)
                sp.stop();
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
        for(ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }
    
    public void Update() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REFRESH, "refresh");
        for(ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }
     
    /**
     * The class to listen the connections from client, avoiding the blocking of accept connection
     *
     */
    class ServerListening extends Thread{
         
        public ServerListening() {
            super();
        }
         
        public void run() {
            view.showMessage("server is listening... ");
            try {
                while(true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread{
        private Socket mySocket;
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;
         
        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }
         
        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
         
        public void run() { 
            try {
                while(true) {
                    ObjectWrapper resultData = new ObjectWrapper();
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if(o instanceof ObjectWrapper){
                        ObjectWrapper data = (ObjectWrapper) o;
 
                        switch(data.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            System.out.println("Login");
                            User user = (User)data.getData();
                            if(userRO.checkLogin(user)){
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, user));
                            }
                            else
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,"false"));  
                            break;
//                        case ObjectWrapper.GET_FILES:
//                            User userfile = (User)data.getData();
//                            ArrayList<file> files = new ArrayList<file>();
//                            FileDAO fileDAO = new FileDAO();
//                            files = fileDAO.searchAllFilesOfUser(userfile.getUser_id());
//                            //System.out.println(files);
//                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FILES,files));
//                            break;
//                        case ObjectWrapper.ADD_FILE:
//                            file f = (file)data.getData();
//                            FileDAO fd = new FileDAO();
//                            fd.addFile(f.getFile_name(), f.getSize(), f.getOwner(), f.getFile_extension());
//                            //System.err.println("Server add file");
////                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FILES,files));
//                            break;
//                        case ObjectWrapper.SEARCH_FRIEND:
//                            User user1 = (User) data.getData();
//                            ArrayList<User> list = new ArrayList<User>();
//                            UserDAO uDao = new UserDAO();
//                            list = uDao.searchFriends(user1.getUser_id());
//                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_FRIEND,list));
//                            break;
//                        case ObjectWrapper.GET_FRIEND_IN_GROUP:
//                            User user2 = (User) data.getData();
//                            ArrayList<User> list1 = new ArrayList<User>();
//                            UserDAO uDao1 = new UserDAO();
//                            list = uDao1.searchFriends(user2.getUser_id());
//                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_IN_GROUP,list));
//                            break;
                        case ObjectWrapper.REGISTER_USER:
                            User user3 = (User) data.getData();
                                if (userRO.AddUser(user3)) {
                                    System.out.println("thanh cong");
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_USER,"ok"));
                                }
                                else {
                                    System.out.println("that bai");
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_USER,"false"));
                                }
                            break;
//                        case ObjectWrapper.SHARE_FILE_TO_USER:
//                            FilePerms fp = (FilePerms) data.getData();
//                            System.out.println(fp.getDeletePermission() + fp.getReadPermission() + fp.getFile_id() + fp.getUser_id());
//                            if (new FileDAO().addFilePermission(fp.getReadPermission(), fp.getDeletePermission(), fp.getUser_id(), fp.getFile_id())) {
//                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SHARE_FILE_TO_USER,"ok"));
//                                System.out.println("gui thanh cong");
//                            }
//                            else {
//                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SHARE_FILE_TO_USER,"false"));
//                                System.out.println("gui that bai");
//                            }
//                            break;
//                        case ObjectWrapper.GET_GROUPS:
//                            User user_group = (User) data.getData();
//                            ArrayList<Group> groups = new ArrayList<>();
//                            
//                            try {
//                                groups = new GroupDAO().searchGroup(user_group.getUser_id());
//                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_GROUPS,groups));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_GROUPS,"false"));
//                            }
//                            break;
//                        case ObjectWrapper.CREATE_GROUP:
//                            Group group = (Group) data.getData();                            
//                            try {
//                                if (new GroupDAO().createGroup(1, group)) {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP,"ok"));
//                                }
//                                else {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP,"false"));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        case ObjectWrapper.ADD_USER_TO_GROUP:
//                            usertogroup a = (usertogroup) data.getData();
//                            try {
//                                if (new GroupDAO().addUser(a.getUser_id(), a.getGroup_id())) {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_USER_TO_GROUP,"ok"));
//                                }
//                                else {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_USER_TO_GROUP,"false"));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            break;
                        }
                    }
                }
            }catch (EOFException | SocketException e) {             
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientNumber();
                try {
                    mySocket.close();
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }  
    }
}