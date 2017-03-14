package fr.miage.paris10.projetm1.helpu;

/**
 * Created by JOAN on 02/02/2017.
 */

public class UserInformation {

    private String id;
    public String email;
    public String lastName;
    public String firstName;
    public String level;
    private String ufr;
    private String filliere;


    public UserInformation() {
    }

    public UserInformation(String email, String lastName, String firstName, String level) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
    }

    public UserInformation(String id, String email, String lastName, String firstName, String level, String ufr, String filliere) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
        this.ufr = ufr;
        this.filliere = filliere;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUfr() {
        return ufr;
    }

    public void setUfr(String ufr) {
        this.ufr = ufr;
    }

    public String getFilliere() {
        return filliere;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getCompletName() {
        return lastName+" "+firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
