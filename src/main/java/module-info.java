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

    exports ingsw.codex_naturalis.model.cards.initial to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.initial to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis.model.cards.gold to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.gold to com.fasterxml.jackson.databind;

    opens ingsw.codex_naturalis.model.cards.objective to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.objective to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis;


    exports ingsw.codex_naturalis.exceptions;
    exports;
    opens to
    exports;
    opens to
    exports ingsw.codex_naturalis.model.cards.initialResourceGold;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold.front;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.front to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold.back;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.back to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.enumerations;
    opens ingsw.codex_naturalis.model.enumerations to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies to com.fasterxml.jackson.databind;
}