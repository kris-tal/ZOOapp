package app.zoo;

import app.zoo.database.MojPracownik;
import app.zoo.database.PracownicyPolaczenie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.zoo.database.PsqlManager;
import app.zoo.database.Zwierze;
import app.zoo.database.ZwierzePolaczenie;

public class ZwierzetaController extends ToolBarController {
    @FXML
    private TableView<Zwierze> zwierzetaTable;
    @FXML
    private TableColumn<Zwierze, Integer> idColumn;
    @FXML
    private TableColumn<Zwierze, Integer> gatunekColumn;
    @FXML
    private TableColumn<Zwierze, String> imieColumn;
    @FXML
    private TableColumn<Zwierze, Integer> poziomUmiejetnosciColumn;
    @FXML
    private TableColumn<Zwierze, String> nazwaGatunkuColumn;
    @FXML
    private Button dodajButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button zmienPoziomButton;

    int currentPage = 0;

    @FXML
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage) dodajButton.getScene().getWindow()));
        usunButton.setOnAction(event -> UsunController.openUsun((Stage) usunButton.getScene().getWindow()));
        zmienPoziomButton.setOnAction(event -> {
            if (MojPracownik.getZarzadca()) {
            UpdatePoziomController.updatujPoziom();
            } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Brak uprawnień");
            alert.setHeaderText(null);
            alert.setContentText("Nie masz uprawnień do wykonania tej operacji.");
            alert.showAndWait();
        }
        });
        prevButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                zwierzetaTable.getItems().setAll(ZwierzePolaczenie.updateTable(currentPage * 23));
            }
            if(currentPage == 0) prevButton.setDisable(true);
        });
        nextButton.setOnAction(event -> {
            prevButton.setDisable(false);
            currentPage++;
            zwierzetaTable.getItems().setAll(ZwierzePolaczenie.updateTable(currentPage * 23));
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        gatunekColumn.setCellValueFactory(new PropertyValueFactory<>("gatunek"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        poziomUmiejetnosciColumn.setCellValueFactory(new PropertyValueFactory<>("poziomUmiejetnosci"));
        nazwaGatunkuColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaGatunku"));
        zwierzetaTable.getItems().setAll(ZwierzePolaczenie.updateTable(currentPage * 23));



    }
}
