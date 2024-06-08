package app.zoo.database;

public class Pracownik {
    int id;
    String imie;
    private String nazwisko;
    private String pesel;
    private int haslo;

    public Pracownik(int id, String imie, String nazwisko, String pesel, int haslo) {
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

    public int getHaslo() {
        return haslo;
    }

    public int getID() {
        return id;
    }
}