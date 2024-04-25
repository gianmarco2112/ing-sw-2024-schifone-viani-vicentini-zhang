package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;

import java.io.BufferedReader;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CTSNewGameUpdate.class, name = "CTSNewGameUpdate"),
        @JsonSubTypes.Type(value = CTSGameToAccessUpdate.class, name = "CTSGameToAccessUpdate"),
        @JsonSubTypes.Type(value = CTSReadyUpdate.class, name = "CTSReadyUpdate"),
        @JsonSubTypes.Type(value = CTSInitialCardUpdate.class, name = "CTSInitialCardUpdate"),
        @JsonSubTypes.Type(value = CTSColorUpdate.class, name = "CTSColorUpdate"),
        @JsonSubTypes.Type(value = CTSObjectiveCardChoiceUpdate.class, name = "CTSObjectiveCardChoiceUpdate")
})
public interface MessageFromClient {

    void run(Client client, Server server, BufferedReader reader);

}
