package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    static Connection connection;
    private static Logger log = Logger.getLogger(DBConnection.class.getName());

    public static Connection getConnection(){
        return connection;
    }

    public DBConnection() {
        connection = SQLiteConnect.Connector();
        if (connection == null) System.exit(1);
    }

    public static boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Exception", e);
            return false;
        }
    }
}
