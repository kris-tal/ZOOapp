package app.zoo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Zwierze {
    private int id;
    private int gatunek;
    private String imie;
    private int poziomUmiejetnosci;
    private String nazwaGatunku; 
    private static Map<Integer, String> gatunki = new HashMap<>();

    public Zwierze(int id, int gatunek, String imie, int poziomUmiejetnosci) {
        this.id = id;
        this.gatunek = gatunek;
        this.imie = imie;
        this.poziomUmiejetnosci = poziomUmiejetnosci;
        if(gatunki.isEmpty()) {
            gatunki = fetchNazwaGatunku(gatunek); 
        }
        this.nazwaGatunku = gatunki.get(gatunek); 
    }

    private Map<Integer, String> fetchNazwaGatunku(int gatunekId) {
        Map<Integer, String> localGatunki = new HashMap<>();
        String query = "SELECT id, nazwa FROM gatunki"; 
        try (Connection connection = PsqlManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nazwa = resultSet.getString("nazwa");
                    localGatunki.put(id, nazwa);
                }
            }
            System.out.println("Species names fetched successfully");
        } catch (SQLException e) {
            System.out.println("SQLException thrown: " + e.getMessage());
        }
        return localGatunki;
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