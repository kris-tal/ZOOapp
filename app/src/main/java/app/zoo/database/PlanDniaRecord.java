package app.zoo.database;

import java.sql.Time;
import java.time.LocalDate;

public class PlanDniaRecord {
    private Integer id;
    private LocalDate data;
    private Time godzina_od;
    private Time godzina_do;
    private Integer id_sprzatacza;
    private Integer id_karmienia;
    private Integer id_popisu;

    public PlanDniaRecord(int id, LocalDate data, Time godzina_od, Time godzina_do, Integer id_sprzatacza, Integer id_karmienia, Integer id_popisu) {
        this.id = id;
        this.data = data;
        this.godzina_od = godzina_od;
        this.godzina_do = godzina_do;
        this.id_sprzatacza = id_sprzatacza;
        this.id_karmienia = id_karmienia;
        this.id_popisu = id_popisu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Time getGodzinaOd() {
        return godzina_od;
    }

    public void setGodzinaOd(Time godzina_od) {
        this.godzina_od = godzina_od;
    }

    public Time getGodzinaDo() {
        return godzina_do;
    }

    public void setGodzinaDo(Time godzina_do) {
        this.godzina_do = godzina_do;
    }

    public Integer getIdSprzatacza() {
        return id_sprzatacza;
    }

    public void setIdSprzatacza(Integer id_sprzatacza) {
        this.id_sprzatacza = id_sprzatacza;
    }

    public Integer getIdKarmienia() {
        return id_karmienia;
    }

    public void setIdKarmienia(Integer id_karmienia) {
        this.id_karmienia = id_karmienia;
    }

    public Integer getIdPopisu() {
        return id_popisu;
    }

    public void setIdPopisu(Integer id_popisu) {
        this.id_popisu = id_popisu;
    }
}