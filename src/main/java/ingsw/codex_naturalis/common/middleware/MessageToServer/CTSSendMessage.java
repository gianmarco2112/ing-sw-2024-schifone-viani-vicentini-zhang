package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;
/**
 * Message from client to server: a player wants to send a message
 */

public class CTSSendMessage implements MessageToServer {

    private String receiver;
    private String content;
    /**
     * CTSSendMessage's constructor
     */
    public CTSSendMessage() {
    }
    /**
     * CTSSendMessage's constructor
     * @param receiver of the message
     * @param content of the message
     */
    public CTSSendMessage(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().sendMessage(clientSkeleton.getNickname(), receiver, content);
    }
    /**
     * Receiver's getter
     * @return receiver of the message
     */
    public String getReceiver() {
        return receiver;
    }
    /**
     * Receiver's setter
     * @param receiver (of the message) to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    /**
     * Content's getter
     * @return content of the message
     */
    public String getContent() {
        return content;
    }
    /**
     * Content's setter
     * @param content (of the message) to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}
