package app.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        strefyComboBox.setOnAction(event -> {
            wypelnijWybiegi(strefyComboBox.getValue());
            wypelnijTreeView();
        });
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
        for(Map.Entry<Integer, Set<String>> entry : strefy.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private void wypelnijTreeView() {
        TreeItem<String> root = new TreeItem<>("Wybiegi");
        ArrayList<TreeItem<String>> wybiegi = new ArrayList<>();

        for (Map.Entry<Integer, Set<String>> entry : strefy.entrySet()) {
            TreeItem<String> wybieg = new TreeItem<>("Wybieg " + entry.getKey());
            ArrayList<TreeItem<String>> gatunki = new ArrayList<>();

            for (String gatunek : entry.getValue()) {
                gatunki.add(new TreeItem<>(gatunek));
            }

            wybieg.getChildren().addAll(gatunki);
            wybiegi.add(wybieg);
        }

        root.getChildren().addAll(wybiegi);
        wybiegiTreeView.setRoot(root);
    }
}