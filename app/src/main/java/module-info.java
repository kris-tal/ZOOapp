module app.zoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens app.zoo to javafx.fxml;
    opens app.zoo.database to javafx.base;
    exports app.zoo;
}