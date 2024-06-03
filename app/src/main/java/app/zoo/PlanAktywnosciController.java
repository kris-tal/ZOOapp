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

public class PlanAktywnosciController extends ToolBarController{

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

    private LocalDate currentDate;

    public void initialize() {
        super.initialize();
        currentDate = LocalDate.now();
        updateDateLabel();
    
        // Set cell value factories
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        // Add similar lines for other TableColumn objects
    
        prevButton.setOnAction(event -> {
            if (currentDate.isAfter(LocalDate.now())) {
                currentDate = currentDate.minusDays(1);
                updateDateLabel();
            }
        });
        nextButton.setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            updateDateLabel();
        });
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);
        displayPlanDniaRecords();
    }

    private void displayPlanDniaRecords() {
        String query = "SELECT * FROM plan_dnia WHERE data = '" + currentDate + "'";
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
    
        tableView.setItems(data);
    }

    private void updateDateLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);

        prevButton.setDisable(!currentDate.isAfter(LocalDate.now()));
    }
}
