package ingsw.codex_naturalis.common.immutableModel;

import ingsw.codex_naturalis.common.enumerations.Symbol;

public record ImmPlayableCard (String cardID,
                               boolean showingFront,
                               String handCard,
                               String areaCard,
                               Symbol kingdom) {
}
