module app.zoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.zoo to javafx.fxml;
    exports app.zoo;
}