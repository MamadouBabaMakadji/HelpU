package fr.miage.paris10.projetm1.helpu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter adapter;
    private String destinataireID;
    private Message contenu;
    ArrayList messages = new ArrayList<String>();
    Button btn_send;
    EditText editText;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    public ChatRoomActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        final String to_userName;
        final Intent intent = getIntent();
        to_userName = intent.getStringExtra("user");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference = databaseReference.child("/users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                if(userInformation.getCompletName().equals(to_userName)) {
                    destinataireID = dataSnapshot.getKey();
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

        listView = (ListView) findViewById(R.id.listViewConversations);
        setTitle(to_userName);
        adapter = new ArrayAdapter(ChatRoomActivity.this,android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(adapter);
        btn_send = (Button) findViewById(R.id.button_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.champText_msg);
                final String msg = editText.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String from = user.getUid();
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference();
                databaseReference = databaseReference.child("/users");
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                        if(userInformation.getCompletName().equals(to_userName)){
                            destinataireID = dataSnapshot.getKey();
                            contenu = new Message(from,destinataireID,msg);
                            FirebaseDatabase.getInstance()
                                    .getReference().child("messages")
                                    .push()
                                    .setValue(contenu);
                            editText.setText("");
                            listView.setAdapter(adapter);
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
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference = databaseReference.child("/messages");
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentlyUser = user.getUid();
                Message message = dataSnapshot.getValue(Message.class);
                //messages.add("Destinataire :"+destinataireID);
                //destinataireID = "WvVnW8sZt0UxBpRPUV4FABrYCFM2";
                if( (message.getFrom().equals(currentlyUser) && message.getDestinataire().equals(destinataireID)) ||
                        (message.getFrom().equals(destinataireID) && message.getDestinataire().equals(currentlyUser))  ){
                    messages.add(message.getMessage());
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
