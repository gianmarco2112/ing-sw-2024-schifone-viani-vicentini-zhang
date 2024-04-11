package ingsw.codex_naturalis.model.observerObservable;

import ingsw.codex_naturalis.exceptions.NoSuchNicknameException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Message;
import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.objective.ObjectiveCard;
import ingsw.codex_naturalis.model.enumerations.Color;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.gameplayPhase.Observer;

import java.util.*;

public class GameView extends Observable<Event> implements Observer<Game, Event> {

    private final Game model;


    public GameView(Game model){
        this.model = model;
    }



    private Player getPlayerByNickname(String nickname) throws NoSuchNicknameException {

        List<Player> playerOrder = model.getPlayerOrder();
        for (Player player : playerOrder) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        throw new NoSuchNicknameException();

    }


    public Color getColor(String nickname){

        Player player = getPlayerByNickname(nickname);

        return player.getColor();
    }
    public int getPoints(String nickname){
        Player player = getPlayerByNickname(nickname);
        return player.getPlayerArea().getPoints();
    }
    public Map<Symbol, Integer> getNumOfSymbols(String nickname){

        Player player = getPlayerByNickname(nickname);
        Map<Symbol, Integer> numOfSymbols = player.getPlayerArea().getNumOfSymbols();
        return Collections.unmodifiableMap(numOfSymbols);

    }
    public int getExtraPoints(String nickname){
        Player player = getPlayerByNickname(nickname);
        return player.getPlayerArea().getExtraPoints();
    }

    public Card.Immutable getInitialCard(String nickname){
        Player player = getPlayerByNickname(nickname);
        return player.getInitialCard().getImmutableCard();
    }
    public Card.Immutable getObjectiveCard(String nickname){
        Player player = getPlayerByNickname(nickname);
        return player.getPlayerArea().getObjectiveCard().getImmutableCard();
    }


    public Map<List<Integer>, Card.Immutable> getPlayerArea(String nickname){

        Player player = getPlayerByNickname(nickname);
        Map<List<Integer>, PlayableCard> area = player.getPlayerArea().getArea();
        Map<List<Integer>, Card.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            immutableArea.replace(key, area.get(key).getImmutableCard());
        }
        return Collections.unmodifiableMap(immutableArea);

    }
    public List<Card.Immutable> getHand(String nickname){

        Player player = getPlayerByNickname(nickname);
        List<PlayableCard> hand = player.getHand();
        List<Card.Immutable> immutableHand = new ArrayList<>();
        for (PlayableCard card : hand) {
            immutableHand.add(card.getImmutableCard());
        }
        return immutableHand;

    }

    public Card.Immutable getTopResourceCardDeck(){
        PlayableCard card = model.getResourceCardsDeck().getFirstCard();
        return card.getImmutableCard();
    }
    public Card.Immutable getTopGoldCardDeck(){
        PlayableCard card = model.getGoldCardsDeck().getFirstCard();
        return card.getImmutableCard();
    }
    public List<Card.Immutable> getRevealedResourceCards(){
        List<PlayableCard> revealedResourceCards = model.getRevealedResourceCards();
        List<Card.Immutable> immutableRevealedResourceCards = new ArrayList<>();
        for (PlayableCard card : revealedResourceCards) {
            immutableRevealedResourceCards.add(card.getImmutableCard());
        }
        return immutableRevealedResourceCards;
    }
    public List<Card.Immutable> getRevealedGoldCards(){
        List<PlayableCard> revealedGoldCards = model.getRevealedGoldCards();
        List<Card.Immutable> immutableRevealedGoldCards = new ArrayList<>();
        for (PlayableCard card : revealedGoldCards) {
            immutableRevealedGoldCards.add(card.getImmutableCard());
        }
        return immutableRevealedGoldCards;
    }
    public List<Card.Immutable> getCommonObjectiveCards(){
        List<ObjectiveCard> commonObjectiveCards = model.getCommonObjectiveCards();
        List<Card.Immutable> immutableCommonObjectiveCards = new ArrayList<>();
        for (ObjectiveCard card : commonObjectiveCards) {
            immutableCommonObjectiveCards.add(card.getImmutableCard());
        }
        return immutableCommonObjectiveCards;
    }

    public List<Message> getChat(){
        return model.getMessages();
    }



    @Override
    public void update(Game o, Event arg, String nickname) {
        notifyObservers(arg, nickname);
    }
}
