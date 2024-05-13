package ingsw.codex_naturalis.common.middleware.MessageToServer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.server.ClientSkeleton;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CTSViewIsReady.class, name = "CTSViewIsReady"),
        @JsonSubTypes.Type(value = CTSChooseNickname.class, name = "CTSChooseNickname"),
        @JsonSubTypes.Type(value = CTSAccessNewGame.class, name = "CTSAccessNewGame"),
        @JsonSubTypes.Type(value = CTSAccessExistingGame.class, name = "CTSAccessExistingGame"),
        @JsonSubTypes.Type(value = CTSGetGameController.class, name = "CTSGetGameController"),
        @JsonSubTypes.Type(value = CTSLeaveGame.class, name = "CTSLeaveGame"),

        @JsonSubTypes.Type(value = CTSReadyToPlay.class, name = "CTSReadyToPlay"),
        @JsonSubTypes.Type(value = CTSUpdateInitialCard.class, name = "CTSUpdateInitialCard"),
        @JsonSubTypes.Type(value = CTSChooseColor.class, name = "CTSChooseColor"),
        @JsonSubTypes.Type(value = CTSObjectiveCardChoiceUpdate.class, name = "CTSObjectiveCardChoiceUpdate"),
        @JsonSubTypes.Type(value = CTSFlipCard.class, name = "CTSFlipCard"),
        @JsonSubTypes.Type(value = CTSPlayCard.class, name = "CTSPlayCard"),
        @JsonSubTypes.Type(value = CTSDrawCard.class, name = "CTSDrawCard"),
        @JsonSubTypes.Type(value = CTSSendMessage.class, name = "CTSSendMessage"),
        @JsonSubTypes.Type(value = CTSImAlive.class, name = "CTSImAlive"),
})
public interface MessageToServer {

    void run(ClientSkeleton clientSkeleton);

}
