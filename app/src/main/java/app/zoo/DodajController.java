package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

import app.zoo.database.PsqlManager;

public class DodajController extends ToolBarController {
    @FXML
    private TextField pole1, pole2, pole3, pole4, pole5, pole6, pole7;
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7;
    @FXML
    private CheckBox iloscCheckBox;
    @FXML
    private TextField iloscTextField;
    @FXML
    private ComboBox<String> tabelaComboBox;
    @FXML
    private Button potwierdzButton;

    private ArrayList<String> columnNames = new ArrayList<>();
    private ArrayList<String> columnTypes = new ArrayList<>();
    private int columnNumber;

    @Override
    public void initialize() {
        super.initialize();
        setupUI();
        setupDatabaseConnection();
    }

    private void setupUI() {
        iloscCheckBox.setDisable(true);
        iloscTextField.setDisable(true);
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6, pole7};
        Label[] etykiety = {label1, label2, label3, label4, label5, label6, label7};

        for (TextField pole : pola) {
            pole.setDisable(true);
            pole.setPromptText("");
        }
        for (Label etykieta : etykiety) {
            etykieta.setText("");
        }

        potwierdzButton.setOnAction(event -> handlePotwierdzButtonAction());
    }

    private void setupDatabaseConnection() {
        try (Connection connection = PsqlManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public'");
            while (resultSet.next()) {
                tabelaComboBox.getItems().add(resultSet.getString("table_name"));
            }
            tabelaComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> loadTableMetadata(newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTableMetadata(String tableName) {
        try (Connection connection = PsqlManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " WHERE 1=0")) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int totalColumnCount = metaData.getColumnCount();

            columnNames.clear();
            columnTypes.clear();
            columnNumber = 0;

            for (int i = 1; i <= totalColumnCount; i++) {
                String columnName = metaData.getColumnName(i);
                if (!columnName.equalsIgnoreCase("id")) {
                    columnNames.add(columnName);
                    columnTypes.add(metaData.getColumnTypeName(i));
                    columnNumber++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUI();
    }

    private void updateUI() {
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6};
        Label[] etykiety = {label1, label2, label3, label4, label5, label6};

        for (int i = 0; i < pola.length; i++) {
            if (i < columnNumber) {
                pola[i].setDisable(false);
                etykiety[i].setText(columnNames.get(i));
            } else {
                pola[i].setDisable(true);
                etykiety[i].setText("");
            }
        }
    }

    private void handlePotwierdzButtonAction() {
        try (Connection connection = PsqlManager.getConnection();
             PreparedStatement preparedStatement = prepareStatement(connection)) {
            preparedStatement.executeUpdate();
            System.out.println("Record added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add record.");
        }
    }

    private PreparedStatement prepareStatement(Connection connection) throws SQLException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < columnNumber; i++) {
            columns.append(columnNames.get(i)).append(i < columnNumber - 1 ? ", " : "");
            values.append("?").append(i < columnNumber - 1 ? ", " : "");
        }
        String insertQuery = "INSERT INTO " + tabelaComboBox.getValue() + " (" + columns + ") VALUES (" + values + ")";
        System.out.println(insertQuery);
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
    
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6};
        for (int i = 0; i < columnNumber; i++) {
            String type = columnTypes.get(i).toUpperCase();
            System.out.println("Type: " + type);
            try {
                switch (type) {
                    case "INTEGER":
                    case "INT4":
                        preparedStatement.setInt(i + 1, Integer.parseInt(pola[i].getText()));
                        break;
                    case "VARCHAR":
                    case "CHAR":
                    case "BPCHAR":
                    case "TEXT":
                        preparedStatement.setString(i + 1, pola[i].getText());
                        break;
                    case "TIME":
                        preparedStatement.setTime(i + 1, Time.valueOf(pola[i].getText()));
                        break;
                    case "DATE":
                        preparedStatement.setDate(i + 1, Date.valueOf(pola[i].getText()));
                        break;
                    default:
                        preparedStatement.setString(i + 1, pola[i].getText());
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing integer for column " + columnNames.get(i) + ": " + e.getMessage());
                // Handle the error appropriately, e.g., by setting a default value or skipping the record insertion.
            }
        }
        System.out.println(preparedStatement);
        return preparedStatement;
    }

    static public void openDodaj(Stage stage) {
        SceneLoader.loadScene("dodaj.fxml", stage);
    }
}