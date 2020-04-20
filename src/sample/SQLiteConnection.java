package sample;
import java.sql.*;

public class SQLiteConnection {
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
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
