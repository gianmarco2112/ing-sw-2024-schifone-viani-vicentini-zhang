package ingsw.codex_naturalis.common.middleware.MessageToClient;

import ingsw.codex_naturalis.client.ServerStub;
/**
 * Message from server to client: the game has been rejoined by a player
 */
public class STCGameRejoined implements MessageToClient {

    private String jsonImmGame;
    private String nickname;
    /**
     * STCGameRejoined's constructor
     */
    public STCGameRejoined() {
    }
    /**
     * STCGameRejoined's constructor
     * @param jsonImmGame : game where the player has rejoined
     * @param nickname : nickname of the player who rejoined
     */
    public STCGameRejoined(String jsonImmGame, String nickname) {
        this.jsonImmGame = jsonImmGame;
        this.nickname = nickname;
    }
    /**
     * To run the serverStub and send the message to the client that the game has been rejoined by a player
     */
    @Override
    public void run(ServerStub serverStub) {
        serverStub.getClient().gameRejoined(jsonImmGame, nickname);
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
     * Nickname's getter
     * @return nickname of the player who has rejoined the game
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * Nickname's setter
     * @param nickname : of the player who has rejoined the game
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
