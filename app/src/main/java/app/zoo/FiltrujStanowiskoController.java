package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FiltrujStanowiskoController {
    @FXML
    private Button potwierdzButton;
    @FXML
    private ListView<String> stanowiskaListView;

    private String filtrujStanowisko() {
        String stanowisko = stanowiskaListView.getSelectionModel().getSelectedItem();
        String filtr = "";

        if (stanowisko != null) {
            filtr = "WHERE stanowisko = " + stanowisko;
        }

        return filtr;
    }

    @FXML
    public void initialize() {
        //tutaj musisz wyciagnas ze stanowisk jakie sa stanowiska jako liste i je wrzucic do tego ponizej
        stanowiskaListView.getItems().addAll("Stanowisko 1", "Stanowisko 2", "Stanowisko 3");
        stanowiskaListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        stanowiskaListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Wybrane stanowisko: " + newValue); //tu masz to stanowisko ktore wybral
        });
        potwierdzButton.setOnAction(event -> filtrujStanowisko());  //i filtrujesz dane przez to stanowisko (mozesz to robic stringiem jak w moj plan)
    }

    @FXML
    public static void openFiltrujStanowisko() {
        SceneLoader.loadNewScene("filtruj-stanowisko.fxml");
    }
}
