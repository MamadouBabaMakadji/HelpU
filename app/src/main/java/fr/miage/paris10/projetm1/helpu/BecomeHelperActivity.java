package fr.miage.paris10.projetm1.helpu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class BecomeHelperActivity extends AppCompatActivity{
  List<String> listEc = new ArrayList<String>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    //TODO
    final UserInformation user = getIntent().getExtras().getParcelable("user");
    // final UserInformation user = new UserInformation("Yc6vaVgfUnW4C0CxLKE6cdINJCD2","32039713@u-paris10.fr","mamadou","makadji baba","M1","SCIENCES TECHNOLOGIES ET SANTE","Methodes informatiques appliquees a la gestion des entreprises (MIAGE)");
    //  final UserInformation user = new UserInformation("WvVnW8sZt0UxBpRPUV4FABrYCFM2","33012900@u-paris10.fr","david","meimoun","M1","SCIENCES TECHNOLOGIES ET SANTE","Methodes informatiques appliquees a la gestion des entreprises (MIAGE)");
    // final UserInformation user = new UserInformation("WvVnW8sZt0UxBpRPUV4FABrYCFM2","33012900@u-paris10.fr","david","meimoun","M1","SCIENCES HUMAINES ET SOCIALES","Psychologie");
    //final UserInformation user = new UserInformation("Yc6vaVgfUnW4C0CxLKE6cdINJCD2","32039713@u-paris10.fr","mamadou","makadji baba","M1","SCIENCES HUMAINES ET SOCIALES","Psychologie");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_become_helper);
    Data data = new Data(this);
    final Spinner spinEC = (Spinner) findViewById(R.id.spinner_BecomeHelper_ec);


     listEc =  data.getEc(user.getFilliere(),user.getLevel());
    if(listEc.isEmpty()){
      Toast.makeText(getBaseContext(), "Error Database", Toast.LENGTH_LONG).show();

    }
    else{
      ArrayAdapter<String> adapterEc=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text, listEc);
      spinEC.setAdapter(adapterEc);

      Button  btn_valider = (Button) findViewById(R.id.button_BecomeHelper_valider);

      btn_valider.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(listEc.isEmpty()){
            Toast.makeText(getBaseContext(), "Error Database", Toast.LENGTH_LONG).show();
          }
          else{
            Intent intent = new Intent(BecomeHelperActivity.this,ValidationActivity.class);
            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            BecomeHelper bec = new BecomeHelper(user.getFilliere(),user.getEmail(),user.getLevel());

            //TODO ajout du test de la connexion internet
            //test caracteres interdit pour la base de donnée
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


            mDatabaseReference.child("BecomeHelper").child(user.getFilliere()).child(tmp).child(user.getId()).setValue(bec);

            startActivity(intent);
          }

        }
      });
    }


  }
}