package ingsw.codex_naturalis.view.lobbyPhase;

import ingsw.codex_naturalis.distributed.local.ClientImpl;
import ingsw.codex_naturalis.distributed.local.ServerImpl;

public class Main {
    public static void main(String[] args){
        /*LobbyUI view = new LobbyTextualUI();
        view.run();*/

        ServerImpl server = new ServerImpl();
        ClientImpl client = new ClientImpl(server);
        client.run();
    }
}
