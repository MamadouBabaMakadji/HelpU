package fr.miage.paris10.projetm1.helpu;

/**
 * Created by david on 24/01/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;

    @Bind(R.id.input_firstName) EditText _firstNameText;
    @Bind(R.id.input_lastName) EditText _lastNameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.spinner_level) Spinner _levelSpinner;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ButterKnife.bind(this);
        Data data = new Data(this);
        InputStream inputStream = getResources().openRawResource(R.raw.nanterre);
        data.insertData(inputStream);
        final Spinner spinLevel = (Spinner) findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(this, R.array.level_array, android.R.layout.simple_spinner_item);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLevel.setAdapter(adapterLevel);

        final Spinner spinFilliere = (Spinner) findViewById(R.id.spinner_filliere);
        ArrayList<String> list=data.getAllFilliere();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.text, list);
        spinFilliere.setAdapter(adapter);


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if(!isOnline())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage(R.string.internet_error_message)
                    .setTitle(R.string.signup_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{

            if (!validate()) {
                onSignupFailed();
                return;
            }

            // _signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            String lastName = _lastNameText.getText().toString();
                            String firstName = _firstNameText.getText().toString();
                            String email = _emailText.getText().toString();
                            String level = _levelSpinner.getSelectedItem().toString();
                            String password = _passwordText.getText().toString();

                            register(email, password, lastName, firstName,level);

                            progressDialog.dismiss();

                        }

                    }, 3000);



        }
    }


    public void onSignupSuccess() {
        //_signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

       // _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String lastName = _lastNameText.getText().toString();
        String firstName = _firstNameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (lastName.isEmpty() || lastName.length() < 2) {
            _lastNameText.setError("At least 2 characters");
            valid = false;
        } else {
            _lastNameText.setError(null);
        }

        if (firstName.isEmpty() || firstName.length() < 2) {
            _firstNameText.setError("At least 2 characters");
            valid = false;
        } else {
            _firstNameText.setError(null);
        }

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
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Passwords do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) { // connected to the internet
            return true;
        }
        return false;

    }

    public void createUser(String uid, String email, String firstName, String lastName, String level) {
        UserInformation userInformation = new UserInformation(email, lastName, firstName, level);
        mDatabaseReference.child("users").child(uid).setValue(userInformation);
    }


    public void register(final String email, final String password,
                         final String firstName, final String lastName, final String level) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            sendVerificationEmail();
                            createUser(task.getResult().getUser().getUid(), email, firstName, lastName, level);

                            onSignupSuccess();
                        } else {
                            onSignupFailed();
                        }
                    }
                });
    }


    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
           user.sendEmailVerification();
            loadLogInView();
            Toast.makeText(getBaseContext(), "An email verification has been sent to you", Toast.LENGTH_LONG).show();

            /* TODO : add send verification
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // finish();
                                Toast.makeText(getBaseContext(), "Registered Successfully. Check your email", Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(getApplicationContext(), yourActivity.class));
                               loadLogInView();

                            }
                        }
                    });
            */



        }
    }

}