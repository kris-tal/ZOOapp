package app.zoo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.zoo.database.Pracownik;
import app.zoo.database.PsqlManager;

public class Find {
public static Pracownik findEmployeeByUserIDAndPassword(int userID, String password) {
    Pracownik pracownik = null;
    displayAllEmployees();
    try (Connection connection = PsqlManager.getConnection()) {
        String sql = "SELECT * FROM pracownicy WHERE id = ? AND haslo = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            String pesel = resultSet.getString("pesel");
            int haslo = resultSet.getInt("haslo");
            pracownik = new Pracownik(userID, imie, nazwisko, pesel, haslo);
        } else {
            System.out.println("No employee found with given userID and password tak tu jestem");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pracownik;
}
public static void displayAllEmployees() {
    try (Connection connection = PsqlManager.getConnection()) {
        String sql = "SELECT * FROM pracownicy";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            String pesel = resultSet.getString("pesel");
            int haslo = resultSet.getInt("haslo");
            System.out.println("ID: " + id + ", Imie: " + imie + ", Nazwisko: " + nazwisko + ", Pesel: " + pesel + ", Haslo: " + haslo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
