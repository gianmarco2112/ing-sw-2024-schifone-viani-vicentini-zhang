package ingsw.codex_naturalis.model;

import java.util.*;

public class Message {

    private String content;

    private List<Player> receivers;


    public Message(String content, List<Player> receivers){
        this.content = content;
        this.receivers = new ArrayList<>(receivers);
    }


    public String getContent() {
        return content;
    }

    public List<Player> getReceivers() {
        return receivers;
    }
}
