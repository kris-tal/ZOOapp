package app.zoo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Zwierze {
    private int id;
    private int gatunek;
    private String imie;
    private int poziomUmiejetnosci;
    private String nazwaGatunku; // New field to store the species name

    public Zwierze(int id, int gatunek, String imie, int poziomUmiejetnosci) {
        this.id = id;
        this.gatunek = gatunek;
        this.imie = imie;
        this.poziomUmiejetnosci = poziomUmiejetnosci;
        this.nazwaGatunku = fetchNazwaGatunku(gatunek); // Fetch and set the species name during object creation
    }

    private String fetchNazwaGatunku(int gatunekId) {
        String nazwa = "";
        try {
            Connection connection = PsqlManager.getConnection();
            String query = "SELECT nazwa FROM gatunki WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gatunekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nazwa = resultSet.getString("nazwa");
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            System.out.println("Species name fetched successfully");
            System.out.println("Species name: " + nazwa);
        } catch (SQLException e) {
            System.out.println("SQLException thrown: " + e.getMessage());
        }
        return nazwa;
    }

    public int getId() {
        return id;
    }

    public int getGatunek() {
        return gatunek;
    }

    public String getImie() {
        return imie;
    }

    public int getPoziomUmiejetnosci() {
        return poziomUmiejetnosci;
    }

    public String getNazwaGatunku() {
        return nazwaGatunku;
    }
}