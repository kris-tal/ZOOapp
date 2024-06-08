package app.zoo.database;

public class Pracownik {
    int id;
    String imie;
    private String nazwisko;
    private String pesel;
    private String haslo;

    public Pracownik(int id, String imie, String nazwisko, String pesel, String haslo) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public String getHaslo() {
        return haslo;
    }

    public int getID() {
        return id;
    }
}