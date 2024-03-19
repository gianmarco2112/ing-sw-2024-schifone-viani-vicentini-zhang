package ingsw.codex_naturalis.model;

import com.fasterxml.jackson.core.type.TypeReference;
import ingsw.codex_naturalis.model.cards.initial.InitialCard;
import ingsw.codex_naturalis.model.cards.resource.ResourceCard;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Game {

    private List<Player> playerOrder;

    private Player currentPlayer;

    private List<ResourceCard> resourceCardsDeck;
    public static final String resourceCardsJsonFilePath = "src/main/resources/ingsw/codex_naturalis/resources/test.json";

    private CenterOfTable centerOfTable;

    // A fini di test terra terra
    public static void main(String[] args) throws IOException {
        Game gioco_test = new Game();
        System.out.println(gioco_test.resourceCardsDeck);
    }

    public Game() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.resourceCardsDeck = objectMapper.readValue(new File(this.resourceCardsJsonFilePath), new TypeReference<List<ResourceCard>>() {});
    }
}
