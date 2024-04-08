package ingsw.codex_naturalis.model;

import ingsw.codex_naturalis.model.player.Player;

import java.time.LocalTime;
import java.util.*;

/**
 * MessageEvent class
 */
public class Message {

    /**
     * Content of the message
     */
    private final String content;
    /**
     * Sent time
     */
    private final LocalTime sentTime;

    /**
     * Sender of the message
     */
    private final String sender;
    /**
     * Receivers of the message
     */
    private final List<String> receivers;


    /**
     * Constructor
     * @param content Content
     * @param sender Sender
     * @param receivers Receivers
     */
    public Message(String content, String sender, List<String> receivers){
        this.content = content;
        this.sender = sender;
        this.receivers = new ArrayList<>(receivers);
        this.sentTime = LocalTime.now();
    }


    /**
     * Content getter
     * @return Content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sent time getter
     * @return Sent time
     */
    public LocalTime getSentTime(){
        return sentTime;
    }

    /**
     * Receivers getter
     * @return List of receivers
     */
    public List<String> getReceivers() {
        return receivers;
    }

    /**
     * Sender getter
     * @return Sender
     */
    public String getSender() {
        return sender;
    }
}
