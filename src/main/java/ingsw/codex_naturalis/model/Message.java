package ingsw.codex_naturalis.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * MessageEvent class
 */
public class Message {

    /**
     * Content of the message
     */
    private String content;
    /**
     * Sent time
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
     * Constructor
     * @param content Content
     * @param sender Sender
     * @param receivers Receivers
     */
    public Message(String content, String sender, List<String> receivers){
        this.content = content;
        this.sender = sender;
        this.receivers = new ArrayList<>(receivers);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        this.sentTime = localTime.format(dateTimeFormatter);
    }

    public Message(){}


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
    public String getSentTime(){
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
