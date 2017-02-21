package fr.miage.paris10.projetm1.helpu;

/**
 * Created by JOAN on 02/02/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

public class UserInformation {

    public String lastName;
    public String firstName;
    public String level;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserInformation() {
    }

    public UserInformation(String lastName, String firstName, String level) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
    }
}
