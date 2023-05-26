package com.shivamgarg1977.connectify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shivamgarg1977.connectify.R;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth= FirebaseAuth.getInstance();

        userEmail=findViewById(R.id.user_email);

    }

    private void checkUserStatus(){
        FirebaseUser user= mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(ProfileActivity.this, SplashScreen.class));
            finish();
        }else{
            userEmail.setText(user.getEmail().toString());
        }

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }
}