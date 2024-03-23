package ingsw.codex_naturalis.model;

import java.time.LocalTime;
import java.util.*;

public class Message {

    private final String content;
    private final LocalTime sentTime;
    private final List<Player> receivers;


    public Message(String content, List<Player> receivers){
        this.content = content;
        this.receivers = new ArrayList<>(receivers);
        this.sentTime = LocalTime.now();
    }


    public String getContent() {
        return content;
    }

    public LocalTime getSentTime(){
        return sentTime;
    }

    public List<Player> getReceivers() {
        return receivers;
    }
}
