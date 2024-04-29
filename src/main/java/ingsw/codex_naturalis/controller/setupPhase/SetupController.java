package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.enumerations.GameStatus;
import ingsw.codex_naturalis.events.setupPhase.ObjectiveCardChoice;
import ingsw.codex_naturalis.exceptions.ColorAlreadyChosenException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.player.Player;

public class SetupController {

    private final Game model;
    private int readyPlayers = 0;

    //--------------------------------------------------------------------------------------
    public SetupController(Game model) {
        this.model = model;
    }


    public Game getModel() {
        return model;
    }


    public synchronized void updateReady(){
        readyPlayers++;
        if (readyPlayers == model.getNumOfPlayers()) {
            model.setupResourceAndGoldCards();
            model.dealInitialCards();
            readyPlayers = 0;
        }
    }

    public synchronized void updateInitialCard(String nickname, InitialCardEvent initialCardEvent) {
        Player player = model.getPlayerByNickname(nickname);
        PlayableCard initialCard = player.getInitialCard();
        switch (initialCardEvent) {
            case FLIP -> player.flip(initialCard);
            case PLAY -> player.playInitialCard();
        }

    }

    public synchronized void updateColor(String nickname, Color color) throws ColorAlreadyChosenException{
        Player player = model.getPlayerByNickname(nickname);
        for (Player p : model.getPlayerOrder())
            if (p.getColor() == color)
                throw new ColorAlreadyChosenException();
        player.setColor(color);
        readyPlayers++;
        if (readyPlayers == model.getNumOfPlayers()) {
            model.setupHands();
            model.setupCommonObjectiveCards();
            model.setupSecretObjectiveCards();
            readyPlayers = 0;
        }
    }

    public synchronized void updateObjectiveCard(String nickname, ObjectiveCardChoice objectiveCardChoice) {
        Player player = model.getPlayerByNickname(nickname);
        ObjectiveCard objectiveCard;
        switch (objectiveCardChoice) {
            case CHOICE_1 -> {
                objectiveCard = player.getSecretObjectiveCards().removeFirst();
                model.getObjectiveCardsDeck().discardACard(player.getSecretObjectiveCards().removeFirst());
            }
            case CHOICE_2 -> {
                objectiveCard = player.getSecretObjectiveCards().removeLast();
                model.getObjectiveCardsDeck().discardACard(player.getSecretObjectiveCards().removeFirst());
            }
            default -> { return; }
        }
        player.chooseObjectiveCard(objectiveCard);
        readyPlayers++;
        if (readyPlayers == model.getPlayerOrder().size()) {
            model.shufflePlayerList();
            model.setGameStatus(GameStatus.GAMEPLAY);
        }
    }
}
