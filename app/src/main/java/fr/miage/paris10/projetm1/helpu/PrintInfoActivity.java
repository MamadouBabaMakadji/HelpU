package fr.miage.paris10.projetm1.helpu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by JOAN on 15/03/2017.
 */

public class PrintInfoActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_info);

        UserInformation user = getIntent().getExtras().getParcelable("user");
        TextView printInfo = (TextView) findViewById(R.id.print);
        printInfo.setText( " FirstName : " +
                user.getFirstName() + "\n" + " LastName : " + user.getLastName() +
                "\n" + " Email : " + user.getEmail() + "\n" + " UFR : " +
                user.getUfr() + "\n" + " Filiere : " + user.getFilliere() +
                "\n" + " Level : " + user.getFilliere());
    }
}
