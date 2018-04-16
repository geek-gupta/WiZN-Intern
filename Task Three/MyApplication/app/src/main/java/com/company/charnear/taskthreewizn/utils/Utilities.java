package com.company.charnear.taskthreewizn.utils;

import android.app.Activity;
import android.content.Intent;

import com.company.charnear.taskthreewizn.activity.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gaurav on 14/4/18.
 */

public class Utilities {

    public static void launchHomeScreen(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public  static String encodeEmail(String email){
        email =  email.replace('.','-');
        email = email.replace('#','=');
        email = email.replace('$','+');
        email = email.replace('[',')');
        email = email.replace(']','(');
        email = email.replace('/','|');
        return  email;
    }

    public static void sendIdTokenToServer(FirebaseDatabase db, String token, String name, String email, String phone){
        DatabaseReference myref= db.getReference().child("users").child(encodeEmail(email.trim().toLowerCase()));
        myref.child("Uname").setValue(name);
        myref.child("UToken").setValue(token);
        myref.child("Uphone").setValue(phone);
    }
    public  static  void updateToken(FirebaseDatabase db, String email, String token){

        DatabaseReference myref= db.getReference().child("users").child(encodeEmail(email.trim().toLowerCase()));
        myref.child("UToken").setValue(token);
    }
}
