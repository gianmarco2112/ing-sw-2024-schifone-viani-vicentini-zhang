package ingsw.codex_naturalis.model.observerObservable;

import ingsw.codex_naturalis.exceptions.NoSuchNicknameException;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.Message;
import ingsw.codex_naturalis.model.cards.Card;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.player.Player;
import ingsw.codex_naturalis.view.playing.Observer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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


    public Map<List<Integer>, Card.Immutable> getPlayerArea(String nickname){

        Player player = getPlayerByNickname(nickname);
        Map<List<Integer>, PlayableCard> area = player.getPlayerArea().getArea();
        Map<List<Integer>, Card.Immutable> immutableArea = new LinkedHashMap<>();
        for (List<Integer> key : area.keySet()) {
            immutableArea.replace(key, area.get(key).getImmutableCard());
        }
        return immutableArea;

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

    public List<Message> getChat(){
        return model.getMessages();
    }

    //todo decks



    @Override
    public void update(Game o, Event arg, String nickname) {
        notifyObservers(arg, nickname);
    }
}
