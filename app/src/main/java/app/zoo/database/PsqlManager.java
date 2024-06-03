package app.zoo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PsqlManager {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "12345a";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Connection established successfully.");
    
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM pracownicy");
    
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    System.out.println("Number of records in 'pracownicy' table: " + count);
                }
    
                resultSet.close();
                statement.close();
                connection.close();
            } else {
                System.out.println("Failed to establish connection.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException thrown: " + e.getMessage());
        }
    }
}