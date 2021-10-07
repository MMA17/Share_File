/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Model.File;
import java.util.ArrayList;
/**
 *
 * @author Hoang Viet
 */
public class FileDAO extends DAO{
    // Tim tat ca cac file duoc nguoi dung tai len hoac duoc chia se
    public ArrayList<File> searchAllFilesOfUser(String user_id) {
        ArrayList<File> files = new ArrayList<File>();
        String query = 
                "SELECT tblfile.file_id, file_name, size, file_extension, readPermission, deletePermission "
                + "FROM tblpermission, tblfile "
                + "WHERE "
                + "(tblfile.owner = ? OR tblpermission.user_id = ?) "
                + "AND tblfile.file_id = tblpermission.file_id";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user_id);
            ps.setString(2, user_id);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                File file = new File();
                file.setFile_id(rs.getInt(0));
                file.setFile_name(rs.getString(1));
                file.setSize(rs.getInt(2));
                file.setFile_extension(rs.getString(3));
                
                files.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }
    
    public boolean addFile(String file_name, int size, int owner, String file_extension) {
        // Them file vao bang tblFile
        String query = "INSERT INTO tblFile (file_name, size, owner, file_extension) VALUES (?, ?, ?, ?)";
        int res;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, file_name);
            ps.setInt(2, size);
            ps.setInt(3, owner);
            ps.setString(4, file_extension);
            res = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // Lay file_id cua file vua duoc them vao bang tblFile de them vao bang tbl Permission
        query = "SELECT file_id FROM tblFile WHERE file_name = ? AND owner = ?";
        int file_id = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, file_name);
            ps.setInt(2, owner);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                file_id = rs.getInt("file_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        query = "INSERT INTO tblPermission (readPermission, deletePermission, user_id, file_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, 1);
            ps.setInt(2, 1);
            ps.setInt(3, owner);
            ps.setInt(4, file_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deleteFile(int file_id) {
        String query = "DELETE FROM tblPermission WHERE file_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, file_id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        query = "DELETE FROM tblFile WHERE file_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, file_id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public boolean addFilePermission(int readPermission, int deletePermission, int user_id, int file_id) {
        String query = "INSERT INTO tblPermission (readPermission, deletePermission, user_id, file_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, readPermission);
            ps.setInt(2, deletePermission);
            ps.setInt(3, user_id);
            ps.setInt(4, file_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
