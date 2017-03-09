package fr.miage.paris10.projetm1.helpu;

/**
 * Created by JOAN on 02/02/2017.
 */

public class UserInformation {

    public String email;
    public String lastName;
    public String firstName;
    public String level;

    public UserInformation() {
    }

    public UserInformation(String email, String lastName, String firstName, String level) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
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
