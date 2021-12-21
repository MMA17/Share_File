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
import model.FilePerms;
import model.Friend;
import model.Group;
import model.GroupxUser;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
//import model.UserPair;
import model.file;
import model.usertogroup;
import view.ServerMainFrm;
 
public class ServerCtr {
    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost",8888);  //default server host and port
    private IPAddress serverAddress = new IPAddress("localhost", 5555); //server udp host and port
    private DatagramSocket udpSocket;
     
    public ServerCtr(ServerMainFrm view){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();
        openUDP();
    }
     
    public ServerCtr(ServerMainFrm view, int serverPort){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();
        openUDP();
    }
    
    private void openUDP() {
        try {
            udpSocket = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
//            view.setServerandClientInfo(serverAddress, myAddress);
            view.showMessage("UDP client đang chạy ở host host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("UDP chua chay duoc!");
//            return false;
        }
//        return true;
    }
     
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
    
    public void refresh() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REFRESH, "refresh");
        for (ServerProcessing sp : myProcess) {
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
                                User user = (User) data.getData();
                                System.out.println("data nhan duoc: " + user.getUser_name());
                                resultData.setPerformative(ObjectWrapper.LOGIN_USER);
                                resultData.setData(user);
                                break;
                            case ObjectWrapper.REGISTER_USER:
                                User user3 = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.REGISTER_USER);
                                resultData.setData(user3);
                                break;
                            case ObjectWrapper.SEARCH_FRIEND:
                                user = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.SEARCH_FRIEND);
                                resultData.setData(user);
                                break;
                            case ObjectWrapper.SEARCH_ALL_USER:
                                user = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.SEARCH_ALL_USER);
                                resultData.setData(user);
                                break;
                            case ObjectWrapper.FRIEND_REQUEST:
                                user = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.FRIEND_REQUEST);
                                resultData.setData(user);
                                break;
                            case ObjectWrapper.SEARCH_USER_BY_NAME:
                                String key = (String) data.getData();
                                resultData.setPerformative(ObjectWrapper.SEARCH_USER_BY_NAME);
                                resultData.setData(key);
                                break;
                            case ObjectWrapper.GET_FILES:
                                User userfile = (User)data.getData();
                                resultData.setPerformative(ObjectWrapper.GET_FILES);
                                resultData.setData(userfile);
                                break;
                            case ObjectWrapper.ADD_FILE:
                                file f = (file)data.getData();
                                resultData.setPerformative(ObjectWrapper.ADD_FILE);
                                resultData.setData(f);
                                break;
                            case ObjectWrapper.GET_FRIEND_IN_GROUP:
                                User user2 = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.GET_FRIEND_IN_GROUP);
                                resultData.setData(user2);
                                break;
                            case ObjectWrapper.SHARE_FILE_TO_USER:
                                FilePerms fp = (FilePerms) data.getData();
                                resultData.setPerformative(ObjectWrapper.SHARE_FILE_TO_USER);
                                resultData.setData(fp);
                                break;
                            case ObjectWrapper.SHARE_FILE_TO_GROUP:
                                FilePerms fp1 = (FilePerms) data.getData();
                                resultData.setPerformative(ObjectWrapper.SHARE_FILE_TO_GROUP);
                                resultData.setData(fp1);
                                break;
                            case ObjectWrapper.CHECK_PERMS_FILE:
                                FilePerms fp2 = (FilePerms) data.getData();
                                resultData.setPerformative(ObjectWrapper.CHECK_PERMS_FILE);
                                resultData.setData(fp2);
                                break;
                            case ObjectWrapper.CHECK_VALID_FILE:
                                file f4 = (file) data.getData();
                                resultData.setPerformative(ObjectWrapper.CHECK_VALID_FILE);
                                resultData.setData(f4);
                                break;
                            case ObjectWrapper.GET_GROUPS:
                                User user_group = (User) data.getData();
                                resultData.setPerformative(ObjectWrapper.GET_GROUPS);
                                resultData.setData(user_group);
                                break;
                            case ObjectWrapper.CREATE_GROUP:
                                GroupxUser group = (GroupxUser) data.getData();
                                resultData.setPerformative(ObjectWrapper.CREATE_GROUP);
                                resultData.setData(group);
                                break;
                            case ObjectWrapper.ADD_USER_TO_GROUP:
                                GroupxUser a = (GroupxUser) data.getData();
                                resultData.setPerformative(ObjectWrapper.ADD_USER_TO_GROUP);
                                resultData.setData(a);
                                break;
                            case ObjectWrapper.SEND_FRIEND_REQUEST:
                                Friend up = (Friend) data.getData();
                                resultData.setPerformative(ObjectWrapper.SEND_FRIEND_REQUEST);
                                resultData.setData(up);
                                break;
                            case ObjectWrapper.ACCEPT_FRIEND_REQUEST:
                                Friend up1 = (Friend) data.getData();
                                resultData.setPerformative(ObjectWrapper.ACCEPT_FRIEND_REQUEST);
                                resultData.setData(up1);
                                break;
                            case ObjectWrapper.DELETE_FRIEND:
                                Friend up2 = (Friend) data.getData();
                                resultData.setPerformative(ObjectWrapper.DELETE_FRIEND);
                                resultData.setData(up2);
                                break;
                            case ObjectWrapper.SET_STATUS_OFFLINE:
                                User u7 = (User) data.getData();
                                resultData.setData(u7);
                                resultData.setPerformative(ObjectWrapper.SET_STATUS_OFFLINE);
                                break;
                        }
                        //send data to udp server
                        sendDataToUdpServer(resultData);

                        //recieve
//                        ObjectWrapper mid = receiveDataFromUdp();
                        //send to tcp client
                        //System.out.println(String.valueOf(receiveDataFromUdp().getPerformative()) + String.valueOf(receiveDataFromUdp().getData()));
                        oos.writeObject(receiveDataFromUdp());
                        if (data.getPerformative() == ObjectWrapper.LOGIN_USER || data.getPerformative() == ObjectWrapper.SEND_FRIEND_REQUEST
                        || data.getPerformative() == ObjectWrapper.ACCEPT_FRIEND_REQUEST || data.getPerformative() == ObjectWrapper.DELETE_FRIEND
                                || data.getPerformative() == ObjectWrapper.SET_STATUS_OFFLINE) {
                            refresh();
                        }
 
                    }
                    //ois.reset();
                    //oos.reset();
                }
            }catch (EOFException | SocketException e) {             
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientNumber();
                refresh();
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
        
        public boolean sendDataToUdpServer(ObjectWrapper data) {
            try {
                //prepare the buffer and write the data to send into the buffer
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(data);
                oos.flush();

                //create data package and send
                byte[] sendData = baos.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort());
                udpSocket.send(sendPacket);

            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error in sending data package");
                return false;
            }
            return true;
        }

        public ObjectWrapper receiveDataFromUdp() {
            ObjectWrapper result = null;
            try {
                //prepare the buffer and fetch the received data into the buffer
                byte[] receiveData = new byte[4096];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                udpSocket.receive(receivePacket);

                //read incoming data from the buffer 
                ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                ObjectInputStream ois = new ObjectInputStream(bais);
                result = (ObjectWrapper) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error in receiving data package");
            }
            return result;
        }
        
        
    }
}