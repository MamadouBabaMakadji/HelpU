package fr.miage.paris10.projetm1.helpu;

/**
 * Created by MBM on 07/03/2017.
 */

public class Message {
    String from;
    String message;

    public Message() {
    }
    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
