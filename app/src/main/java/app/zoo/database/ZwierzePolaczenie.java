package app.zoo.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ZwierzePolaczenie {
    public static List<Zwierze> updateTable(int number) {
        List<Zwierze> zwierzeta = new ArrayList<>();
        try (Connection connection = PsqlManager.getConnection()) {
            Statement statement = connection.createStatement();
            System.out.println("Number: " + number);
            String query = "SELECT * FROM zwierzeta ORDER BY id ASC LIMIT 23 OFFSET " + number + ";";
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int gatunek = resultSet.getInt("gatunek");
                String imie = resultSet.getString("imie");
                int poziomUmiejetnosci = resultSet.getInt("poziom_umiejetnosci");
                zwierzeta.add(new Zwierze(id, gatunek, imie, poziomUmiejetnosci));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zwierzeta;
    }
}
