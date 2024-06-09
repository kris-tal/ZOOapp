package app.zoo.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PracownikDao {
    public static boolean getPracownicyWithStanowiska(String username, String surname, String pesel) {
        String stanowisko = null;

        try (Connection conn = PsqlManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT s.nazwa as stanowisko s FROM pracownicy p JOIN pracownicy_stanowiska ps ON p.id = ps.id_pracownika JOIN stanowiska s ON ps.id_stanowiska = s.id WHERE p.imie = '" + username + "' AND p.nazwisko = '" + surname + "' AND p.pesel = '" + pesel + "'")) {

            if (rs.next()) {
                stanowisko = rs.getString("stanowisko");
                if(stanowisko == "zarządca") {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}