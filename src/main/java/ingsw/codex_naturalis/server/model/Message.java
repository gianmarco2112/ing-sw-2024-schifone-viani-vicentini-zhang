package ingsw.codex_naturalis.server.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MessageEvent's class
 */
public class Message {

    /**
     * Content of the message
     */
    private String content;
    /**
     * Time the message was sent
     */
    private String sentTime;

    /**
     * Sender of the message
     */
    private String sender;
    /**
     * Receivers of the message
     */
    private List<String> receivers;


    /**
     * Message's constructor
     * @param content: Content of the message
     * @param sender: Sender of the message
     * @param receivers: Receivers of the message
     */
    public Message(String content, String sender, List<String> receivers){
        this.content = content;
        this.sender = sender;
        this.receivers = new ArrayList<>(receivers);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.sentTime = localTime.format(dateTimeFormatter);
    }

    /**
     * Content's getter
     * @return Content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Sent time's getter
     * @return Sent time
     */
    public String getSentTime(){
        return sentTime;
    }

    /**
     * Receivers' getter
     * @return List of receivers
     */
    public List<String> getReceivers() {
        return receivers;
    }

    /**
     * Sender's getter
     * @return Sender
     */
    public String getSender() {
        return sender;
    }
}
