package fr.miage.paris10.projetm1.helpu;

/**
 * Created by MBM on 21/02/2017.
 */

public class ProfilConversation {
    private int image;
    private String username;

    public ProfilConversation(int image, String username) {
        this.image = image;
        this.username = username;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
