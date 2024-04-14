package ingsw.codex_naturalis.events.gameplayPhase;

public enum DrawCard {

    DRAW_FROM_RESOURCE_CARDS_DECK("Draw from the resource cards deck"),
    DRAW_FROM_GOLD_CARDS_DECK("Draw from the gold cards deck"),
    DRAW_REVEALED_RESOURCE_CARD_1("Draw the revealed resource card 1"),
    DRAW_REVEALED_RESOURCE_CARD_2("Draw the revealed resource card 2"),
    DRAW_REVEALED_GOLD_CARD_1("Draw the revealed gold card 1"),
    DRAW_REVEALED_GOLD_CARD_2("Draw the revealed gold card 2");

    private final String description;

    DrawCard(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
