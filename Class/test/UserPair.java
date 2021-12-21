

import java.io.Serializable;

/**
 *
 * @author Tuan-user
 */
public class UserPair implements Serializable{
    private int senderId;
    private int recevierId;

    public UserPair() {
    }

    public UserPair(int senderId, int recevierId) {
        this.senderId = senderId;
        this.recevierId = recevierId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecevierId() {
        return recevierId;
    }

    public void setRecevierId(int recevierId) {
        this.recevierId = recevierId;
    }
    
    
}
