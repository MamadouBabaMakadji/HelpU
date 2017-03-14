package fr.miage.paris10.projetm1.helpu;

/**
 * Created by david on 3/13/17.
 */

public class BecomeHelper {
    private String nomMatiere;
    private String mailHelper;
    private String level;

    public BecomeHelper() {
    }

    public BecomeHelper(String nomMatiere, String mailHelper, String level) {
        this.nomMatiere = nomMatiere;
        this.mailHelper = mailHelper;
        this.level = level;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getMailHelper() {
        return mailHelper;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMailHelper(String mailHelper) {
        this.mailHelper = mailHelper;

    }
}
