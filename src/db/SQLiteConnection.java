package db;
import java.sql.*;

public class SQLiteConnection {
    public static Connection Connector() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:resources/db/TestDB.s3db");
            System.out.println("conn");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            //todo Exception handle
        }
    }
}
