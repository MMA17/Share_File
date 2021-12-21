package dao;

//import model.Group;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Friend;

public class UserDAO extends DAO {

    public ArrayList<User> searchAllUser(int userId) {
        ArrayList<User> res = new ArrayList<User>();
        String query = "SELECT * FROM tblUser where user_id <> ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
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
                user.setStatus(rs.getString("status"));
                res.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public String isSentRequest(int sender_id, int receiver_id) {
        String res = "none";
        String query = "select * from tblfriend where sender_id = ? and receiver_id = ?";
        System.out.println("UserDAO: " + sender_id + " " + receiver_id);
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, sender_id);
            ps.setInt(2, receiver_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tmp = rs.getString("status");
                if (tmp.equals("pending")) {
                    res = "sent";
                } else if (tmp.equals("done")) {
                    res = "friend";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<User> searchUserByName(String username) {
        ArrayList<User> res = new ArrayList<User>();
        String query = "SELECT * FROM tblUser WHERE user_name LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            username = username.trim();
            ps.setString(1, "%" + username + "%");
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
                user.setStatus(rs.getString("status"));
                res.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public User checkLogin(User user) {
        User res = new User();
        user.setUser_id(0);
        String query = "SELECT * FROM tblUser WHERE user_name = ? AND pass = ?";
        int count = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUser_name().trim());
            ps.setString(2, user.getPass().trim());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.setUser_id(rs.getInt("user_id"));
                res.setUser_name(rs.getString("user_name"));
                res.setPass(rs.getString("pass"));
                res.setName(rs.getString("name"));
                res.setUsaged(rs.getInt("usaged"));
                res.setTel(rs.getString("tel"));
                res.setNote(rs.getString("note"));
                res.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    public boolean addUser(User user) {
        String query = "INSERT INTO tbluser (user_name, pass, name, tel) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getPass());
            ps.setString(3, user.getName());
            ps.setString(4, user.getTel());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean editUserInfo(User user) {
        String query = "UPDATE tblUser SET name = ?, tel = ? WHERE user_name = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(3, user.getUser_name());
            ps.setString(1, user.getName());
            ps.setString(2, user.getTel());
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

    public boolean deleteUser(User user) {
        String query = "DELETE FROM tblUser WHERE user_name = ?";
        int res;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUser_name());
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

//    public boolean issendFriendRequest(int sender_id, int receiver_id) {
//        boolean check = false;
//        String query = "select * from tblfriend where sender_id = ? and receiver_id = ?";
//        System.out.println(sender_id + " " + receiver_id);
//        try {
//            PreparedStatement ps = con.prepareStatement(query);
//            ps.setInt(1, sender_id);
//            ps.setInt(2, receiver_id);
//            ResultSet rs = ps.executeQuery(query);
//            int count = rs.getRow();
//            if (count == 0) {
//                check = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return check;
//    }

    public String sendFriendRequest(Friend friend) {
        int user1 = friend.getUser1().getUser_id();
        int user2 = friend.getUser2().getUser_id();
        String res = isSentRequest(user1, user2);
        if (res.equals("none")) {
            String query = "INSERT INTO tblfriend VALUES (?, ?, 'pending')";
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, user1);
                ps.setInt(2, user2);
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            res = "done";
        }
        return res;
    }

    public ArrayList<User> searchAddFriendRequest(User u) {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM tbluser, tblfriend WHERE receiver_id = ? AND tblfriend.status = 'pending' AND tbluser.user_id = tblfriend.sender_id";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, u.getUser_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUser_name(rs.getString("user_name"));

                user.setName(rs.getString("name"));
                user.setUsaged(rs.getInt("usaged"));
                user.setTel(rs.getString("tel"));
                user.setNote(rs.getString("note"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> searchFriends(User u) {
        ArrayList<User> users = new ArrayList<User>();
//        String query = "SELECT * FROM tbluser, tblfriend WHERE (sender_id = ? OR receiver_id = ?) AND tblfriend.status = 'done' AND tbluser.user_id = tblfriend.receiver_id";
        String query = "SELECT * FROM tbluser, tblfriend WHERE (sender_id = ? OR receiver_id = ?) AND tblfriend.status = 'done' AND (tbluser.user_id = tblfriend.receiver_id or tbluser.user_id = tblfriend.sender_id) AND tbluser.user_id <> ? ";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, u.getUser_id());
            ps.setInt(2, u.getUser_id());
            ps.setInt(3, u.getUser_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setName(rs.getString("name"));
                user.setUsaged(rs.getInt("usaged"));
                user.setTel(rs.getString("tel"));
                user.setNote(rs.getString("note"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> acceptFriend(Friend friend) {
        int user1 = friend.getUser1().getUser_id();
        int user2 = friend.getUser2().getUser_id();
        String query = "UPDATE tblfriend SET status = 'done' WHERE sender_id = ? AND receiver_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchAddFriendRequest(friend.getUser2());
    }

    public ArrayList<User> ignoreFriend(Friend friend) {
        int user1 = friend.getUser1().getUser_id();
        int user2 = friend.getUser2().getUser_id();
        String query = "DELETE FROM tblfriend WHERE sender_id = ? AND receiver_id = ? ";//and status = 'pending'
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchAddFriendRequest(friend.getUser2());
    }

    public void setUserStatusOnline(int user_id) {
        String query = "UPDATE tbluser SET status = 'online' WHERE user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserStatusOffline(int user_id) {
        String query = "UPDATE tbluser SET status = 'offline' WHERE user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFriend(Friend friend) {
        System.out.println("user dao" + friend.getUser2().getUser_id());
        int user1 = friend.getUser1().getUser_id();
        int user2 = friend.getUser2().getUser_id();
        String sql = "delete from tblfriend where sender_id = ? and receiver_id = ? and status = 'done'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user1);
            ps.setInt(2, user2);
            ps.execute();
//            if (ps.execute()) {
            ps.setInt(1, user2);
            ps.setInt(2, user1);
            ps.executeUpdate();
//            }
        } catch (Exception e) {
            e.printStackTrace();
//            return false;
        }
//        return true;
    }

//        public ArrayList<Group> displayGroup(int userId)
//        {
//        	ArrayList<Group> res = new ArrayList<Group>();
//			String sql = "select * from tblgroup join tbljoingroup on tblgroup.group_id = tbljoingroup.group_id where tbljoingroup.user_id = ?";
//			try {
//                PreparedStatement ps = con.prepareStatement(sql);
//                ps.setInt(1, userId);
//                ResultSet rs = ps.executeQuery();
//                //System.out.println(rs);
//                while (rs.next()) {
//                    Group group = new Group();
//                    group.setGroup_id(rs.getInt("group_id"));
//                    group.setGroup_name(rs.getString("group_name"));
//                    group.setGroup_type(rs.getString("group_type"));
//                    
//                    res.add(group);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return res;
//		}
}
