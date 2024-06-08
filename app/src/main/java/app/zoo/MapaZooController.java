package app.zoo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.zoo.database.PsqlManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MapaZooController extends ToolBarController{
    @FXML
    private Button dodajButton;
    @FXML
    private Button edytujButton;
    @FXML
    private Button usunButton;
    @FXML
    private Button filtrujButton;
    @FXML
    private ComboBox<String> strefyComboBox;

    @FXML
    public void usunKrotke() {  //tu jako argument bedzie jakas krotka cze cos
        //usuniecie krotki z bazy danych
    }

    @Override
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage)dodajButton.getScene().getWindow()));
        edytujButton.setOnAction(event -> EdytujController.openEdytuj((Stage)edytujButton.getScene().getWindow()));
        usunButton.setOnAction(event -> usunKrotke());
        wypelnijStrefyComboBox();
    }
    private void wypelnijStrefyComboBox() {
        try (Connection connection = PsqlManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nazwa FROM strefy");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                strefyComboBox.getItems().add(resultSet.getString("nazwa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
