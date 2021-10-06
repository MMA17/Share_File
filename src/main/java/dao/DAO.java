package dao;

import java.sql.Connection;
import java.sql.DriverManager;


public class DAO {
    protected static Connection con;
    
    public DAO() {
        if (con == null) {
            String dbUrl="jdbc:mysql://localhost:3306/sharefile";
            String dbClass="com.mysql.cj.jdbc.Driver";
            try {
                Class.forName(dbClass);
//                con = DriverManager.getConnection(dbUrl, "root", "");
                con = DriverManager.getConnection(dbUrl, "root", "123456a@");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
