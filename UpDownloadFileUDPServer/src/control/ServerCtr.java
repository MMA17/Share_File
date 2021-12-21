package control;

import dao.FileDAO;
import dao.GroupDAO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dao.UserDAO;
import java.util.ArrayList;
import model.*;
import view.ServerMainFrm;

public class ServerCtr {

    private ServerMainFrm view;
    private DatagramSocket myServer;
    private IPAddress myAddress = new IPAddress("localhost", 5555); //default server address
    private UDPListening myListening;

    public ServerCtr(ServerMainFrm view) {
        this.view = view;
    }

    public ServerCtr(ServerMainFrm view, int port) {
        this.view = view;
        myAddress.setPort(port);
    }

    public boolean open() {
        try {
            myServer = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress);
            myListening = new UDPListening();
            myListening.start();
            view.showMessage("UDP server is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }

    public boolean close() {
        try {
            myListening.stop();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }

    class UDPListening extends Thread {

        public UDPListening() {

        }

        public void run() {
            while (true) {
                try {
                    //prepare the buffer and fetch the received data into the buffer
                    byte[] receiveData = new byte[4096];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    myServer.receive(receivePacket);

                    //read incoming data from the buffer 
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper receivedData = (ObjectWrapper) ois.readObject();

                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();

                    switch (receivedData.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            User user = (User) receivedData.getData();// login
                            resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
                            UserDAO ud = new UserDAO();
                            User user_res = ud.checkLogin(user);
                            if (user_res instanceof User) {
                                resultData.setData(user_res);
                                ud.setUserStatusOnline(user_res.getUser_id());
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.REGISTER_USER:
                            User user3 = (User) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_REGISTER_USER);
                                if (new UserDAO().addUser(user3)) {
                                    System.out.println("thanh cong");
                                    resultData.setData("ok");
                                }
                                else {
                                    System.out.println("that bai");
                                    resultData.setData("false");
                                }
                            break;
//                        case ObjectWrapper.SEARCH_USER_BY_NAME: // search friends by name
//                            String key = (String) receivedData.getData();
//                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_FRIEND);
//                            resultData.setData(new UserDAO().searchUserByName(key));
//                            break;
                        case ObjectWrapper.SEARCH_USER_BY_NAME: // search friends by name
                            String key = (String) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_USER);
                            resultData.setData(new UserDAO().searchUserByName(key));
                            break;
                        case ObjectWrapper.GET_FILES:
                            User userfile = (User)receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_GET_FILES);
//                            ArrayList<file> files = new ArrayList<file>();
//                            FileDAO fileDAO = new FileDAO();
//                            files = fileDAO.searchAllFilesOfUser(userfile.getUser_id());
//                            System.out.println(files);
                            resultData.setData(new FileDAO().searchAllFilesOfUser(userfile));
                            break;
                        case ObjectWrapper.ADD_FILE:
                            file f = (file)receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_ADD_FILE);
                            if (new FileDAO().addFile(f)) {
                                resultData.setData("oke");
                            }
                            else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.GET_FRIEND_IN_GROUP:
                            User user2 = (User) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_GET_FRIEND_IN_GROUP);
                            resultData.setData(new UserDAO().searchFriends(user2));
                            break;
                        case ObjectWrapper.SHARE_FILE_TO_USER:
                            FilePerms fp = (FilePerms) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_SHARE_FILE_TO_USER);
                            if (new FileDAO().addFilePermission(fp)) {
                                resultData.setData("ok");
                            }
                            else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.SHARE_FILE_TO_GROUP:
                            FilePerms fp1 = (FilePerms) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_SHARE_FILE_TO_GROUP);
                            Group g = new Group();
                            g.setGroup_id(fp1.getUser().getUser_id());
                            try {
                            ArrayList<User> u5 =(ArrayList<User>) new GroupDAO().searchAllUserInGroup(g);
                                System.out.println(u5);
                                for (User u1 : u5) {
//                                    System.out.println(u1.getUser_name());
                                    fp1.setUser(u1);
                                    new FileDAO().addFilePermission(fp1);
                                }
                                resultData.setData("ok");
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.GET_GROUPS:
                            User user_group = (User) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_GET_GROUPS);
                            resultData.setData(new GroupDAO().searchGroup(user_group));
                            break;
                        case ObjectWrapper.CREATE_GROUP:
                            GroupxUser gu = (GroupxUser) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_CREATE_GROUP);
                            if (new GroupDAO().createGroup(gu.getUser(), gu.getGroup())) {
                                resultData.setData("ok");
                            }
                            else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.ADD_USER_TO_GROUP:
                            GroupxUser gu1 = (GroupxUser) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_ADD_USER_TO_GROUP);
                            if (new GroupDAO().addUser(gu1.getUser(), gu1.getGroup())) {
                                resultData.setData("ok");
                            }
                            else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.SEARCH_ALL_USER:
                            user = (User) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_USER);
                            resultData.setData(new UserDAO().searchAllUser(user.getUser_id()));
                            break;

                        case ObjectWrapper.SEARCH_FRIEND: // search friend
