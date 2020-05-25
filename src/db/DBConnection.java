package db;
import main.Main;
import misc.sql.SQLCommands;

import java.sql.*;

import java.sql.Connection;

public class DBConnection {
    static Connection connection;

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
            //todo
            e.printStackTrace();
            return false;
        }
    }
}
