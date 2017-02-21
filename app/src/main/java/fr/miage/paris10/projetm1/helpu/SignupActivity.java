package fr.miage.paris10.projetm1.helpu;

/**
 * Created by david on 24/01/2017.
 */

import android.app.Activity;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth mAuthListener;
    private DatabaseReference mDatabaseReference;

    @Bind(R.id.input_firstName) EditText _firstNameText;
    @Bind(R.id.input_lastName) EditText _lastNameText;
    //@Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.spinner_level) Spinner _levelSpinner;
    //@Bind(R.id.input_mobile) EditText _mobileText;
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

        final Spinner spin = (Spinner) findViewById(R.id.spinner_level);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.level_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


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

/*
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            Toast.makeText(getBaseContext(), "Not Connected to Internet", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), "You are not connected to Internet!", Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage(R.string.internet_error_message)
                    .setTitle(R.string.signup_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();


        }*/

        if(!isOnline())
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setMessage(R.string.internet_error_message)
                    .setTitle(R.string.signup_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();

        }


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
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        String lastName = _lastNameText.getText().toString();
                        String firstName = _firstNameText.getText().toString();
                        // String address = _addressText.getText().toString();
                        String email = _emailText.getText().toString();
                        String level = _levelSpinner.getSelectedItem().toString();
                        //String mobile = _mobileText.getText().toString();
                        String password = _passwordText.getText().toString();

                        UserInformation userInformation = new UserInformation(lastName, firstName, level);

                        //if (user.isEmailVerified()){
                        // TODO: Implement your own signup logic here.
                        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //mFirebaseAuth.getCurrentUser().sendEmailVerification();
                                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            onSignupSuccess();
                                        } else {
                                            /*AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                            builder.setMessage(task.getException().getMessage())
                                                    .setTitle(R.string.login_error_title)
                                                    .setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();*/
                                            onSignupFailed();
                                        }
                                    }
                                });

                        mDatabaseReference.child("user").push().setValue(userInformation);

                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        /*}
        else{

            Toast.makeText(getBaseContext(), "Please check your email", Toast.LENGTH_LONG).show();
        }
        */
    }


    public void onSignupSuccess() {
        //_signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

       // _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String lastName = _lastNameText.getText().toString();
        String firstName = _firstNameText.getText().toString();
        // String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        //String mobile = _mobileText.getText().toString();
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

        /*
        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }
        */

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

        /*
        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }
        */

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
        NetworkInfo netInfoMob = cm.getNetworkInfo(cm.TYPE_MOBILE);
        NetworkInfo netInfoWifi = cm.getNetworkInfo(cm.TYPE_WIFI);
        if ((netInfoMob != null || netInfoWifi != null) && (netInfoMob.isConnectedOrConnecting() || netInfoWifi.isConnectedOrConnecting())) {
            return true;
        }
        return false;
    }



}