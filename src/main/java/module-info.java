module javafx-poc {
    requires javafx.fxml;
    requires javafx.controls;
    opens javafx-poc to javafx.graphics;
    exports javafx-poc;
}
