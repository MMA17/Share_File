package model;
 
import java.io.Serializable;
 
public class ObjectWrapper  implements Serializable{
    private static final long serialVersionUID = 20210811011L;
    public static final int LOGIN_USER = 1;
    public static final int REPLY_LOGIN_USER = 2;
    public static final int EDIT_CUSTOMER = 3;
    public static final int REPLY_EDIT_CUSTOMER = 4;
    public static final int SEARCH_CUSTOMER_BY_NAME = 5;
    public static final int REPLY_SEARCH_CUSTOMER = 6;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 7;
    public static final int REFRESH = 8;
    public static final int GET_FILES = 9;
    public static final int REPLY_GET_FILES =10;
    public static final int ADD_FILE = 11;
    public static final int REPLY_ADD_FILE = 12;
    public static final int REGISTER_USER = 13;
    public static final int REPLY_REGISTER_USER = 14;
    public static final int SEARCH_FRIEND = 15;
    public static final int REPLY_SEARCH_FRIEND = 16;
    public static final int SHARE_FILE_TO_USER = 17;
    public static final int REPLY_SHARE_FILE_TO_USER = 18;
    public static final int GET_GROUPS = 19;
    public static final int REPLY_GET_GROUPS = 20;
    public static final int CREATE_GROUP = 21;
    public static final int REPLY_CREATE_GROUP = 22;
    public static final int ADD_USER_TO_GROUP = 23;
    public static final int REPLY_ADD_USER_TO_GROUP = 24;
    public static final int GET_FRIEND_IN_GROUP = 25;
    public static final int REPLY_GET_FRIEND_IN_GROUP = 26;
    public static final int FRIEND_REQUEST = 27;
    public static final int REPLY_FRIEND_REQUEST = 28;
    public static final int DELETE_FRIEND = 29;
    public static final int ACCEPT_FRIEND_REQUEST = 30;
    public static final int IGNORE_FRIEND_REQUEST = 31;
    public static final int SEND_FRIEND_REQUEST = 32;
    public static final int SEARCH_USER_BY_NAME = 33;
    public static final int REPLY_SEARCH_USER = 34;
    public static final int SEARCH_ALL_USER = 35;
    public static final int REPLY_SEARCH_ALL_USER = 36;
    public static final int SHARE_FILE_TO_GROUP = 37;
    public static final int REPLY_SHARE_FILE_TO_GROUP = 38;
    public static final int RESULT_REQUEST = 39;
    public static final int SET_STATUS_OFFLINE = 40;
    public static final int CHECK_VALID_FILE = 41;
    public static final int REPLY_CHECK_VALID_FILE = 42;
    public static final int CHECK_PERMS_FILE = 43;
    public static final int REPLY_CHECK_PERMS_FILE = 44;
    public static final int REPLY_DELETE_FRIEND = 45;
    private int performative;
    private Object data;
    public ObjectWrapper() {
        super();
    }
    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }
    public int getPerformative() {
        return performative;
    }
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }   
}