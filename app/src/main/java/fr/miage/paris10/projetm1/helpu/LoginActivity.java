package fr.miage.paris10.projetm1.helpu;

/**
 * Created by david on 24/01/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Bind;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    @Bind(R.id.link_reset_password) TextView _resetPasswordLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _resetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if(!isOnline())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage(R.string.internet_error_message)
                    .setTitle(R.string.signup_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{

            if (!validate()) {
                onLoginFailed();
                return;
            }

            //_loginButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            // TODO: Implement your own authentication logic here.


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            String email = _emailText.getText().toString();
                            String password = _passwordText.getText().toString();
                            // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            //   final String currentlyUser = user.getUid();
                            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                if( user.isEmailVerified()){
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference refData =  database.getReference();
                                                    refData.child("users").addListenerForSingleValueEvent(new ValueEventListener() {


                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            UserInformation u = null;
                                                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            Log.d(LoginActivity.class.getSimpleName(),id);
                                                            for (DataSnapshot data : dataSnapshot.getChildren() ) {

                                                                if(data.getKey().equals(id)){
                                                                    u =  data.getValue(UserInformation.class);
                                                                    u.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                }
                                                            }

                                                            //     }

                                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                            // UserInformation a = new UserInformation("WvVnW8sZt0UxBpRPUV4FABrYCFM2","33012900@u-paris10.fr","david","meimoun","M1","SCIENCES TECHNOLOGIES ET SANTE","Methodes informatiques appliquees a la gestion des entreprises (MIAGE)");
                                                            // UserInformation a = u ;
                                                            i.putExtra("user", u);
                                                            startActivity(i);
                                                            //startActivityForResult(i, REQUEST_SIGNUP);
                                                            finish();
                                                            //   overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                                            onLoginSuccess();


                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });








                                                    //      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    //      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    //      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    //      startActivity(intent);

                                                }
                                                else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                    builder.setMessage(R.string.verify_email_error_message)
                                                            .setTitle(R.string.signup_error_title);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            } else {
                                                onLoginFailed();
                                            }
                                        }
                                    });

                            progressDialog.dismiss();
                        }
                    }, 3000);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP && resultCode == RESULT_OK) {

            // TODO: Implement successful signup logic here
            // By default we just finish the Activity and log them in automatically
            this.finish();

        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        // _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        //_loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Pattern p = Pattern.compile(".+@u-paris10.fr+");
        Matcher m = p.matcher(email);

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        }
        else if (!m.matches()){
            _emailText.setError("Domain name must be u-paris10.fr");
            valid = false;
        }
        else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());

    }

}