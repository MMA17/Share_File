/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Hoang Viet
 */
public class File implements Serializable{
    private int file_id;
    private String file_name;
    private int size;
    private int owner;
    private String file_extension;
    
    public File() {
        super();
    }
    
    public File(int file_id, String file_name, int size, int owner, String file_extension) {
        super();
        this.file_id = file_id;
        this.file_name = file_name;
        this.size = size;
        this.owner = owner;
        this.file_extension = file_extension;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }
    
}
