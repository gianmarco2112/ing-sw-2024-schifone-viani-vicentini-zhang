package ingsw.codex_naturalis.common.middleware.MessageToServer;

import ingsw.codex_naturalis.server.ClientSkeleton;


public class CTSSendMessage implements MessageToServer {

    private String receiver;
    private String content;

    public CTSSendMessage() {
    }

    public CTSSendMessage(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public void run(ClientSkeleton clientSkeleton) {
        clientSkeleton.getGameControllerImpl().sendMessage(clientSkeleton.getNickname(), receiver, content);
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
