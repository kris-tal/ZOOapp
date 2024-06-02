package app.zoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PlanAktywnosciController extends ToolBarController{

    @FXML
    private Label dataLabel;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    private LocalDate currentDate;

    public void initialize() {
        super.initialize();
        currentDate = LocalDate.now();
        updateDateLabel();

        prevButton.setOnAction(event -> {
            if (currentDate.isAfter(LocalDate.now())) {
                currentDate = currentDate.minusDays(1);
                updateDateLabel();
            }
        });
        nextButton.setOnAction(event -> {
            currentDate = currentDate.plusDays(1);
            updateDateLabel();
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);
    }

    private void updateDateLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy", new Locale("pl", "PL"));
        String formattedDate = currentDate.format(formatter);
        dataLabel.setText(formattedDate);

        prevButton.setDisable(!currentDate.isAfter(LocalDate.now()));
    }
}
