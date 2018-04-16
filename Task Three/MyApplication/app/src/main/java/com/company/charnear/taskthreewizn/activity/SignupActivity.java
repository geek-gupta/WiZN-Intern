package com.company.charnear.taskthreewizn.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.charnear.taskthreewizn.R;
import com.company.charnear.taskthreewizn.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private FirebaseDatabase db;

    public static String USER_NAME;

    private EditText userNameText, phoneNumberText, emailText, passwordText;
    private Button register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        actionBar = getSupportActionBar();
        actionBar.hide();

        db = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        emailText = (EditText) findViewById(R.id.email_text);
        userNameText = (EditText) findViewById(R.id.userNameText);
        phoneNumberText =(EditText)findViewById(R.id.phoneText);

        passwordText =(EditText) findViewById(R.id.passwordText);
        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                if (validateInput())
                    attemptRegister();
                break;
        }
    }

    void registerUser(final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String token = FirebaseInstanceId.getInstance().getToken();
                        if (task.isSuccessful() && token!=null) {

                            Utilities.sendIdTokenToServer(FirebaseDatabase.getInstance(),token
                                    , userNameText.getText().toString()
                                    , emailText.getText().toString(),
                                    phoneNumberText.getText().toString());
                            USER_NAME = userNameText.getText().toString();
//                            Intent intent = new Intent(SignupActivity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
                            Utilities.launchHomeScreen(SignupActivity.this);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            }
                            Toast.makeText(SignupActivity.this, "user Regsitered", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(SignupActivity.this, "Unable to Register ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void attemptRegister() {


        emailText.setError(null);
        passwordText.setError(null);
        phoneNumberText.setError(null);
        userNameText.setError(null);


        String username = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        String phone = phoneNumberText.getText().toString();
        String email = emailText.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordText.setError("Enter Password");
            focusView = passwordText;
            cancel = true;
        }


        if (TextUtils.isEmpty(email)) {
            emailText.setError("Enter Email");
            focusView = emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailText.setError("Enter Valid Email");
            focusView = emailText;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {


            registerUser(email, password);
        }
    }
    boolean validateInput() {


        String username = userNameText.getText().toString();

        String password = passwordText.getText().toString();
        String phone = phoneNumberText.getText().toString();
        String email = emailText.getText().toString();
        if (username.trim().length() == 0) {
            userNameText.setError("Please enter userName");

        }
        if (password.trim().length() == 0) {
            passwordText.setError("Please enter password");

        }
        if (phone.trim().length() == 0) {
            phoneNumberText.setError("Please enter phone");

        }else if(phone.trim().length() != 10){
            phoneNumberText.setError("Enter valid Phone number");

        }else if(phone.trim().contains("+91")){
            if(phone.trim().length() != 12){
                phoneNumberText.setError("Enter valid phone number");

            }
        }
        if (email.trim().length() == 0) {
            emailText.setError("Please enter email");

        }

        return true;
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
