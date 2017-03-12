package fr.miage.paris10.projetm1.helpu;

/**
 * Created by MBM on 07/03/2017.
 */

public class Message {
    private String from;
    private String destinataire;
    private String message;

    public Message(String from, String destinataire, String message) {
        this.from = from;
        this.destinataire = destinataire;
        this.message = message;
    }

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
