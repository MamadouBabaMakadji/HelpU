package fr.miage.paris10.projetm1.helpu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BecomeHelperActivity extends AppCompatActivity{

  private Button btn_valider;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_become_helper);

    btn_valider = (Button) findViewById(R.id.button_valider);


    btn_valider.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(BecomeHelperActivity.this,ValidationActivity.class);
        startActivity(intent);
      }
    });

  }
}