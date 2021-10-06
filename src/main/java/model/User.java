
package model;

import java.io.Serializable;


public class User implements Serializable{
    private int user_id;
    private String user_name;
    private String pass;
    private String name;
    private String tel;
    private int usaged;
    private String note;
    
    public User() {
        super();
    }
    
    public User(String user_name, String pass, String name, String tel, int usaged, String note) {
        super();
        this.user_name = user_name;
        this.pass = pass;
        this.name = name;
        this.tel = tel;
        this.usaged = usaged;
        this.note = note;
    }
//    public User(String user_name, String pass, String name, String tel) {
//        super();
//        User(user_name, pass, name, tel, "", "");
//    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUsaged() {
        return usaged;
    }

    public void setUsaged(int usaged) {
        this.usaged = usaged;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
