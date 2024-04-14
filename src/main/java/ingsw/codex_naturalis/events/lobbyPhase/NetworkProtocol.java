package ingsw.codex_naturalis.events.lobbyPhase;

public enum NetworkProtocol {

    RMI("Remote Method Interface - RMI"),
    TCP_IP("Transmission Control Protocol - TCP/IP");

    private final String description;

    NetworkProtocol(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
