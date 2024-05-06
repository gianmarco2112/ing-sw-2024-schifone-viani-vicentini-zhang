package ingsw.codex_naturalis.common.middleware.MessageFromClient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ingsw.codex_naturalis.common.middleware.ClientSkeleton;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CTSNewGameUpdate.class, name = "CTSNewGameUpdate"),
        @JsonSubTypes.Type(value = CTSGameToAccessUpdate.class, name = "CTSGameToAccessUpdate"),
        @JsonSubTypes.Type(value = CTSReadyUpdate.class, name = "CTSReadyUpdate"),
        @JsonSubTypes.Type(value = CTSInitialCardUpdate.class, name = "CTSInitialCardUpdate"),
        @JsonSubTypes.Type(value = CTSColorUpdate.class, name = "CTSColorUpdate"),
        @JsonSubTypes.Type(value = CTSObjectiveCardChoiceUpdate.class, name = "CTSObjectiveCardChoiceUpdate"),
        @JsonSubTypes.Type(value = CTSFlipCardUpdate.class, name = "CTSFlipCardUpdate"),
        @JsonSubTypes.Type(value = CTSPlayCardUpdate.class, name = "CTSPlayCardUpdate"),
        @JsonSubTypes.Type(value = CTSDrawCardUpdate.class, name = "CTSDrawCardUpdate"),
        @JsonSubTypes.Type(value = CTSSendMessageUpdate.class, name = "CTSSendMessageUpdate"),
        @JsonSubTypes.Type(value = CTSGetGameController.class, name = "CTSGetGameController"),
})
public interface MessageFromClient {

    void run(ClientSkeleton clientSkeleton);

}
