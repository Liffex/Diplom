package db;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteConnect {
    private static Logger log = Logger.getLogger(SQLiteConnect.class.getName());
    public static Connection Connector() {
        SQLiteConfig config = new SQLiteConfig();
        config.enableCaseSensitiveLike(false);
        try {
            return DriverManager.getConnection("jdbc:sqlite:resources/db/EuphemismDB.s3db", config.toProperties());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception", e);
            return null;
            //todo Exception handle
        }
    }
}
