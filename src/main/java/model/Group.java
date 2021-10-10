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
public class Group implements Serializable{
    private int group_id;
    private String group_name;
    private String group_type;
    
    public Group() {
        super();
    }
    
    public Group(int group_id, String group_name, String group_type) {
        super();
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_type = group_type;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }
    
    
}
