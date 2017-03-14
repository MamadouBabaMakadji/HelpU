package fr.miage.paris10.projetm1.helpu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by JOAN on 14/03/2017.
 */

public class PrintInfo extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_info);


        if (getIntent().hasExtra("user")) {
            UserInformation u = (UserInformation)getIntent().getParcelableExtra("user");
            String info = u.getFirstName() + " " + u.getLastName() + " " + u.getUfr();
            Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
        }
    }

}
