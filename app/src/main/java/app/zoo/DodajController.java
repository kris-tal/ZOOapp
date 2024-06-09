package app.zoo;

import app.zoo.database.MojPracownik;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

import app.zoo.database.Pracownik;
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
                }
            }
    
            // Check if the table is "Pracownicy" and add custom fields
            if ("Pracownicy".equalsIgnoreCase(tableName)) {
                // Example of adding custom fields
                columnNames.add("id_stanowiska");
                columnTypes.add("INTEGER");
                columnNumber++;
    
                columnNames.add("id gatunku/klatki");
                columnTypes.add("INTEGER");
                columnNumber++;
            }
            if ("pracownicy_stanowiska".equalsIgnoreCase(tableName)) {
                columnNames.add("id gatunku/klatki");
                columnTypes.add("INTEGER");
                columnNumber++;
            }
            if("gatunki".equalsIgnoreCase(tableName))
            {
                columnNames.add("id opiekuna");
                columnTypes.add("INTEGER");
                columnNumber++;
            }
            if("wybiegi".equalsIgnoreCase(tableName))
            {
                columnNames.add("id sprzatacza");
                columnTypes.add("INTEGER");
                columnNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateUI();
    }

    private void updateUI() {
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6, pole7};
        Label[] etykiety = {label1, label2, label3, label4, label5, label6, label7};

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
        boolean isPracownicyTable = "Pracownicy".equalsIgnoreCase(tabelaComboBox.getValue());
        boolean isPracownicyStanowiskaTable = "pracownicy_stanowiska".equalsIgnoreCase(tabelaComboBox.getValue());
        boolean isWybiegiTable = "wybiegi".equalsIgnoreCase(tabelaComboBox.getValue());
        try (Connection connection = PsqlManager.getConnection()) {
            connection.setAutoCommit(false);

            if(isWybiegiTable) {
                String wybiegiQuery = "INSERT INTO wybiegi (strefa) VALUES (?);";
                try (PreparedStatement wybiegiStmt = connection.prepareStatement(wybiegiQuery, Statement.RETURN_GENERATED_KEYS)) {
                    wybiegiStmt.setInt(1, Integer.parseInt(pole1.getText())); 
                    wybiegiStmt.executeUpdate();
            
                    ResultSet generatedKeys = wybiegiStmt.getGeneratedKeys();
                    if (!generatedKeys.next()) {
                        throw new SQLException("Creating wybieg failed, no ID obtained.");
                    }
                    int id_wybiegu = generatedKeys.getInt(1);
            
                    String sprzataczeWybiegiQuery = "INSERT INTO sprzatacze_wybiegi (id_pracownika, id_wybiegu) VALUES (?, ?);";
                    try (PreparedStatement sprzataczeWybiegiStmt = connection.prepareStatement(sprzataczeWybiegiQuery)) {
                        sprzataczeWybiegiStmt.setInt(1, Integer.parseInt(pole2.getText())); 
                        sprzataczeWybiegiStmt.setInt(2, id_wybiegu); 
                        sprzataczeWybiegiStmt.executeUpdate();
                    }
            
                    connection.commit();
                    System.out.println("Transaction successful.");
                } catch (SQLException e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
                return;
            }

            if(isPracownicyStanowiskaTable) {
                String pracownicyStanowiskaQuery = "INSERT INTO pracownicy_stanowiska (id_pracownika, id_stanowiska, data_dodania) VALUES (?, ?, CURRENT_DATE)";
                try (PreparedStatement pracownicyStanowiskaStmt = connection.prepareStatement(pracownicyStanowiskaQuery)) {
                    pracownicyStanowiskaStmt.setInt(1, Integer.parseInt(pole1.getText()));
                    pracownicyStanowiskaStmt.setInt(2, Integer.parseInt(pole2.getText()));
                    pracownicyStanowiskaStmt.executeUpdate();
                    String tableName;
                    switch (Integer.parseInt(pole2.getText())) {
                        case 1:
                            tableName = "trenerzy_gatunki";
                            break;
                        case 2:
                            tableName = "opiekunowie_gatunki";
                            break;
                        case 3:
                            tableName = "sprzatacze_wybiegi";
                            break;
                        default:
                            tableName = "";
                            break;
                    }
    
                    if (!tableName.isEmpty() && (tableName.equals("trenerzy_gatunki") || tableName.equals("opiekunowie_gatunki"))) {
                        String dynamicQuery = String.format("INSERT INTO %s (id_pracownika, id_gatunku) VALUES (?, ?)", tableName);
                        try (PreparedStatement dynamicStmt = connection.prepareStatement(dynamicQuery)) {
                            dynamicStmt.setInt(1, Integer.parseInt(pole1.getText()));
                            dynamicStmt.setInt(2, Integer.parseInt(pole4.getText()));
                            dynamicStmt.executeUpdate();
                        }
                    }
                    else if (!tableName.isEmpty() && tableName.equals("sprzatacze_wybiegi")) {
                        String dynamicQuery = String.format("INSERT INTO %s (id_pracownika, id_wybiegu) VALUES (?, ?)", tableName);
                        try (PreparedStatement dynamicStmt = connection.prepareStatement(dynamicQuery)) {
                            dynamicStmt.setInt(1, Integer.parseInt(pole1.getText()));
                            dynamicStmt.setInt(2, Integer.parseInt(pole4.getText()));
                            dynamicStmt.executeUpdate();
                        }
                    }
    
                    connection.commit();
                    System.out.println("Transaction successful.");
                } catch (SQLException e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
                return;
            }
            boolean isGatunkiTable = "gatunki".equalsIgnoreCase(tabelaComboBox.getValue());

            if(isGatunkiTable) {
                String gatunkiQuery = "INSERT INTO gatunki (nazwa, id_wybiegu) VALUES (?, ?);";
                try (PreparedStatement gatunkiStmt = connection.prepareStatement(gatunkiQuery, Statement.RETURN_GENERATED_KEYS)) {
                    gatunkiStmt.setString(1, pole1.getText());
                    gatunkiStmt.setInt(2, Integer.parseInt(pole2.getText()));
                    gatunkiStmt.executeUpdate();
            
                    ResultSet generatedKeys = gatunkiStmt.getGeneratedKeys();
                    if (!generatedKeys.next()) {
                        throw new SQLException("Creating gatunek failed, no ID obtained.");
                    }
                    int gatunekId = generatedKeys.getInt(1);
            
                    String opiekunGatunekQuery = "INSERT INTO opiekunowie_gatunki (id_pracownika, id_gatunku) VALUES (?, ?);";
                    try (PreparedStatement opiekunGatunekStmt = connection.prepareStatement(opiekunGatunekQuery)) {
                        opiekunGatunekStmt.setInt(1, Integer.parseInt(pole3.getText()));
                        opiekunGatunekStmt.setInt(2, gatunekId);
                        opiekunGatunekStmt.executeUpdate();
                    }
            
                    connection.commit();
                    System.out.println("Transaction successful.");
                } catch (SQLException e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
                return;
            }
    
            if (isPracownicyTable) {
                String pracownicyQuery = "INSERT INTO pracownicy (imie, nazwisko, pesel, haslo) VALUES (?, ?, ?, ?);";
                try (PreparedStatement pracownicyStmt = connection.prepareStatement(pracownicyQuery, Statement.RETURN_GENERATED_KEYS)) {
                    pracownicyStmt.setString(1, pole1.getText());
                    pracownicyStmt.setString(2, pole2.getText());
                    pracownicyStmt.setString(3, pole3.getText()); // Assuming pole3 is pesel
                    pracownicyStmt.setString(4, pole4.getText()); // Assuming pole4 is haslo
                    pracownicyStmt.executeUpdate();
    
                    ResultSet rs = pracownicyStmt.getGeneratedKeys();
                    int pracownikId = 0;
                    if (rs.next()) {
                        pracownikId = rs.getInt(1); // Retrieve the generated id
                    }
    
                    String pracownicyStanowiskaQuery = "INSERT INTO pracownicy_stanowiska (id_pracownika, id_stanowiska, data_dodania) VALUES (?, ?, CURRENT_DATE)";
                    try (PreparedStatement pracownicyStanowiskaStmt = connection.prepareStatement(pracownicyStanowiskaQuery)) {
                        pracownicyStanowiskaStmt.setInt(1, pracownikId);
                        pracownicyStanowiskaStmt.setInt(2, Integer.parseInt(pole5.getText()));
                        pracownicyStanowiskaStmt.executeUpdate();
                    }
    
                    String tableName;
                    switch (Integer.parseInt(pole5.getText())) {
                        case 1:
                            tableName = "trenerzy_gatunki";
                            break;
                        case 2:
                            tableName = "opiekunowie_gatunki";
                            break;
                        case 3:
                            tableName = "sprzatacze_wybiegi";
                            break;
                        default:
                            tableName = "";
                            break;
                    }
    
                    if (!tableName.isEmpty() && (tableName.equals("trenerzy_gatunki") || tableName.equals("opiekunowie_gatunki"))) {
                        String dynamicQuery = String.format("INSERT INTO %s (id_pracownika, id_gatunku) VALUES (?, ?)", tableName);
                        try (PreparedStatement dynamicStmt = connection.prepareStatement(dynamicQuery)) {
                            dynamicStmt.setInt(1, pracownikId);
                            dynamicStmt.setInt(2, Integer.parseInt(pole6.getText()));
                            dynamicStmt.executeUpdate();
                        }
                    }
                    else if (!tableName.isEmpty() && tableName.equals("sprzatacze_wybiegi")) {
                        String dynamicQuery = String.format("INSERT INTO %s (id_pracownika, id_wybiegu) VALUES (?, ?)", tableName);
                        try (PreparedStatement dynamicStmt = connection.prepareStatement(dynamicQuery)) {
                            dynamicStmt.setInt(1, pracownikId);
                            dynamicStmt.setInt(2, Integer.parseInt(pole6.getText()));
                            dynamicStmt.executeUpdate();
                        }
                    }
    
                    connection.commit();
                    System.out.println("Transaction successful.");
                } catch (SQLException e) {
                    System.out.println("Transaction failed: " + e.getMessage());
                    connection.rollback();
                } finally {
                    connection.setAutoCommit(true);
                }
            } else {
                // Handle other table operations here if isPracownicyTable is false
                // This part of the code was missing in the original function
                System.out.println("Handling for non-Pracownicy tables not implemented.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to establish connection or handle transaction.");
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
    
        TextField[] pola = {pole1, pole2, pole3, pole4, pole5, pole6, pole7};
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
        if(!MojPracownik.getZarzadca()) {
            MojPracownik.brakUprawnien();
            return;
        }
        SceneLoader.loadScene("dodaj.fxml", stage);
    }
}