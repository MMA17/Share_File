/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tuan-user
 */
public class GroupDAO extends DAO {

    public boolean addUser(int user_id, Group group, String note) {
        String sql = "SELECT * FROM tblgroup WHERE group_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, group.getGroup_id());
            ResultSet rs = ps.executeQuery();
//            System.out.println(rs);
            int row=rs.getRow();
//            System.out.println(rs.getInt("group_id"));
            if (row < 1) {
                return false;
            } else {
                sql = "INSERT INTO tbljoingroup(note, user_id, group_id) VALUES(?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, note);
                ps.setInt(2, user_id);
                ps.setInt(3, rs.getInt("group_id"));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
//    Overload
    public boolean addUser(int user_id, Group group){
        return addUser(user_id, group, "");
    }
            
            
    public boolean delUser(int user_id, Group group) {
        String sql = "DELETE FROM tbljoingroup WHERE user_id=? AND group_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, group.getGroup_id());
            ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delGroup(Group group) {
        String sql = "DELETE FROM tbljoingroup WHERE group_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, group.getGroup_id());
            ps.executeUpdate();

            sql = "DELETE FROM tblgroup WHERE group_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, group.getGroup_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean createGroup(int user_id, Group group, String note) {
        String sql = "INSERT INTO tblgroup(group_name, group_type) VALUES(?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, group.getGroup_name());
            ps.setString(2, group.getGroup_type());
            ps.executeUpdate();
            
//            addUser(user_id, group, note);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        sql = "select * from tblgroup where group_name = ?";
        int groupId = 0;
        try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, group.getGroup_name());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                groupId = rs.getInt("group_id");
            }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        
        sql = "INSERT INTO tbljoingroup(note, user_id, group_id) VALUES(?, ?, ?)";
        try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, note);
			ps.setInt(2, user_id);
			ps.setInt(3, groupId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        
        
        return true;
    }
    
    
//    Overload
    public boolean createGroup(int user_id, Group group) {
        return createGroup(user_id, group,"");
    }
}
