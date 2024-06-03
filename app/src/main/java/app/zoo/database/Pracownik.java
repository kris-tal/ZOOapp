package app.zoo.database;

public class Pracownik {
    private int id;
    private String imie;
    private String nazwisko;
    private String pesel;
    private int haslo;
    private String role;
    private boolean permissions;

    public Pracownik(int id, String imie, String nazwisko, String pesel, int haslo) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.haslo = haslo;
        this.role = PracownikDao.getPracownicyWithStanowiska(imie, nazwisko, pesel);
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

    public String getRole() {
        return role;
    }

    public boolean getPermissions() {
        return permissions;
    }
}