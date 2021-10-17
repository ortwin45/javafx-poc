module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires org.slf4j;
    requires opencv;

    opens ortwin to javafx.fxml;
    exports ortwin;
}