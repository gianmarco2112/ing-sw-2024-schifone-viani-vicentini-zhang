package ingsw.codex_naturalis.model;

import java.time.LocalTime;
import java.util.*;

/**
 * Message class
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
     * Receivers of the message
     */
    private final List<Player> receivers;


    /**
     * Constructor
     * @param content Content
     * @param receivers Receivers
     */
    public Message(String content, List<Player> receivers){
        this.content = content;
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
    public List<Player> getReceivers() {
        return receivers;
    }
}
