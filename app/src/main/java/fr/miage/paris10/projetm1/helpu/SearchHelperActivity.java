package fr.miage.paris10.projetm1.helpu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 12/03/2017.
 */

public class SearchHelperActivity extends AppCompatActivity {
   private  FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference refData =  database.getReference();
    List<String> listEc = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final UserInformation user = getIntent().getExtras().getParcelable("user");

        //TODO

        // final UserInformation user = new UserInformation("Yc6vaVgfUnW4C0CxLKE6cdINJCD2","32039713@u-paris10.fr","mamadou","makadji baba","M1","SCIENCES TECHNOLOGIES ET SANTE","Methodes informatiques appliquees a la gestion des entreprises (MIAGE)");
        //final UserInformation user = new UserInformation("WvVnW8sZt0UxBpRPUV4FABrYCFM2","33012900@u-paris10.fr","david","meimoun","M1","DROIT ECONOMIE GESTION","Finance");
        // final UserInformation user = new UserInformation("WvVnW8sZt0UxBpRPUV4FABrYCFM2","33012900@u-paris10.fr","david","meimoun","M1","SCIENCES HUMAINES ET SOCIALES","Psychologie");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_helper);
        Data data = new Data(this);
        final Spinner spinEC = (Spinner) findViewById(R.id.spinner_SearchHelper_ec);

        //le level n'est pas pris en compte

        listEc = data.getEc(user.getFilliere(),user.getLevel());
        if(listEc.isEmpty()){
            Toast.makeText(getBaseContext(), "Error Database", Toast.LENGTH_LONG).show();

        }
        else{
            ArrayAdapter<String> adapterEc=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text, listEc);
            spinEC.setAdapter(adapterEc);
            Button btn_valider = (Button) findViewById(R.id.button_SearchHelper_valider);

            btn_valider.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(listEc.isEmpty()){
                        Toast.makeText(getBaseContext(), "Error Database", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String tmp = spinEC.getSelectedItem().toString();
                        if(tmp.contains(".")) {
                            tmp = spinEC.getSelectedItem().toString().replace(".","");
                        }
                        else if (tmp.contains("#")){
                            tmp = spinEC.getSelectedItem().toString().replace("#","");
                        }
                        else if (tmp.contains("[")){
                            tmp = spinEC.getSelectedItem().toString().replace("[","");
                        }
                        else if (tmp.contains("]")){
                            tmp = spinEC.getSelectedItem().toString().replace("]","");
                        }


                        refData.child("BecomeHelper")
                                .child(user.getFilliere()).child(tmp)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final List<String> listKeys = new ArrayList<String>();
                                        for (DataSnapshot data : dataSnapshot.getChildren() ) {
                                            listKeys.add(data.getKey());

                                        }
                                        if(listKeys.isEmpty()){
                                            Intent intent = new Intent(SearchHelperActivity.this,ValidationDemandeAideActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Intent intent = new Intent(SearchHelperActivity.this,ListHelperActivity.class);
                                            intent.putExtra("listKey", (ArrayList<String>) listKeys);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                    }








                }
            });

        }

    }
}