package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.distributed.Client;

import java.io.BufferedReader;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = STCGameStartingUIGameIDUpdate.class, name = "STCGameStartingUIGameIDUpdate"),
        @JsonSubTypes.Type(value = STCLobbyUIErrorReport.class, name = "STCLobbyUIErrorReport"),
        @JsonSubTypes.Type(value = STCLobbyUIGamesSpecsUpdate.class, name = "STCLobbyUIGamesSpecsUpdate"),
        @JsonSubTypes.Type(value = STCSetupUpdate.class, name = "STCSetupUpdate"),
        @JsonSubTypes.Type(value = STCUIUpdate.class, name = "STCUIUpdate"),
        @JsonSubTypes.Type(value = STCInitialCardUpdate.class, name = "STCInitialCardUpdate"),
        @JsonSubTypes.Type(value = STCColorUpdate.class, name = "STCColorUpdate"),
        @JsonSubTypes.Type(value = STCSetupUIErrorReport.class, name = "STCSetupUIErrorReport")
})
public interface MessageFromServer {

    void run(Client client, BufferedReader reader);

}
