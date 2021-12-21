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
public class GroupxUser implements Serializable{
    private Group group;
    private User user;
    
    public GroupxUser() {
        super();
    }
    
    public GroupxUser(Group group, User user) {
        super();
        this.group = group;
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
