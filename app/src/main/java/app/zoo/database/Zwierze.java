package app.zoo.database;

public class Zwierze {
    private int id;
    private int gatunek;
    private String imie;
    private int poziomUmiejetnosci;

    public Zwierze(int id, int gatunek, String imie, int poziomUmiejetnosci) {
        this.id = id;
        this.gatunek = gatunek;
        this.imie = imie;
        this.poziomUmiejetnosci = poziomUmiejetnosci;
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
}
