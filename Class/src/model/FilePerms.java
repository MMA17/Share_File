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
public class FilePerms implements Serializable{
    private int readPermission;
    private int deletePermission;
    private User user;
    private file file;
    
    public FilePerms() {
        super();
    }
    
    public FilePerms(int readPermission, int deletePermission, User user, file file) {
        this.readPermission = readPermission;
        this.deletePermission = deletePermission;
        this.user = user;
        this.file = file;
    }

    public int getReadPermission() {
        return readPermission;
    }

    public void setReadPermission(int readPermission) {
        this.readPermission = readPermission;
    }

    public int getDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(int deletePermission) {
        this.deletePermission = deletePermission;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public file getFile() {
        return file;
    }

    public void setFile(file file) {
        this.file = file;
    }

    
}
