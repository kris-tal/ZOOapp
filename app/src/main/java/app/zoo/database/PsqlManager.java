package app.zoo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PsqlManager {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = ".";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}