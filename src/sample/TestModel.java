package sample;
import java.sql.*;

import java.sql.Connection;

public class TestModel {
    static Connection connection;

    public static Connection getConnection(){
        return connection;
    }

    public TestModel () {
        connection = SQLiteConnection.Connector();
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
