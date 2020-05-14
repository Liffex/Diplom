package db;
import org.sqlite.SQLiteConfig;

import java.sql.*;

public class SQLiteConnect {
    public static Connection Connector() {
        SQLiteConfig config = new SQLiteConfig();
        config.enableCaseSensitiveLike(false);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:resources/db/TestDB.s3db", config.toProperties());
            System.out.println("conn");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            //todo Exception handle
        }
    }
}
