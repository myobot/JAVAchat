package javachat.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wzw on 2016/12/19.
 */
class LCSQL{
    private String DBDRIVER="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/chatdb?useSSL=false";
    private Connection conn;
    public LCSQL(){
        try{
            Class.forName(DBDRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.conn= DriverManager.getConnection(url,"root","wang8970");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn(){
        return conn;
    }
}
public class LoadandConnectSQL {
    private static LCSQL lcsql=new LCSQL();
    private static Connection conn=lcsql.getConn();
    private LoadandConnectSQL(){
    }
    public static Connection getConn(){
        return conn;
    }
    public static void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}