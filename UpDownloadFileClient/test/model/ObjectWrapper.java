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
    public static final int SEARCH_FRIEND = 13;
    public static final int REPLY_SEARCH_FRIEND = 14;
     
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