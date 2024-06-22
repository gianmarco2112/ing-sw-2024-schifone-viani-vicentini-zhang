module ingsw.codex_naturalis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires com.fasterxml.jackson.databind;
    requires java.rmi;
    requires java.desktop;
    requires com.google.common;

    opens ingsw.codex_naturalis to javafx.fxml;

    opens ingsw.codex_naturalis.server.model.cards.initialResourceGold to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.server.model.cards.initialResourceGold to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.cards.initialResourceGold.front to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.server.model.cards.initialResourceGold.front to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.server.model.cards.initialResourceGold.front.strategies to com.fasterxml.jackson.databind;


    exports ingsw.codex_naturalis.common.enumerations;
    exports ingsw.codex_naturalis.server.model.cards;

    exports ingsw.codex_naturalis.server.model.cards.initialResourceGold.back to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.cards.initialResourceGold.back to com.fasterxml.jackson.databind;

    opens ingsw.codex_naturalis.server.model.cards.objective to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.server.model.cards.objective to com.fasterxml.jackson.databind;

    exports ingsw.codex_naturalis;

    exports ingsw.codex_naturalis.common.middleware.MessageToClient;
    exports ingsw.codex_naturalis.common.middleware.MessageToServer;

    exports ingsw.codex_naturalis.client.view;
    exports ingsw.codex_naturalis.server.exceptions;
    exports ingsw.codex_naturalis.server.model.util to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.util to com.fasterxml.jackson.databind;
    exports ingsw.codex_naturalis.server.model.player to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.player to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.server.model.cards to com.fasterxml.jackson.databind;
    opens ingsw.codex_naturalis.common.enumerations to javafx.fxml;
    opens ingsw.codex_naturalis.common.middleware.MessageToServer to javafx.fxml;
    exports ingsw.codex_naturalis.client;
    opens ingsw.codex_naturalis.client to javafx.fxml;
    exports ingsw.codex_naturalis.server;
    opens ingsw.codex_naturalis.server to javafx.fxml;
    exports ingsw.codex_naturalis.common;
    opens ingsw.codex_naturalis.common to javafx.fxml;
    exports ingsw.codex_naturalis.server.util;
    opens ingsw.codex_naturalis.server.util to javafx.fxml;
    exports ingsw.codex_naturalis.common.util;
    opens ingsw.codex_naturalis.common.util to javafx.fxml;
    exports ingsw.codex_naturalis.server.model;
    opens ingsw.codex_naturalis.server.model to com.fasterxml.jackson.databind, javafx.fxml;

    exports ingsw.codex_naturalis.client.view.gui;
    opens ingsw.codex_naturalis.client.view.gui;
    opens ingsw.codex_naturalis.client.view to javafx.fxml;
    exports ingsw.codex_naturalis.common.immutableModel;
    opens ingsw.codex_naturalis.common.immutableModel to com.fasterxml.jackson.databind, javafx.fxml;
    exports ingsw.codex_naturalis.client.view.tui;
    opens ingsw.codex_naturalis.client.view.tui to javafx.fxml;
    exports ingsw.codex_naturalis.common.events;
}