package app.zoo;

import app.zoo.database.MojPracownik;
import app.zoo.database.PsqlManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;

public class UsunController extends ToolBarController{
    @FXML
    private TextField pole1, pole2, pole3, pole4, pole5, pole6, pole7;
    @FXML
    private Label label1, label2, label3, label4, label5, label6, label7;
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
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6, pole7};
        Label[] etykiety = {label1, label2, label3, label4, label5, label6, label7};

        for (TextField pole : pola) {
            pole.setDisable(true);
            pole.setPromptText("");
        }
        for (Label etykieta : etykiety) {
            etykieta.setText("");
        }

        potwierdzButton.setOnAction(event -> {
            try {
                handlePotwierdzButtonAction();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to handle the 'Potwierdz' button action.");
            }
        });
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
                    System.out.println(columnName + " " + metaData.getColumnTypeName(i));
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

    private void handlePotwierdzButtonAction() throws SQLException {
    String tableName = tabelaComboBox.getSelectionModel().getSelectedItem();
    if (tableName == null || tableName.isEmpty()) {
        showAlert(AlertType.INFORMATION, "No table selected.");
        return;
    }

    StringBuilder queryBuilder = new StringBuilder("DELETE FROM " + tableName + " WHERE ");
    TextField[] fields = {pole1, pole2, pole3, pole4, pole5, pole6, pole7};
    boolean first = true;

    ArrayList<String> values = new ArrayList<>();
    ArrayList<String> filteredTypes = new ArrayList<>();
    for (int i = 0; i < columnNumber; i++) {
        if (!fields[i].isDisabled() && !fields[i].getText().isEmpty()) {
            if (!first) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(columnNames.get(i)).append(" = ?");
            values.add(fields[i].getText());
            filteredTypes.add(columnTypes.get(i));
            first = false;
        }
    }

    if (values.isEmpty()) {
        showAlert(AlertType.INFORMATION, "No criteria for deletion. Operation aborted.");
        return;
    }

    try (Connection connection = PsqlManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
        for (int i = 0; i < values.size(); i++) {
            String type = filteredTypes.get(i).toUpperCase();
            switch (type) {
                case "INTEGER":
                case "INT4":
                    preparedStatement.setInt(i + 1, Integer.parseInt(values.get(i)));
                    break;
                case "VARCHAR":
                case "CHAR":
                case "BPCHAR":
                case "TEXT":
                    preparedStatement.setString(i + 1, values.get(i));
                    break;
                case "DATE":
                    preparedStatement.setDate(i + 1, java.sql.Date.valueOf(values.get(i)));
                    break;
                default:
                    preparedStatement.setString(i + 1, values.get(i));
            }
        }
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows > 0) {
            showAlert(AlertType.INFORMATION, "Successfully deleted " + affectedRows + " rows.");
        } else {
            showAlert(AlertType.INFORMATION, "No rows were deleted.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(AlertType.ERROR, "Failed to execute delete operation.");
    }
}

private void showAlert(AlertType alertType, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle("Operation Status");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    static public void openUsun(Stage stage) {
        if(!MojPracownik.getZarzadca()) {
            MojPracownik.brakUprawnien();
            return;
        }
        SceneLoader.loadScene("usun.fxml", stage);
    }

}
