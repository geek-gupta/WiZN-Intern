package com.company.charnear.taskthreewizn.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.charnear.taskthreewizn.R;
import com.company.charnear.taskthreewizn.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private Button signUpButton, loginButton;
    private EditText mEmailView, mPasswordView;
    public FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Utilities.launchHomeScreen(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        signUpButton = (Button) findViewById(R.id.signUpButton);
        mEmailView = (EditText) findViewById(R.id.userNameLoginText);
        mPasswordView = (EditText) findViewById(R.id.passwordLoginText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpButton:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.loginButton:
                if (validateInput()) {
                    attemptLogin();

                }

        }
    }
    private void attemptLogin() {


        mEmailView.setError(null);
        mPasswordView.setError(null);


        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Enter Password");
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Enter Email");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("Enter Valid Email");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            onSignInRegister(email, password);
        }
    }


    private void onSignInRegister(final String email, final String password) {
        OnCompleteListener<AuthResult> onCompleteListener =
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utilities.updateToken(FirebaseDatabase.getInstance(), email, FirebaseInstanceId.getInstance().getToken());
                            loginSuccessful();

                        } else {
                            Toast.makeText(LoginActivity.this, "Pls Register with us ", Toast.LENGTH_SHORT).show();
                        }
                    }
                };


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, onCompleteListener);
    }
    void loginSuccessful() {
        Utilities.launchHomeScreen(this);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    boolean validateInput() {

        boolean result = true;
        String username = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (username.trim().length() == 0) {
            mEmailView.setError("Please enter userName");
            result = false;
        }
        if (password.trim().length() == 0) {
            mPasswordView.setError("Please enter password");
            result = false;
        }
        return true;
    }
}
