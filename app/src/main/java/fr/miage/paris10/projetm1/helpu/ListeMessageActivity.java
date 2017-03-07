package fr.miage.paris10.projetm1.helpu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

public class ListeMessageActivity extends AppCompatActivity {

    ListView listView;
    String[] users_name = {"Mamadou", "Joan"};
    //ArrayList<String> users_name = new ArrayList<>() ;
    int icon = R.drawable.user;
    ListMessageAdapter adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_message);
        listView = (ListView) findViewById(R.id.list_msg);
        adapter = new ListMessageAdapter(ListeMessageActivity.this,users_name);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListeMessageActivity.this, ChatRoomActivity.class);
                //String item = ((TextView) view).getText().toString();
                String item = listView.getItemAtPosition(position).toString();
                //String item = listView.getSelectedItem().toString();
                intent.putExtra("user",item);
                startActivity(intent);
            }
        });
        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                //String lesNoms = databaseReference.child("firstName").toString();
                //users_name.add(lesNoms);
                users_name.add(value);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
        });*/
    }
}
