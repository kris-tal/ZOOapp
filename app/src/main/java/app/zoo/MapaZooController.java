package app.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    Map<Integer , Set<String>> strefy;


    @Override
    public void initialize() {
        super.initialize();
        dodajButton.setOnAction(event -> DodajController.openDodaj((Stage) dodajButton.getScene().getWindow()));
        usunButton.setOnAction(event -> UsunController.openUsun((Stage) usunButton.getScene().getWindow()));
        wypelnijStrefyComboBox();
        strefyComboBox.setOnAction(event -> wypelnijWybiegi(strefyComboBox.getValue()));
    }

    private void wypelnijStrefyComboBox() {
        TreeItem<String> rootItem = new TreeItem<>("Wybiegi");
        rootItem.setExpanded(true);
        for (Map.Entry<Integer, Set<String>> entry : strefy.entrySet()) {
            TreeItem<String> wybiegItem = new TreeItem<>(entry.getKey().toString());
            rootItem.getChildren().add(wybiegItem);
            for (String gatunek : entry.getValue()) {
                TreeItem<String> gatunekItem = new TreeItem<>(gatunek);
                wybiegItem.getChildren().add(gatunekItem);
            }
        }
        wybiegiTreeView.setRoot(rootItem);
    }

    private void wypelnijWybiegi(String strefa) {
    strefy = new HashMap<>();
    int strefaId = Integer.parseInt(strefa.substring(strefa.indexOf('(') + 1, strefa.indexOf(')')));

    try (Connection connection = PsqlManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM wybiegi WHERE strefa = ?")) {
            System.out.println("jestem tu");
        preparedStatement.setInt(1, strefaId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int wybiegId = resultSet.getInt("id");
            try (PreparedStatement speciesStmt = connection.prepareStatement("SELECT nazwa FROM gatunki WHERE id_wybiegu = ?")) {
                speciesStmt.setInt(1, wybiegId);
                ResultSet speciesResultSet = speciesStmt.executeQuery();
                Set<String> gatunkiSet = new HashSet<>();
                while (speciesResultSet.next()) {
                    gatunkiSet.add(speciesResultSet.getString("nazwa"));
                }
                strefy.put(wybiegId, gatunkiSet);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}