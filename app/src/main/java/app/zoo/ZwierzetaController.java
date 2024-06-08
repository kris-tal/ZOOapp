package app.zoo;

import app.zoo.database.PracownicyPolaczenie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private Button edytujButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button filtrujButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    int currentPage = 0;

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

    @FXML
    public void initialize() {
        super.initialize();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        gatunekColumn.setCellValueFactory(new PropertyValueFactory<>("gatunek"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        poziomUmiejetnosciColumn.setCellValueFactory(new PropertyValueFactory<>("poziomUmiejetnosci"));
        nazwaGatunkuColumn.setCellValueFactory(new PropertyValueFactory<>("nazwaGatunku"));

        try (Connection connection = PsqlManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM zwierzeta limit 10;");

            List<Zwierze> zwierzeta = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int gatunek = resultSet.getInt("gatunek");
                String imie = resultSet.getString("imie");
                int poziomUmiejetnosci = resultSet.getInt("poziom_umiejetnosci");
                zwierzeta.add(new Zwierze(id, gatunek, imie, poziomUmiejetnosci));
            }

            zwierzetaTable.getItems().setAll(zwierzeta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dodajButton.setOnAction(event -> openDodaj());
        edytujButton.setOnAction(event -> openEdytuj());
        prevButton.setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                zwierzetaTable.getItems().setAll(updateTable(currentPage * 30));
            }
            if(currentPage == 0) prevButton.setDisable(true);
        });
        nextButton.setOnAction(event -> {
            prevButton.setDisable(false);
            currentPage++;
            zwierzetaTable.getItems().setAll(updateTable(currentPage * 30));
        });

    }
}
