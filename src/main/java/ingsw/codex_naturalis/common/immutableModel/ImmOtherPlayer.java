package ingsw.codex_naturalis.common.immutableModel;

import ingsw.codex_naturalis.common.enumerations.Color;
import ingsw.codex_naturalis.common.enumerations.TurnStatus;

import java.util.List;

public record ImmOtherPlayer (String nickname,
                              Color color,
                              TurnStatus turnStatus,
                              List<ImmPlayableCard> hand,
                              ImmOtherPlayerArea playerArea) {
}
