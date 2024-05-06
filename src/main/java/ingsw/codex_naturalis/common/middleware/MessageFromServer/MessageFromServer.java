package ingsw.codex_naturalis.common.middleware.MessageFromServer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.common.Client;

import java.io.BufferedReader;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = STCLobbyUIErrorReport.class, name = "STCLobbyUIErrorReport"),
        @JsonSubTypes.Type(value = STCLobbyUIGamesSpecsUpdate.class, name = "STCLobbyUIGamesSpecsUpdate"),
        @JsonSubTypes.Type(value = STCSetupUpdate.class, name = "STCSetupUpdate"),
        @JsonSubTypes.Type(value = STCInitialCardUpdate.class, name = "STCInitialCardUpdate"),
        @JsonSubTypes.Type(value = STCColorUpdate.class, name = "STCColorUpdate"),
        @JsonSubTypes.Type(value = STCSetupUIErrorReport.class, name = "STCSetupUIErrorReport"),
        @JsonSubTypes.Type(value = STCObjectiveCardChoiceUpdate.class, name = "STCObjectiveCardChoiceUpdate"),
        @JsonSubTypes.Type(value = STCGameplayUIErrorReport.class, name = "STCGameplayUIErrorReport"),
        @JsonSubTypes.Type(value = STCGameplayUIUpdate.class, name = "STCGameplayUIUpdate"),
        @JsonSubTypes.Type(value = STCSetViewAsLobby.class, name = "STCSetViewAsLobby"),
        @JsonSubTypes.Type(value = STCSetViewAsGameStarting.class, name = "STCSetViewAsGameStarting"),
        @JsonSubTypes.Type(value = STCSetViewAsSetup.class, name = "STCSetViewAsSetup"),
        @JsonSubTypes.Type(value = STCSetViewAsGameplay.class, name = "STCSetViewAsGameplay"),
})
public interface MessageFromServer {

    void run(Client client, BufferedReader reader);

}
