package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//import jdbc.dao.UserDAO;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.LoginFrm;
import view.FilesFrm;
import view.SearchCustomerFrm;
import view.ClientMainFrm;
import view.DashboardFrm;
import view.DownloadFrm;
import view.EditCustomerFrm;
import view.FileManagerFrm;
import view.FriendFrm;
import view.GroupFrm;
import view.GroupManagerFrm;
import view.RegisterFrm;
import view.UploadFrm;

public class ClientCtr {

    private Socket mySocket;
    private FriendFrm friendFrm;
    private ClientMainFrm view;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost", 8888);  // default server host and port

    public ClientCtr(ClientMainFrm view) {
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            view.showMessage("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when connecting to the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when sending data to the server!");
            return false;
        }
        return true;
    }

    /*
    public Object receiveData(){
        Object result = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            result = ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when receiving data from the server!");
            return null;
        }
        return result;
    }*/
    public boolean closeConnection() {
        try {
            if (myListening != null) {
                myListening.stop();
            }
            if (mySocket != null) {
                mySocket.close();
                view.showMessage("Disconnected from the server!");
            }
            myFunction.clear();
        } catch (Exception e) {
            //e.printStackTrace();
            view.showMessage("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }

    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER) {
                            view.showMessage("Number of client connecting to the server: " + data.getData());
                        } else {
                            for (ObjectWrapper fto : myFunction) {
                                if (fto.getPerformative() == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_USER:
                                            LoginFrm loginView = (LoginFrm) fto.getData();
                                            loginView.receivedDataProcessing(data);
                                            //System.out.println("test_login");
                                            break;
                                        case ObjectWrapper.REPLY_GET_FILES:
                                            FilesFrm filesfrm = (FilesFrm) fto.getData();
                                            filesfrm.receivedDataProcessing(data);
                                            //System.out.println(data);
                                            break;
                                        case ObjectWrapper.REPLY_ADD_FILE:
//                                    FilesFrm filesfrm = (FilesFrm)fto.getData();
//                                    filesfrm.receivedDataProcessing(data);
//                                    System.out.println(data);
                                            break;
                                        case ObjectWrapper.REPLY_SEARCH_FRIEND:
                                            friendFrm = (FriendFrm) fto.getData();
                                            friendFrm.fillFriendList(data);  //fill data to jtable 1
                                            break;
                                        case ObjectWrapper.REPLY_SEARCH_ALL_USER:
                                            friendFrm = (FriendFrm) fto.getData();
                                            friendFrm.fillUserList(data);
                                            break;
                                        case ObjectWrapper.REPLY_FRIEND_REQUEST:
                                            friendFrm = (FriendFrm) fto.getData();
                                            friendFrm.displayFriendRequest(data);
                                            break;
                                        case ObjectWrapper.REPLY_SEARCH_USER:
                                            friendFrm = (FriendFrm) fto.getData();
                                            friendFrm.fillUserList(data);
                                            break;
                                        case ObjectWrapper.REPLY_REGISTER_USER:
                                            RegisterFrm resfrm = (RegisterFrm) fto.getData();
                                            resfrm.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_SHARE_FILE_TO_USER:
                                            FileManagerFrm fmf = (FileManagerFrm) fto.getData();
                                            fmf.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_GET_GROUPS:
                                            GroupFrm gfrm = (GroupFrm) fto.getData();
                                            gfrm.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_CREATE_GROUP:
                                            GroupFrm gfrm1 = (GroupFrm) fto.getData();
                                            gfrm1.checkCreateGroup(data);
                                            break;
                                        case ObjectWrapper.REPLY_GET_FRIEND_IN_GROUP:
                                            GroupManagerFrm gmf = (GroupManagerFrm) fto.getData();
                                            gmf.receivedDataProcessing1(data);
                                            break;
                                        case ObjectWrapper.REPLY_ADD_USER_TO_GROUP:
                                            GroupManagerFrm gmf1 = (GroupManagerFrm) fto.getData();
                                            gmf1.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_SHARE_FILE_TO_GROUP:
                                            FileManagerFrm fmff = (FileManagerFrm) fto.getData();
                                            fmff.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.RESULT_REQUEST:
                                            friendFrm = (FriendFrm) fto.getData();
                                            String res = (String) data.getData();
                                            friendFrm.showResult(res);
                                            break;
                                        case ObjectWrapper.REFRESH:
                                            System.out.println("REFRSH section");
                                            friendFrm = (FriendFrm) fto.getData();
                                            friendFrm.getFriendList();
                                            friendFrm.getUserList();
                                            friendFrm.getRequestList();
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_PERMS_FILE:
                                            DownloadFrm DF = (DownloadFrm) fto.getData();
                                            DF.checkPermFile(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_VALID_FILE:
                                            UploadFrm UF = (UploadFrm) fto.getData();
                                            UF.Upload(data);
                                            break;
                                    }
                                }
                            }
                            //view.showMessage("Received an object: " + data.getPerformative());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error when receiving data from the server!");
                view.resetClient();
            }
        }
    }
}
