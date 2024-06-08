package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.zoo.database.PsqlManager;
import app.zoo.database.PracownicyPolaczenie;
import app.zoo.database.Pracownik;
import javafx.stage.Stage;

public class PracownicyController extends ToolBarController {
    @FXML
    private TableView<Pracownik> mainTable;
    @FXML
    private TableColumn<Pracownik, Integer> idColumn;
    @FXML
    private TableColumn<Pracownik, String> imieColumn;
    @FXML
    private TableColumn<Pracownik, String> nazwiskoColumn;
    @FXML
    private TableColumn<Pracownik, String> peselColumn;
    @FXML
    private TableColumn<Pracownik, Integer> hasloColumn;
    @FXML
    private Button dodajButton;
    @FXML
    private Button edytujButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button filtrujButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    private int currentPage = 0;

    //dac updateArray jako funkcje, to samo w zwierzetach (argument to ilosc offsetu)

    @FXML
    public void initialize() {
        super.initialize();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        hasloColumn.setCellValueFactory(new PropertyValueFactory<>("haslo"));


        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage)dodajButton.getScene().getWindow()));
        edytujButton.setOnAction(event -> EdytujController.openEdytuj((Stage)edytujButton.getScene().getWindow()));
        //usunButton.setOnAction(event -> usunPracownika());
        filtrujButton.setOnAction(event -> FiltrujController.openFiltruj());
        prevButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                mainTable.getItems().setAll(PracownicyPolaczenie.updateTable(currentPage * 30));
            }
            if(currentPage == 0) prevButton.setDisable(true);
        });
        nextButton.setOnAction(event -> {
            prevButton.setDisable(false);
            currentPage++;
            mainTable.getItems().setAll(PracownicyPolaczenie.updateTable(currentPage * 30));
        });
    }
}