package com.qd8s.werewolf2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.qd8s.werewolf2.User;

//Google Inc. All Rights Reserved.
public class Authentication extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "firebase";
    private EditText userEmail;
    private EditText userPassword;
    private FirebaseAuth mAuth;
    private EditText userName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        userEmail = (EditText) findViewById(R.id.editText2);
        userPassword = (EditText) findViewById(R.id.editText);
        userName = (EditText) findViewById(R.id.userName);

        //Log.i(TAG, userEmail.getText().getSt);
        findViewById(R.id.newUser).setOnClickListener(this);
        findViewById(R.id.Authenticate_Button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if mUser is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in mUser's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the mUser.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Authentication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }

                    }
                });
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in mUser's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the mUser.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Authentication.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            valid = false;
        } else {
            userEmail.setError(null);
            }

        String password = userPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Required.");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        String name = userName.getText().toString();
        if (TextUtils.isEmpty(name)){
            userName.setError("required.");
            valid = false;
        }
        else {
            userName.setError(null);
        }

        return valid;
    }


    public void startGameMenu() {
        Intent intent = new Intent(this, GameMenu.class);
        User user = new User(userEmail.getText().toString(), userName.getText().toString());

        //Log.i(client.getPlayer().getName(), "Is mny name");
        intent.putExtra("Client_Data", user);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        /*hideProgressDialog();
        if (mUser != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    mUser.getEmail(), mUser.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, mUser.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!mUser.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (i == R.id.newUser) {
            createAccount(userEmail.getText().toString(), userPassword.getText().toString());
            System.out.println("i am here new account");
            if ( !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){
                startGameMenu();}
        } else if (i == R.id.Authenticate_Button) {
            signIn(userEmail.getText().toString(), userPassword.getText().toString());
            System.out.println("i am here exsisting account");
            if ( !TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
                startGameMenu();
            }
        }
    }

}

