package ingsw.codex_naturalis.common.middleware.MessageToClient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.client.ServerStub;

/**
 * This interface represents a message sent to a socket client.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = STCReportException.class, name = "STCReportException"),

        @JsonSubTypes.Type(value = STCNicknameChosen.class, name = "STCNicknameChosen"),
        @JsonSubTypes.Type(value = STCGamesSpecsUpdated.class, name = "STCGamesSpecsUpdated"),

        @JsonSubTypes.Type(value = STCGameJoined.class, name = "STCGameJoined"),

        @JsonSubTypes.Type(value = STCAllPlayersJoined.class, name = "STCAllPlayersJoined"),

        @JsonSubTypes.Type(value = STCSetupUpdated.class, name = "STCSetupUpdated"),
        @JsonSubTypes.Type(value = STCInitialCardUpdated.class, name = "STCInitialCardUpdated"),
        @JsonSubTypes.Type(value = STCColorUpdated.class, name = "STCColorUpdated"),
        @JsonSubTypes.Type(value = STCObjectiveCardChosen.class, name = "STCObjectiveCardChosen"),

        @JsonSubTypes.Type(value = STCSetupEnded.class, name = "STCSetupEnded"),

        @JsonSubTypes.Type(value = STCCardFlipped.class, name = "STCCardFlipped"),
        @JsonSubTypes.Type(value = STCCardPlayed.class, name = "STCCardPlayed"),
        @JsonSubTypes.Type(value = STCCardDrawn.class, name = "STCCardDrawn"),
        @JsonSubTypes.Type(value = STCTurnChanged.class, name = "STCTurnChanged"),
        @JsonSubTypes.Type(value = STCMessageSent.class, name = "STCMessageSent"),

        @JsonSubTypes.Type(value = STCTwentyPointsReached.class, name = "STCTwentyPointsReached"),
        @JsonSubTypes.Type(value = STCDecksEmpty.class, name = "STCDecksEmpty"),
        @JsonSubTypes.Type(value = STCGameEnded.class, name = "STCGameEnded"),
        @JsonSubTypes.Type(value = STCGameCanceled.class, name = "STCGameCanceled"),
        @JsonSubTypes.Type(value = STCGameLeft.class, name = "STCGameLeft"),
        @JsonSubTypes.Type(value = STCGameRejoined.class, name = "STCGameRejoined"),
        @JsonSubTypes.Type(value = STCUpdatePlayerInGameStatus.class, name = "STCUpdatePlayerInGameStatus"),
        @JsonSubTypes.Type(value = STCGameToCancelLater.class, name = "STCGameToCancelLater"),
        @JsonSubTypes.Type(value = STCGameResumed.class, name = "STCGameResumed"),
        @JsonSubTypes.Type(value = STCPlayerIsReady.class, name = "STCPlayerIsReady"),
})
public interface MessageToClient {
    void run(ServerStub serverStub);

}
