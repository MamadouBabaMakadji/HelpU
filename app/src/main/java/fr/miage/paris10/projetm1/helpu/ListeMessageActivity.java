package fr.miage.paris10.projetm1.helpu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListeMessageActivity extends AppCompatActivity {

    ListView listView;
    ArrayList db_userName = new ArrayList<String>() ;
    int icon = R.drawable.user;
    ListMessageAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_message);
        setTitle("Messagerie");
        listView = (ListView) findViewById(R.id.list_msg);
        adapter = new ListMessageAdapter(ListeMessageActivity.this,db_userName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListeMessageActivity.this, ChatRoomActivity.class);
                String item = listView.getItemAtPosition(position).toString();
                intent.putExtra("user",item);
                startActivity(intent);
            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference = databaseReference.child("/users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentlyUser = user.getUid();
                if(!dataSnapshot.getKey().equals(currentlyUser)){
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                    db_userName.add(userInformation.getFirstName());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
