package fr.miage.paris10.projetm1.helpu;

/**
 * Created by JOAN on 02/02/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class UserInformation implements Parcelable{

    private String id;
    public String email;
    public String lastName;
    public String firstName;
    public String level;
    private String ufr;
    private String filliere;


    public UserInformation() {
    }

    public UserInformation( String email, String lastName, String firstName, String level, String ufr, String filliere) {

        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.level = level;
        this.ufr = ufr;
        this.filliere = filliere;
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

    public UserInformation(Parcel in) {
        this.id = in.readString();
        this.email = in.readString();
        this.lastName = in.readString();
        this.firstName = in.readString();
        this.level = in.readString();
        this.ufr = in.readString();
        this.filliere = in.readString();

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

    public static final Parcelable.Creator<UserInformation> getCreator() {
        return CREATOR;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.email);
        dest.writeString(this.lastName);
        dest.writeString(this.firstName);
        dest.writeString(this.level);
        dest.writeString(this.ufr);
        dest.writeString(this.filliere);


    }

    public static final Parcelable.Creator<UserInformation> CREATOR = new Parcelable.Creator<UserInformation>() {


        @Override
        public UserInformation createFromParcel(Parcel in) {
            return new UserInformation(in);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }


    };









}
