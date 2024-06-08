package app.zoo.database;

public class MojPracownik {
private static int id;
private static String imie;
private static String nazwisko;
private static String pesel;
private static int haslo;
private static String role;
private static boolean permissions;

public MojPracownik(int id, String imie, String nazwisko, String pesel, int haslo) {
    MojPracownik.id = id;
    MojPracownik.imie = imie;
    MojPracownik.nazwisko = nazwisko;
    MojPracownik.pesel = pesel;
    MojPracownik.haslo = haslo;
    MojPracownik.role = PracownikDao.getPracownicyWithStanowiska(imie, nazwisko, pesel);
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

public static int getHaslo() {
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
}
