package shop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public final class MysqlConnection {

    //singleton design pattern
    public Connection conn;
    private Statement statement;
    public static MysqlConnection db;

    private  MysqlConnection(){
        String url = "jdbc:mysql://localhost:3306/venditaricambi?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String dbName = "venditaricambi";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = null;

        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }

    }

    public static synchronized MysqlConnection getDbCon() {
        if (db == null) {
            db = new MysqlConnection();
        }
        return db;
    }

    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;

    }




    }
