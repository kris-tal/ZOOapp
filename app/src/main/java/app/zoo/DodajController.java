package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import app.zoo.database.PsqlManager;

public class DodajController extends ToolBarController {
    @FXML
    private TextField pole1;
    @FXML
    private TextField pole2;
    @FXML
    private TextField pole3;
    @FXML
    private TextField pole4;
    @FXML
    private TextField pole5;
    @FXML
    private TextField pole6;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private CheckBox iloscCheckBox;
    @FXML
    private TextField iloscTextField;
    @FXML
    private ComboBox tabelaComboBox;

    @Override
    public void initialize() {
        super.initialize();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Ustaw połączenie z bazą danych korzystając z klasy PsqlManager
            connection = PsqlManager.getConnection();
            statement = connection.createStatement();
            
            // Zapytanie SQL do pobrania metadanych (nazw kolumn)
            String query = "SELECT * FROM nazwa_tabeli WHERE 1=0"; // Dostosuj nazwę tabeli
            resultSet = statement.executeQuery(query);
            
            // Uzyskaj metadane
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnNumber = metaData.getColumnCount();
            
            // Zapisz nazwy kolumn
            ArrayList<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnNumber; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            
            iloscCheckBox.setDisable(true);
            iloscTextField.setDisable(true);
            TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6};
            Label[] etykiety = {label1, label2, label3, label4, label5, label6};
            for (TextField pole : pola) {
                pole.setDisable(true);
            }
            
            // Ustawienie dostępnych pól i etykiet na podstawie metadanych
            for (int i = 0; i < columnNumber; i++) {
                if (i < pola.length) { // Zabezpieczenie przed wyjściem poza zakres
                    pola[i].setDisable(false);
                    etykiety[i].setText(columnNames.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static public void openDodaj(Stage stage) {
        SceneLoader.loadScene("dodaj.fxml", stage);
    }
}