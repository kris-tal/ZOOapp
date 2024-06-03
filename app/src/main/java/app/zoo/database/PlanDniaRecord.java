package app.zoo.database;

import java.sql.Time;
import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlanDniaRecord {
    private final SimpleIntegerProperty id;
    private final SimpleObjectProperty<LocalDate> data;
    private final SimpleObjectProperty<Time> godzina_od;
    private final SimpleObjectProperty<Time> godzina_do;
    private final SimpleIntegerProperty id_sprzatacza;
    private final SimpleIntegerProperty id_karmienia;
    private final SimpleIntegerProperty id_popisu;

    public PlanDniaRecord(int id, LocalDate data, Time godzina_od, Time godzina_do, Integer id_sprzatacza, Integer id_karmienia, Integer id_popisu) {
        this.id = new SimpleIntegerProperty(id);
        this.data = new SimpleObjectProperty<>(data);
        this.godzina_od = new SimpleObjectProperty<>(godzina_od);
        this.godzina_do = new SimpleObjectProperty<>(godzina_do);
        this.id_sprzatacza = (id_sprzatacza != null) ? new SimpleIntegerProperty(id_sprzatacza) : null;
        this.id_karmienia = (id_karmienia != null) ? new SimpleIntegerProperty(id_karmienia) : null;
        this.id_popisu = (id_popisu != null) ? new SimpleIntegerProperty(id_popisu) : null;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getData() {
        return data.get();
    }

    public void setData(LocalDate data) {
        this.data.set(data);
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleObjectProperty<LocalDate> dataProperty() {
        return data;
    }
}
