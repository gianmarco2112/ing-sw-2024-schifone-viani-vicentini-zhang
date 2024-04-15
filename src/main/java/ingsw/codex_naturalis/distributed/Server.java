package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.events.gameplayPhase.FlipCard;
import ingsw.codex_naturalis.events.gameplayPhase.Message;
import ingsw.codex_naturalis.events.gameplayPhase.PlayCard;
import ingsw.codex_naturalis.events.lobbyPhase.NetworkProtocol;
import ingsw.codex_naturalis.exceptions.NotYourTurnException;
import ingsw.codex_naturalis.exceptions.NotYourDrawTurnStatusException;
import ingsw.codex_naturalis.events.gameplayPhase.DrawCard;

import java.util.List;

public interface Server {

    void register(Client client);


    void updateNetworkProtocol(Client client, NetworkProtocol networkProtocol);
    void updateGameToAccess(Client client, int gameID, String nickname);
    void updateNewGame(Client client, int numOfPlayers, String nickname);


    void updateReady(Client client);


    void updateFlipCard(Client client, FlipCard flipCard);
    void updatePlayCard(Client client, PlayCard playCard, int x, int y) throws NotYourTurnException;
    void updateDrawCard(Client client, DrawCard drawCard) throws NotYourTurnException, NotYourDrawTurnStatusException;
    void updateText(Client client, Message message, String content, List<String> receivers);

}
