package fr.miage.paris10.projetm1.helpu;

/**
 * Created by MBM on 16/03/2017.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class Message {

    private String uid;
    private String username;
    private String userId;
    private String content;
    private String imageUrl;
    private Long date;
    private String destinataireID;

    public String getDestinataireID() {
        return destinataireID;
    }

    public void setDestinataireID(String destinataireID) {
        this.destinataireID = destinataireID;
    }

    public Message(){}

    public Message(String username, String userId, String content, String destinataireID, String imageUrl) {
        this.username = username;
        this.userId = userId;
        this.content = content;
        this.destinataireID = destinataireID;
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, String> getDate(){
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getLongDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Message)) return false;

        Message message = (Message) obj;
        return this.getUid().equals(message.getUid());
    }
}
