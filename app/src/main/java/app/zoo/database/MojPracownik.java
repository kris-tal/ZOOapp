package app.zoo.database;

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

public MojPracownik(int id, String imie, String nazwisko, String pesel, String haslo) {
    MojPracownik.id = id;
    MojPracownik.imie = imie;
    MojPracownik.nazwisko = nazwisko;
    MojPracownik.pesel = pesel;
    MojPracownik.haslo = haslo;
    MojPracownik.zarzadca = PracownikDao.getPracownicyWithStanowiska(imie, nazwisko, pesel);
    System.out.println(zarzadca);
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
