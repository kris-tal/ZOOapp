package app.zoo.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PracownicyPolaczenie {
    public static List<Pracownik> updateTable(int number) {
        List<Pracownik> pracownicy = new ArrayList<>();
        try (Connection connection = PsqlManager.getConnection()) {
            Statement statement = connection.createStatement();
            System.out.println("Number: " + number);
            // Corrected SQL query to properly paginate results
            String query = "SELECT * FROM pracownicy ORDER BY id ASC LIMIT 23 OFFSET " + number + ";";
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String pesel = resultSet.getString("pesel");
                int haslo = resultSet.getInt("haslo");
                pracownicy.add(new Pracownik(id, imie, nazwisko, pesel, haslo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pracownicy;
    }
}
