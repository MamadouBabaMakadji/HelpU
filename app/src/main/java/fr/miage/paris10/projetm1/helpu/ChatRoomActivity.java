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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private String to_userName;
    ListView listView;
    ArrayAdapter<String> adapter;
    List messages = new ArrayList<String>();
    Button btn_send;
    EditText editText;
    DatabaseReference d;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listViewConversations);
        to_userName = intent.getStringExtra("user");
        setTitle(to_userName);
        adapter = new ArrayAdapter<String>(ChatRoomActivity.this,android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(adapter);
        btn_send = (Button) findViewById(R.id.button_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.champText_msg);
                String msg = editText.getText().toString();
                String author = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                Message message = new Message(author,msg);
                FirebaseDatabase.getInstance()
                        .getReference().child("message")
                        .push()
                        .setValue(message);
                editText.setText("");
                listView.setAdapter(adapter);
            }
        });
        database = FirebaseDatabase.getInstance();
        d = database.getReference();
        d = d.child("/message");
        d.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message.getMessage());
                adapter.notifyDataSetChanged();
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
