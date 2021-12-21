/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Hoang Viet
 */
public class usertogroup implements Serializable{
    private int user_id;
    private int group_id;
    
    public usertogroup() {
        super();
    }
    
    public usertogroup(int user_id, int group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
    
    
}
