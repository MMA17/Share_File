package dao;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO extends DAO{
         
        public ArrayList<User> searchAllUser() {
            ArrayList<User> res = new ArrayList<User>();
            String query = "SELECT * FROM tblUser";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                //System.out.println(rs);
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPass(rs.getString("pass"));
                    user.setName(rs.getString("name"));
                    user.setUsaged(rs.getInt("usaged"));
                    user.setTel(rs.getString("tel"));
                    user.setNote(rs.getString("note"));
                    
                    res.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        public ArrayList<User> searchUserByName(String username) {
            ArrayList<User> res = new ArrayList<User>();
            String query = "SELECT * FROM tblUser WHERE user_name = ?";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                username = username.trim();
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                //System.out.println(rs);
                while (rs.next()) {
                    User user = new User();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPass(rs.getString("pass"));
                    user.setName(rs.getString("name"));
                    user.setUsaged(rs.getInt("usaged"));
                    user.setTel(rs.getString("tel"));
                    user.setNote(rs.getString("note"));
                    
                    res.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
        
        public User checkLogin(String username, String pass) {
            ArrayList<User> res = new ArrayList<User>();
            User user = new User();
            user.setUser_id(0);
            String query = "SELECT * FROM tblUser WHERE user_name = ? AND pass = ?";
            int count = 0;
            try {
                PreparedStatement ps = con.prepareStatement(query);
                username = username.trim();
                ps.setString(1, username);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                	user.setUser_id(rs.getInt("user_id"));
                    user.setUser_name(rs.getString("user_name"));
                    user.setPass(rs.getString("pass"));
                    user.setName(rs.getString("name"));
                    user.setUsaged(rs.getInt("usaged"));
                    user.setTel(rs.getString("tel"));
                    user.setNote(rs.getString("note"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return user;
        }
        
        public boolean addUser(String user_name, String pass, String name, String tel) {
            String query = "INSERT INTO tbluser (user_name, pass, name, tel) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                user_name = user_name.trim();
                name = name.trim();
                tel = tel.trim();
                ps.setString(1, user_name);
                ps.setString(2, pass);
                ps.setString(3, name);
                ps.setString(4, tel);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        
        public boolean editUserInfo(String user_name, String name, String tel) {
            String query = "UPDATE tblUser SET name = ?, tel = ? WHERE user_name = ?";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                user_name = user_name.trim();
                name = name.trim();
                tel = tel.trim();
                ps.setString(3, user_name);
                ps.setString(1, name);
                ps.setString(2, tel);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        
        public boolean editUserPass(String user_name, String old_pass, String new_pass) {
            String query = "UPDATE tblUser SET pass = ? WHERE user_name = ? AND pass = ?";
            int res;
            try {
                PreparedStatement ps = con.prepareStatement(query);
                user_name = user_name.trim();
                ps.setString(1, new_pass);
                ps.setString(2, user_name);
                ps.setString(3, old_pass);
                res = ps.executeUpdate();
                //System.out.println(res);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (res == 0) {
                return false;
            }
            return true;
        }
        
        public boolean deleteUser(String user_name) {
            String query = "DELETE FROM tblUser WHERE user_name = ?";
            int res;
            try {
                PreparedStatement ps = con.prepareStatement(query);
                user_name = user_name.trim();
                ps.setString(1, user_name);
                res = ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (res == 0) {
                return false;
            }
            return true;
        }
}
