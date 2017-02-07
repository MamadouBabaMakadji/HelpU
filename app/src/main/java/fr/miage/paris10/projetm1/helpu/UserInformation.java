package fr.miage.paris10.projetm1.helpu;

/**
 * Created by JOAN on 02/02/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

public class UserInformation {

    public String name;
    public String address;
    public String mobile;



    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserInformation() {
    }

    public UserInformation(String name, String address, String mobile) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
    }
}
