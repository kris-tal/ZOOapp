package app.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.zoo.database.Pracownik;
import app.zoo.database.PsqlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class MapaZooController extends ToolBarController {
    @FXML
    private Button dodajButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button filtrujButton;
    @FXML
    private ComboBox<String> strefyComboBox; // Zmieniamy typ na ComboBox<Pair<String, Integer>> jeśli chcemy przechowywać zarówno nazwę jak i ID
    @FXML
    private TreeView<String> wybiegiTreeView;



    @Override
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage) dodajButton.getScene().getWindow()));
        usunButton.setOnAction(event -> UsunController.openUsun((Stage) usunButton.getScene().getWindow()));
        wypelnijStrefyComboBox();
        strefyComboBox.setOnAction(event -> wypelnijWybiegiTreeView(strefyComboBox.getValue()));
    }

    private void wypelnijStrefyComboBox() {
        try (Connection connection = PsqlManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nazwa FROM strefy");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String strefa = resultSet.getString("nazwa") + " (" + resultSet.getInt("id") + ")";
                strefyComboBox.getItems().add(strefa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void wypelnijWybiegiTreeView(String strefa) {
        int strefaId = Integer.parseInt(strefa.substring(strefa.indexOf('(') + 1, strefa.indexOf(')')));
        try (Connection connection = PsqlManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM wybiegi WHERE strefa = ?")) {
            preparedStatement.setInt(1, strefaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            wybiegiTreeView.setRoot(null);
            while (resultSet.next()) {
                TreeItem<String> wybiegItem = new TreeItem<>(String.valueOf(resultSet.getInt("id")));
                wybiegiTreeView.getRoot().getChildren().add(wybiegItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}