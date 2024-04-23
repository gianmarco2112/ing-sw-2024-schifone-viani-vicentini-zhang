package ingsw.codex_naturalis.distributed.socket.MessageFromClient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.distributed.Server;
import ingsw.codex_naturalis.distributed.socket.MessageFromServer.*;

import java.io.BufferedReader;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewGameUpdate.class, name = "NewGameUpdate"),
        @JsonSubTypes.Type(value = GameToAccessUpdate.class, name = "GameToAccessUpdate"),
        @JsonSubTypes.Type(value = ReadyUpdate.class, name = "ReadyUpdate"),
        @JsonSubTypes.Type(value = InitialCardUpdate.class, name = "InitialCardUpdate")
})
public interface MessageFromClient {

    void run(Client client, Server server, BufferedReader reader);

}
