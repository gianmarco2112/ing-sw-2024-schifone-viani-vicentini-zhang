package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: a card has been drawn
 */
public class STCCardDrawn implements MessageToClient {

    private String jsonImmGame;
    private String playerNicknameWhoUpdated;
    /**
     * STCCardDrawn's constructor
     */
    public STCCardDrawn() {
    }
    /**
     * STCCardDrawn's constructor
     * @param jsonImmGame : game where the card has been drawn
     * @param playerNicknameWhoUpdated : nickname of the player who has drawn the card
     */
    public STCCardDrawn(String jsonImmGame, String playerNicknameWhoUpdated) {
        this.jsonImmGame = jsonImmGame;
        this.playerNicknameWhoUpdated = playerNicknameWhoUpdated;
    }

    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().cardDrawn(jsonImmGame, playerNicknameWhoUpdated);
    }
    /**
     * JsonImmGame's getter
     * @return jsonImmGame
     */
    public String getJsonImmGame() {
        return jsonImmGame;
    }
    /**
     * JsonImmGame's setter
     * @param jsonImmGame to set
     */
    public void setJsonImmGame(String jsonImmGame) {
        this.jsonImmGame = jsonImmGame;
    }
    /**
     * PlayerNicknameWhoUpdated's getter
     * @return the nickname of the player who has drawn the card
     */
    public String getPlayerNicknameWhoUpdated() {
        return playerNicknameWhoUpdated;
    }
    /**
     * PlayerNicknameWhoUpdated's setter
     * @param playerNicknameWhoUpdated : the nickname of the player who has drawn the card
     */
    public void setPlayerNicknameWhoUpdated(String playerNicknameWhoUpdated) {
        this.playerNicknameWhoUpdated = playerNicknameWhoUpdated;
    }
}
