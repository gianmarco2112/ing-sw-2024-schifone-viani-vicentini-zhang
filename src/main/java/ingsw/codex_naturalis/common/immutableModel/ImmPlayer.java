package ingsw.codex_naturalis.common.immutableModel;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;

import java.util.List;

public record ImmPlayer (String nickname,
                         Color color,
                         TurnStatus turnStatus,
                         ImmPlayableCard initialCard,
                         List<ImmObjectiveCard> secretObjectiveCards,
                         List<ImmPlayableCard> hand,
                         ImmPlayerArea playerArea) {
}