//                            System.out.println("search friend section");
                            UserDAO uDao = new UserDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_FRIEND);
                            user = (User) receivedData.getData();
                            resultData.setData(uDao.searchFriends(user));
                            break;


                        case ObjectWrapper.FRIEND_REQUEST:
                            resultData.setPerformative(ObjectWrapper.REPLY_FRIEND_REQUEST);
                            uDao = new UserDAO();
                            user = (User) receivedData.getData();
                            resultData.setData(uDao.searchAddFriendRequest(user));
                            break;

                        case ObjectWrapper.DELETE_FRIEND:
                            resultData.setPerformative(ObjectWrapper.REPLY_DELETE_FRIEND);
                            Friend friend = (Friend) receivedData.getData();
//                            System.out.println("svudp-278: " + friend.getUser2().getUser_id());
                            uDao = new UserDAO();
                            uDao.deleteFriend(friend);
                            resultData.setData(uDao.searchFriends(friend.getUser1()));

                        case ObjectWrapper.ACCEPT_FRIEND_REQUEST:
                            resultData.setPerformative(ObjectWrapper.REPLY_FRIEND_REQUEST);
                            uDao = new UserDAO();
                            friend = (Friend) receivedData.getData();
//                            uDao.acceptFriend(friend);
                            resultData.setData(uDao.acceptFriend(friend));
                            break;

                        case ObjectWrapper.IGNORE_FRIEND_REQUEST:
                            resultData.setPerformative(ObjectWrapper.REPLY_FRIEND_REQUEST);
                            uDao = new UserDAO();
                            friend = (Friend) receivedData.getData();
//                            uDao.acceptFriend(friend);
                            resultData.setData(uDao.ignoreFriend(friend));
                            break;

                        case ObjectWrapper.SEND_FRIEND_REQUEST:
                            resultData.setPerformative(ObjectWrapper.RESULT_REQUEST);
                            uDao = new UserDAO();
                            friend = (Friend) receivedData.getData();
                            resultData.setData(uDao.sendFriendRequest(friend));
                            break;

                        case ObjectWrapper.SET_STATUS_OFFLINE:
                            uDao = new UserDAO();
                            user = (User) receivedData.getData();
                            uDao.setUserStatusOffline(user.getUser_id());
                            break;
                        case ObjectWrapper.CHECK_PERMS_FILE:
                            resultData.setPerformative(ObjectWrapper.REPLY_CHECK_PERMS_FILE);
                            FilePerms fp4 = (FilePerms) receivedData.getData();
                            int file_id = new FileDAO().findIDFromName(fp4.getFile());
                            file f2 = new file();
                            f2.setFile_id(file_id);
                            fp4.setFile(f2);
                            System.out.println(fp4.getFile().getFile_id() + "   " + fp4.getUser().getUser_id());
                            if (new FileDAO().checkPermToDownload(fp4)) {
                                resultData.setData("ok");
                            }
                            else {
                                resultData.setData("false");
                            }          
                            break;
                        case ObjectWrapper.CHECK_VALID_FILE:
                            resultData.setPerformative(ObjectWrapper.REPLY_CHECK_VALID_FILE);
                            file f4 = (file) receivedData.getData();
                            boolean res4 = new FileDAO().checkExistFile(f4);
                            if (res4) {
                                resultData.setData("ok");
                            }
                            else {
                                resultData.setData("false");
                            }
                            break;
                    }

                    //prepare the buffer and write the data to send into the buffer
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(resultData);
                    oos.flush();

                    //create data package and send
                    byte[] sendData = baos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    myServer.send(sendPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessage("Error when processing an incoming package");
                }
            }
        }
    }
}
