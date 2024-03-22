package ingsw.codex_naturalis.model;

import java.time.LocalTime;
import java.util.*;

public class Message {

    private String content;
    private LocalTime sentTime;
    private List<Player> receivers;


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
