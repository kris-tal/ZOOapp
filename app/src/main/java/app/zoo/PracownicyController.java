package app.zoo;

import javafx.fxml.FXML;
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
import app.zoo.database.Pracownik;

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
    public void initialize() {
        super.initialize();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        imieColumn.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwiskoColumn.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        hasloColumn.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        try (Connection connection = PsqlManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pracownicy");

            List<Pracownik> pracownicy = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String imie = resultSet.getString("imie");
                String nazwisko = resultSet.getString("nazwisko");
                String pesel = resultSet.getString("pesel");
                int haslo = resultSet.getInt("haslo");
                pracownicy.add(new Pracownik(id, imie, nazwisko, pesel, haslo));
            }

            mainTable.getItems().setAll(pracownicy);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}