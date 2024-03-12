module ingsw.codex_naturalis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ingsw.codex_naturalis to javafx.fxml;
    exports ingsw.codex_naturalis;
}