package ingsw.codex_naturalis.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ingsw.codex_naturalis.common.Client;
import ingsw.codex_naturalis.server.util.GameObserver;
import ingsw.codex_naturalis.common.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.server.model.Game;
import ingsw.codex_naturalis.server.model.Message;
import ingsw.codex_naturalis.server.model.player.Player;
import ingsw.codex_naturalis.server.model.util.GameEvent;
import ingsw.codex_naturalis.server.model.util.PlayerEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation server-side of the client's view, it receives the updates from the model and
 * forwards them to the client through the network.
 */
public class VirtualView implements GameObserver {

    private final Client client;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public VirtualView(Client client) {
        this.client = client;
    }


    public Client getClient(){
        return client;
    }


    @Override
    public void update(Game game, GameEvent gameEvent) {

        if (gameEvent == GameEvent.MESSAGE) {
            messageCase(game);
            return;
        }

        if (gameEvent == GameEvent.GAME_STATUS_CHANGED)
            switch (game.getGameStatus()) {
                case GAMEPLAY -> {
                    setClientsViewAsGameplay(game);
                    return;
                }
                case LAST_ROUND_20_POINTS -> {

                }
            }

        try {
            Game.Immutable immGame = game.getImmutableGame(client.getNickname());
            client.stcUpdateSetupUI(objectMapper.writeValueAsString(immGame), objectMapper.writeValueAsString(gameEvent));
        } catch (RemoteException | JsonProcessingException e){
            System.err.println("Error while updating client");
        }

    }

    private void setClientsViewAsGameplay(Game game) {

            try {
                Game.Immutable immGame = game.getImmutableGame(client.getNickname());
                ObjectMapper objectMapper = new ObjectMapper();
                client.setViewAsGameplay(objectMapper.writeValueAsString(immGame));
            } catch (RemoteException | JsonProcessingException e) {
                System.err.println("Error while updating client");
            }

    }

    private void messageCase(Game game) {

        Message message = game.getChat().getLast();
        List<String> playersInvolved = new ArrayList<>();
        playersInvolved.add(message.getSender());
        playersInvolved.addAll(message.getReceivers());

        try {
            if (playersInvolved.contains(client.getNickname())) {
                Game.Immutable immGame = game.getImmutableGame(client.getNickname());
                client.stcUpdateGameplayUI(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }




    @Override
    public void update(Game game, PlayerEvent playerEvent, Player playerWhoUpdated) {
        switch (playerEvent) {
            case INITIAL_CARD_FLIPPED -> initialCardCase(game, playerWhoUpdated, InitialCardEvent.FLIP);
            case INITIAL_CARD_PLAYED -> initialCardCase(game, playerWhoUpdated, InitialCardEvent.PLAY);
            case COLOR_SETUP -> colorCase(game, playerWhoUpdated);
            case OBJECTIVE_CARD_CHOSEN -> objectiveCardCase(game, playerWhoUpdated);
            case HAND_CARD_FLIPPED -> privateCase(game, playerWhoUpdated);
            case HAND_CARD_PLAYED, CARD_DRAWN -> publicCase(game);
        }
    }

    private void publicCase(Game game) {

        try {
            Game.Immutable immGame = game.getImmutableGame(client.getNickname());
            client.stcUpdateGameplayUI(objectMapper.writeValueAsString(immGame));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

    private void privateCase(Game game, Player playerWhoUpdated) {

        try {
            if (client.getNickname().equals(playerWhoUpdated.getNickname())) {
                Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
                client.stcUpdateGameplayUI(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }


    private void objectiveCardCase(Game game, Player playerWhoUpdated) {

        try {
            if (client.getNickname().equals(playerWhoUpdated.getNickname())) {
                Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
                client.stcUpdateSetupUIObjectiveCardChoice(objectMapper.writeValueAsString(immGame));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

    private void colorCase(Game game, Player playerWhoUpdated) {

        try {
            if (client.getNickname().equals(playerWhoUpdated.getNickname()))
                client.stcUpdateSetupUIColor(objectMapper.writeValueAsString(playerWhoUpdated.getColor()));
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

    private void initialCardCase(Game game, Player playerWhoUpdated, InitialCardEvent initialCardEvent) {

        try {
            if (client.getNickname().equals(playerWhoUpdated.getNickname())) {
                Game.Immutable immGame = game.getImmutableGame(playerWhoUpdated.getNickname());
                client.stcUpdateSetupUIInitialCard(objectMapper.writeValueAsString(immGame), objectMapper.writeValueAsString(initialCardEvent));
            }
        } catch (RemoteException | JsonProcessingException e) {
            System.err.println("Error while updating client");
        }

    }

}
