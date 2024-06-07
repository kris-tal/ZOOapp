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

import app.zoo.database.PlanDniaRecord;
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
    private Button dodajButton;
    @FXML
    private Button edytujButton;
    @FXML
    private Button usunButton;

    private LocalDate currentDate;

    @FXML
    public void openDodaj() {
        try {
            SceneLoader.loadScene("dodaj.fxml", new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openEdytuj() {
        try {
            SceneLoader.loadScene("edytuj.fxml", new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        super.initialize();
        currentDate = LocalDate.now();
        updateDateLabel();
    
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
    
        prevButton.setOnAction(event -> {
            if (currentDate.isAfter(LocalDate.now().minusDays(1))) { 
                currentDate = currentDate.minusDays(1);
                updateDateLabel();
                displayPlanDniaRecords(); 
            }
        });
        nextButton.setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            updateDateLabel();
            displayPlanDniaRecords(); 
        });

        dodajButton.setOnAction(event -> openDodaj());
        edytujButton.setOnAction(event -> openEdytuj());

        displayPlanDniaRecords(); 
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);
        displayPlanDniaRecords();
    }

    private void displayPlanDniaRecords() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCurrentDate = currentDate.format(formatter);
        System.out.println(formattedCurrentDate);
        String query = "SELECT * FROM plan_dnia WHERE data = '" + formattedCurrentDate + "'";
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
                Integer id_sprzatacza = (Integer) rs.getObject("id_sprzatacza");
                Integer id_karmienia = (Integer) rs.getObject("id_karmienia");
                Integer id_popisu = (Integer) rs.getObject("id_popisu");
    
                data.add(new PlanDniaRecord(id, date, godzina_od, godzina_do, id_sprzatacza, id_karmienia, id_popisu));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        tableView.setItems(null); 
        tableView.setItems(data);
    }

    private void updateDateLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);

        prevButton.setDisable(!currentDate.isAfter(LocalDate.now()));
    }
}
