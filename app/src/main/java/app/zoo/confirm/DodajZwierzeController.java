package app.zoo.confirm;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import app.zoo.database.PsqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DodajZwierzeController {
    @FXML
    private TextField gatunekField;
    @FXML
    private TextField imieField;
    @FXML
    private TextField poziomUmiejetnosciField;

    @FXML
    public void onZatwierdzButtonClick() {
        try (Connection connection = PsqlManager.getConnection()) {
            String query = "INSERT INTO zwierzeta (gatunek, imie, poziom_umiejetnosci) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(gatunekField.getText()));
            preparedStatement.setString(2, imieField.getText());
            preparedStatement.setInt(3, Integer.parseInt(poziomUmiejetnosciField.getText()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}