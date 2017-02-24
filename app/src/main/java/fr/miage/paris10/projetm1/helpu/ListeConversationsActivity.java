package fr.miage.paris10.projetm1.helpu;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListeConversationsActivity extends AppCompatActivity {

    private final int photoProfil = R.drawable.user;
    private String []username = new String[]{"Mamadou MAK","David Meimoun","Joan Medjid"};
    private ConversationAdapter adapter;
    //private FireBase reference;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference dataFirstName;
    private DatabaseReference dataLastName;
    ListView listView;
    private ProfilConversation profil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_conversations);
        listView = (ListView) findViewById(R.id.mes_conversations);
        adapter = new ConversationAdapter(getApplicationContext(),R.layout.row_convers);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        dataFirstName = mDatabaseReference.child("firstName");
        dataLastName = mDatabaseReference.child("lastName");
        /*dataFirstName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot noms : dataSnapshot.getChildren()){
                    username = (String) noms.getValue();
                    profil = new ProfilConversation(photoProfil,username);
                    adapter.add(profil);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //while(mDatabaseReference.child("user").child("firstName"))
        for(int i = 0; i<username.length;i++){
            profil = new ProfilConversation(photoProfil,username[i]);
            adapter.add(profil);
            listView.setAdapter(adapter);
        }
    }
}
