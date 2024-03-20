module ingsw.codex_naturalis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires com.fasterxml.jackson.databind;

    opens ingsw.codex_naturalis to javafx.fxml;

    opens ingsw.codex_naturalis.model.cards to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.resource to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.resource to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.enumerations to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis.model.cards.initial to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.initial to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis;
}