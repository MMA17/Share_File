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
import java.util.ArrayList;

/**
 *
 * @author Tuan-user
 */
public class GroupDAO extends DAO {

    public boolean addUser(User user, Group group, String note) {
        String sql = "INSERT INTO tbljoingroup(user_id, group_id) VALUES( ?, ?)";
        try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, user.getUser_id());
                ps.setInt(2, group.getGroup_id());
                ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
//    Overload
    public boolean addUser(User user, Group group){
        return addUser(user, group, "");
    }
            
            
    public boolean delUser(User user, Group group) {
        String sql = "DELETE FROM tbljoingroup WHERE user_id=? AND group_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getUser_id());
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

    public boolean createGroup(User user, Group group) {
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
        
        sql = "INSERT INTO tbljoingroup( user_id, group_id) VALUES(?, ?)";
        try {
			PreparedStatement ps = con.prepareStatement(sql);
//                        System.out.println(user.getUser_id() + " " + groupId);
			ps.setInt(1, user.getUser_id());
			ps.setInt(2, groupId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        return true;
    }
    
    
//    Overload
    public ArrayList<Group> searchGroup(User user) {
        ArrayList<Group> groups = new ArrayList<>();
        String sql = "SELECT tblgroup.group_id,tblgroup.group_name,tblgroup.group_type FROM tblgroup,tbljoingroup WHERE user_id = ? AND tblgroup.group_id = tbljoingroup.group_id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getUser_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setGroup_id(rs.getInt("group_id"));
                group.setGroup_name(rs.getString("group_name"));
                group.setGroup_type(rs.getString("group_type"));
                
                groups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }
    
    public ArrayList<User> searchAllUserInGroup(Group group) {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM tbluser, tbljoingroup WHERE tbljoingroup.group_id= ? AND tbluser.user_id = tbljoingroup.user_id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, group.getGroup_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUser_id(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setUser_name(rs.getString("user_name"));
                u.setTel(rs.getString("tel"));
                u.setPass(rs.getString("pass"));
                u.setUsaged(rs.getInt("usaged"));
                u.setNote(rs.getString("note"));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
