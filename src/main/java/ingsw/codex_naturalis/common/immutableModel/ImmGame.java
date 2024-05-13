package ingsw.codex_naturalis.common.immutableModel;

import ingsw.codex_naturalis.common.enumerations.GameStatus;

import java.util.List;

public record ImmGame(int gameID,
                      GameStatus gameStatus,
                      List<String> playerOrderNicknames,
                      String currentPlayerNickname,
                      List<ImmOtherPlayer> otherPlayers,
                      ImmPlayer player,
                      ImmPlayableCard topResourceCard,
                      List<ImmPlayableCard> revealedResourceCards,
                      ImmPlayableCard topGoldCard,
                      List<ImmPlayableCard> revealedGoldCards,
                      List<ImmObjectiveCard> commonObjectiveCards,
                      List<ImmMessage> chat) {

}
