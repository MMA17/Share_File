/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Hoang Viet
 */
public class FileDAO extends DAO{
    // Tim tat ca cac file duoc nguoi dung tai len hoac duoc chia se
    public ArrayList<file> searchAllFilesOfUser(User user) {
        ArrayList<file> files = new ArrayList<file>();
        String query = 
                "SELECT tblfile.file_id, file_name, size, file_extension, readPermission, deletePermission "
                + "FROM tblpermission, tblfile "
                + "WHERE "
                + "(tblfile.owner = ? OR tblpermission.user_id = ?) "
                + "AND tblfile.file_id = tblpermission.file_id";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getUser_id());
            ps.setInt(2, user.getUser_id());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                file file = new file();
                file.setFile_id(rs.getInt(1));
                file.setFile_name(rs.getString(2));
                file.setSize(rs.getInt(3));
                file.setFile_extension(rs.getString(4));
                
                files.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }
    
    public boolean addFile(file file) {
        // Them file vao bang tblFile
        String query = "INSERT INTO tblFile (file_name, size, owner, file_extension) VALUES (?, ?, ?, ?)";
        int res;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, file.getFile_name());
            ps.setInt(2, file.getSize());
            ps.setInt(3, file.getOwner());
            ps.setString(4, file.getFile_extension());
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
            ps.setString(1, file.getFile_name());
            ps.setInt(2, file.getOwner());
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
            ps.setInt(3, file.getOwner());
            ps.setInt(4, file_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean deleteFile(file file) {
        String query = "DELETE FROM tblPermission WHERE file_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, file.getFile_id());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        query = "DELETE FROM tblFile WHERE file_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, file.getFile_id());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public boolean addFilePermission(FilePerms fp) {
        String query = "INSERT INTO tblpermission (readPermission, deletePermission, user_id, file_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, fp.getReadPermission());
            ps.setInt(2, fp.getDeletePermission());
            ps.setInt(3, fp.getUser().getUser_id());
            ps.setInt(4, fp.getFile().getFile_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean checkExistFile (file f) {
        String query = "SELECT * FROM tblfile WHERE file_name = ?";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, f.getFile_name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (count != 0 ) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public boolean checkPermToDownload (FilePerms fp) {
        String query = "SELECT * FROM tblpermission WHERE user_id = ? AND file_id = ?";
        int res = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, fp.getUser().getUser_id());
            ps.setInt(2, fp.getFile().getFile_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt("readPermission");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (res == 1 ) {
            return true;
        }
        else if (res == 0) {
            return false;
        }
        return false;
    }
    
    public int findIDFromName (file f) {
        String query = "SELECT * FROM tblfile WHERE file_name = ?";
        int ID = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, f.getFile_name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ID = rs.getInt("file_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }
}
