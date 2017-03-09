package fr.miage.paris10.projetm1.helpu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by JOAN on 08/03/2017.
 */

public class ProfileActivity extends AppCompatActivity{

        private Button btnRemoveUser;


        private FirebaseAuth mFirebaseAuth;
        private FirebaseUser mFirebaseUser;
        private DatabaseReference mDatabaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            mFirebaseAuth = FirebaseAuth.getInstance();


            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            btnRemoveUser = (Button) findViewById(R.id.remove_user_button);

            btnRemoveUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  deleteAccount();

                }
            });
        }

        public void deleteUserData(String uid) {
            mDatabaseReference.child("users").child(uid).removeValue();
        }

        public void deleteAccount() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        deleteUserData(mFirebaseUser.getUid());
                        Toast.makeText(ProfileActivity.this, "Your account has been deleted successfully", Toast.LENGTH_SHORT).show();
                        loadLogInView();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        private void loadLogInView() {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }




}

