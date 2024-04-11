package ingsw.codex_naturalis.model;


import ingsw.codex_naturalis.model.cards.Corner;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableSide;
import ingsw.codex_naturalis.model.cards.initialResourceGold.back.Back;
import ingsw.codex_naturalis.model.enumerations.PlayableCardType;
import ingsw.codex_naturalis.model.enumerations.Symbol;
import ingsw.codex_naturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PlayableCardTest {

    Player player;
    Game game;
    PlayableCard initialCard;

    @BeforeEach
    void setup(){
        player = new Player("P1");
        game = new Game(0,4);
        game.addPlayer(player);
        initialCard = new PlayableCard(
                "ITest",
                PlayableCardType.INITIAL,
                Symbol.EMPTY,
                new PlayableSide(
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false),
                        new Corner(Symbol.QUILL,false)),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.QUILL,Symbol.QUILL,Symbol.QUILL)));
    }
    private void setInitialCard(){
        player.setInitialCard(initialCard);
        player.playInitialCard();
    }
    private PlayableCard insectResourceCard(){
        PlayableCard resourceCard;
        resourceCard= new PlayableCard(
                "RTest",
                PlayableCardType.RESOURCE,
                Symbol.INSECT,
                new PlayableSide(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false)),
                new Back(
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        new Corner(Symbol.EMPTY,false),
                        List.of(Symbol.INSECT))
        );
        return(resourceCard);
    }
    @Test
    void testIsPlayable(){
        setInitialCard();
        PlayableCard card = insectResourceCard();
        assertTrue(card.isPlayable(player.getPlayerArea(),1,1));
        assertFalse(card.isPlayable(player.getPlayerArea(),3,3));
    }
    @Test
    void testPlayWithoutCoordinates(){
        initialCard.play(player.getPlayerArea());
        assertEquals(3,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
        for(int i = 0; i < 3; i++){
            player.getPlayerArea().decrNumOfSymbol(Symbol.QUILL);
        }
        initialCard.flip("Test");
        initialCard.play(player.getPlayerArea());
        assertEquals(4,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
    }
    @Test
    void testPlayWithCoordinates(){
        initialCard.flip("Test");
        setInitialCard();

        PlayableCard card = insectResourceCard();
        card.play(player.getPlayerArea(),1,1);
        assertEquals(3,player.getPlayerArea().getNumOfSymbol(Symbol.QUILL));
        assertEquals(1,player.getPlayerArea().getNumOfSymbol(Symbol.INSECT));
    }
}
