package app.zoo.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyController {
    private PsqlManager psqlManager;

    public MyController(PsqlManager psqlManager) {
        this.psqlManager = psqlManager;
    }

    public void loadData() {
        try (Connection conne = PsqlManager.getConnection()) {
            Statement stmt = conne.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM popisy");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Trener: " + rs.getInt("trener"));
                System.out.println("Gatunek: " + rs.getInt("gatunek"));
                System.out.println("Min ilość: " + rs.getInt("min_ilosc"));
                System.out.println("Min poziom umiejętności: " + rs.getInt("min_poziom_umiejetnosci"));
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania danych: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PsqlManager psqlManager = new PsqlManager(); 
        MyController controller = new MyController(psqlManager); 
        controller.loadData(); 
    }
}