module ingsw.codex_naturalis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires com.fasterxml.jackson.databind;
    requires java.rmi;

    opens ingsw.codex_naturalis to javafx.fxml;

    opens ingsw.codex_naturalis.model.cards.initialResourceGold to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.front to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold.front to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.initialResourceGold.front.strategies to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model  to com.fasterxml.jackson.databind;


    exports ingsw.codex_naturalis.enumerations;
    exports ingsw.codex_naturalis.view.gameplayPhase;
    exports ingsw.codex_naturalis.model.cards;
    exports ingsw.codex_naturalis.controller.setupPhase;

    exports ingsw.codex_naturalis.model.cards.initialResourceGold.back to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards.initialResourceGold.back to com.fasterxml.jackson.databind;

    opens ingsw.codex_naturalis.model.cards.objective to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.cards.objective to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis;

    exports ingsw.codex_naturalis.view;
    exports ingsw.codex_naturalis.exceptions;
    exports ingsw.codex_naturalis.model.observerObservable to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.observerObservable to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.model.player to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.player to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.model.cards to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.controller.gameplayPhase;
    exports ingsw.codex_naturalis.events.gameplayPhase;
    opens ingsw.codex_naturalis.enumerations to javafx.fxml;
    exports ingsw.codex_naturalis.distributed;
    opens ingsw.codex_naturalis.distributed to javafx.fxml;
    exports ingsw.codex_naturalis.distributed.socket;
    opens ingsw.codex_naturalis.distributed.socket to javafx.fxml;
}