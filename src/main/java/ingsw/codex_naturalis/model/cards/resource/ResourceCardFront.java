package ingsw.codex_naturalis.model.cards.resource;

import ingsw.codex_naturalis.model.Corner;
import ingsw.codex_naturalis.model.cards.PlayerAreaCard;
import ingsw.codex_naturalis.model.enumerations.Symbol;

public class ResourceCardFront extends PlayerAreaCard {

    public ResourceCardFront(Symbol kingdom, Corner topLeftCorner, Corner topRightCorner, Corner bottomLeftCorner, Corner bottomRightCorner, int points){
        super(kingdom, topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, points);
    }

}
