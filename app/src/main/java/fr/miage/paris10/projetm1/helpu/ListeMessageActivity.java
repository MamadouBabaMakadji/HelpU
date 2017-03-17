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
import java.util.HashSet;
import java.util.Set;

public class ListeMessageActivity extends AppCompatActivity {

    private ListView listView;
   private ArrayList db_userName = new ArrayList<String>() ;
    private ArrayList db_id_userName = new ArrayList<String>() ;
    private ArrayList<String> db_message = new ArrayList<String>();
    private ListMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_liste_message);
            setTitle("Messagerie");
            listView = (ListView) findViewById(R.id.list_msg);
            adapter = new ListMessageAdapter(ListeMessageActivity.this, db_userName);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ListeMessageActivity.this, ChatRoomActivity.class);
                    String item = listView.getItemAtPosition(position).toString();
                    String id_user = db_id_userName.get(position).toString();
                    intent.putExtra("user", item);
                    intent.putExtra("id", id_user);
                    startActivity(intent);
                }
            });
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference();
            databaseReference = databaseReference.child("/messages");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String currentlyUser = user.getUid();
                    Message m = dataSnapshot.getValue(Message.class);
                    if (m.getFrom().equals(currentlyUser)) {
                        db_message.add(m.getDestinataire());
                        adapter.notifyDataSetChanged();
                    }
                    if (m.getDestinataire().equals(currentlyUser)) {
                        db_message.add(m.getFrom());
                        adapter.notifyDataSetChanged();
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference da = database.getReference();
                    da = da.child("/users");
                    da.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshota, String s) {

                            String id = dataSnapshota.getKey();
                            UserInformation usertest = dataSnapshota.getValue(UserInformation.class);


                            Set set = new HashSet();
                            set.addAll(db_message);
                            ArrayList distinctList = new ArrayList(set);
                            if (distinctList.contains(id) && !db_userName.contains(usertest.getLastName() + " " + usertest.getFirstName())) {
                                db_userName.add(usertest.getLastName() + " " + usertest.getFirstName());
                                db_id_userName.add(id);
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
