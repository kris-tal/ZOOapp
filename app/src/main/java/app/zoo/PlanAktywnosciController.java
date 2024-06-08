package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.sql.DriverManager;
import java.sql.Statement;

import app.zoo.database.MojPracownik;
import app.zoo.database.PlanDniaRecord;
import app.zoo.database.Pracownik;
import app.zoo.database.PsqlManager;
import javafx.stage.Stage;

public class PlanAktywnosciController extends ToolBarController {

    @FXML
    private Label dataLabel;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private TableView<PlanDniaRecord> tableView;
    @FXML
    private TableColumn<PlanDniaRecord, Integer> idColumn;
    @FXML
    private TableColumn<PlanDniaRecord, LocalDate> dataColumn;
    @FXML
    private TableColumn<PlanDniaRecord, Time> godzinaOdColumn;
    @FXML
    private TableColumn<PlanDniaRecord, Time> godzinaDoColumn;
    @FXML
    private TableColumn<PlanDniaRecord, Integer> idSprzataczaColumn;
    @FXML
    private TableColumn<PlanDniaRecord, Integer> idKarmieniaColumn;
    @FXML
    private TableColumn<PlanDniaRecord, Integer> idPopisuColumn;
    @FXML
    private Button dodajButton;
    @FXML
    private Button edytujButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button mojPlanButton;
    @FXML
    public Button filtrujButton;

    private LocalDate currentDate;

    private String mojPlanOption;
    private boolean mojPlan;


    public void initialize() {
        super.initialize();
        currentDate = LocalDate.now();
        updateDateLabel();
        mojPlanOption = "";
        mojPlan = false;

    
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        godzinaOdColumn.setCellValueFactory(new PropertyValueFactory<>("godzinaOd"));
        godzinaDoColumn.setCellValueFactory(new PropertyValueFactory<>("godzinaDo"));
        idSprzataczaColumn.setCellValueFactory(new PropertyValueFactory<>("idSprzatacza"));
        idKarmieniaColumn.setCellValueFactory(new PropertyValueFactory<>("idKarmienia"));
        idPopisuColumn.setCellValueFactory(new PropertyValueFactory<>("idPopisu"));
    
        prevButton.setOnAction(event -> {
            if (currentDate.isAfter(LocalDate.now().minusDays(1))) { 
                currentDate = currentDate.minusDays(1);
                updateDateLabel();
                displayPlanDniaRecords(mojPlanOption);
            }
        });
        nextButton.setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            updateDateLabel();
            displayPlanDniaRecords(mojPlanOption);
        });
        
        mojPlanButton.setOnAction(event -> {
            if(mojPlan) {
                mojPlan = false;
                mojPlanButton.setText("Mój plan");
                mojPlanOption = "";
                displayPlanDniaRecords(mojPlanOption);
                return;
            }
            mojPlan = true;
            mojPlanButton.setText("Cały plan");
            mojPlanOption = " AND (" + MojPracownik.getID() + " = id_sprzatacza" + " OR " + MojPracownik.getID() + " = id_karmienia" + " OR " + MojPracownik.getID() + " = id_popisu)";
            displayPlanDniaRecords(mojPlanOption);
        });

        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage)dodajButton.getScene().getWindow()));
        edytujButton.setOnAction(event -> EdytujController.openEdytuj((Stage)edytujButton.getScene().getWindow()));
        filtrujButton.setOnAction(event -> FiltrujController.openFiltruj());

        
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);
        displayPlanDniaRecords(mojPlanOption);
    }

    private void displayPlanDniaRecords(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCurrentDate = currentDate.format(formatter);
        System.out.println(formattedCurrentDate);
        String query = "SELECT * FROM plan_dnia WHERE data = '" + formattedCurrentDate + "'" + s + ";";
        System.out.println(query);
        ObservableList<PlanDniaRecord> data = FXCollections.observableArrayList();
    
        try (Connection conn = PsqlManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("data").toLocalDate();
                Time godzina_od = rs.getTime("godzina_od");
                Time godzina_do = rs.getTime("godzina_do");
                Integer id_sprzatacza = rs.getObject("id_sprzatacza") != null ? rs.getInt("id_sprzatacza") : null;
                Integer id_karmienia = rs.getObject("id_karmienia") != null ? rs.getInt("id_karmienia") : null;
                Integer id_popisu = rs.getObject("id_popisu") != null ? rs.getInt("id_popisu") : null;
    
                data.add(new PlanDniaRecord(id, date, godzina_od, godzina_do, id_sprzatacza, id_karmienia, id_popisu));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tableView.setItems(data);
    }

    private void updateDateLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);

        prevButton.setDisable(!currentDate.isAfter(LocalDate.now()));
    }
}
