package ingsw.codex_naturalis.distributed.socket.MessageFromServer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.distributed.Client;

import java.io.BufferedReader;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GameStartingUIGameIDUpdate.class, name = "GameStartingUIGameIDUpdate"),
        @JsonSubTypes.Type(value = LobbyUIErrorReport.class, name = "LobbyUIErrorReport"),
        @JsonSubTypes.Type(value = LobbyUIGamesSpecsUpdate.class, name = "LobbyUIGamesSpecsUpdate"),
        @JsonSubTypes.Type(value = Setup1Update.class, name = "Setup1Update"),
        @JsonSubTypes.Type(value = UIUpdate.class, name = "UIUpdate")
})
public interface MessageFromServer {

    void run(Client client, BufferedReader reader);

}
