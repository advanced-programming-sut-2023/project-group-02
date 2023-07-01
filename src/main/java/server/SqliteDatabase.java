package server;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDatabase {
    private static java.sql.Connection connection = null;

    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    public static java.sql.Connection getConnection() {
        return connection;
    }
}
