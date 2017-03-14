package fr.miage.paris10.projetm1.helpu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by david on 12/03/2017.
 */

public class SearchHelperActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO

        String filliere = "Methodes informatiques appliquees a la gestion des entreprises (MIAGE)";
        String level = "M1";


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_helper);
        Data data = new Data(this);
        final Spinner spinEC = (Spinner) findViewById(R.id.spinner_BecomeHelper_ec);
        //le level n'est pas pris en compte
        ArrayList<String> listEc= data.getEc(filliere,level);
        ArrayAdapter<String> adapterEc=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text, listEc);
        spinEC.setAdapter(adapterEc);
        Button btn_valider = (Button) findViewById(R.id.button_valider);

        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchHelperActivity.this,ValidationDemandeAideActivity.class);
                startActivity(intent);
            }
        });

    }
}