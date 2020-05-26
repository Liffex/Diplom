package db;
import org.sqlite.SQLiteConfig;

import java.sql.*;

public class SQLiteConnect {
    public static Connection Connector() {
        SQLiteConfig config = new SQLiteConfig();
        config.enableCaseSensitiveLike(false);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:resources/db/EuphemismDB.s3db", config.toProperties());
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            //todo Exception handle
        }
    }
}
