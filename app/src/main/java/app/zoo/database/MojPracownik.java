package app.zoo.database;

import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MojPracownik {
private static int id;
private static String imie;
private static String nazwisko;
private static String pesel;
private static String haslo;
private static String role;
private static boolean permissions;
private static boolean zarzadca;
public static Set<String> uprawnienia;

public MojPracownik(int id, String imie, String nazwisko, String pesel, String haslo) {
    this.id = id;
    this.imie = imie;
    this.nazwisko = nazwisko;
    this.pesel = pesel;
    this.haslo = haslo;
    this.uprawnienia = PracownikDao.getPracownicyWithStanowiska(imie, nazwisko, pesel);
    this.zarzadca = false;
    for(String uprawnienie : uprawnienia) {
        if(uprawnienie.trim().equalsIgnoreCase("zarzadca")) {
            this.zarzadca = true;
            break; 
        }
    }
}
public static void brakUprawnien() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Błąd");
    alert.setHeaderText(null);
    alert.setContentText("Nie masz uprawnień aby wykonać tę operację");

    alert.showAndWait();
}

public static String getImie() {
    return imie;
}

public static String getNazwisko() {
    return nazwisko;
}

public static String getPesel() {
    return pesel;
}

public static String getHaslo() {
    return haslo;
}

public static int getID() {
    return id;
}

public static String getRole() {
    return role;
}

public static boolean getPermissions() {
    return permissions;
}
public static boolean getZarzadca() {
    return zarzadca;
}
}
