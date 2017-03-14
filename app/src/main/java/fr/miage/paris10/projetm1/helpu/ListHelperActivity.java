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
import java.util.List;

public class ListHelperActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList db_userName = new ArrayList<String>() ;
    private ListHelperAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<String> listId = new ArrayList<String>();
        listId.add("WvVnW8sZt0UxBpRPUV4FABrYCFM2");
        listId.add("Yc6vaVgfUnW4C0CxLKE6cdINJCD2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_helper);
        setTitle("Helper");
        listView = (ListView) findViewById(R.id.list_helpeur);
        adapter = new ListHelperAdapter(ListHelperActivity.this,db_userName);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListHelperActivity.this, ChatRoomActivity.class);
                String item = listView.getItemAtPosition(position).toString();
                intent.putExtra("user", item);
                startActivity(intent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference = databaseReference.child("/users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentlyUser = user.getUid();
                if(listId.contains(dataSnapshot.getKey()) && !dataSnapshot.getKey().equals(currentlyUser) ){
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                    db_userName.add(userInformation.getCompletName());
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
