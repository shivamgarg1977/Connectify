package com.shivamgarg1977.connectify.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.shivamgarg1977.connectify.R;

public class SplashScreen extends AppCompatActivity {
    ExtendedFloatingActionButton getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getStarted= findViewById(R.id.get_started);
        getStarted.setOnClickListener((this::onClick));

    }

    public void onClick(View v){
       switch (v.getId()){
           case R.id.get_started:
               Intent intent=new Intent(SplashScreen.this,RegisterActivity.class);
               startActivity(intent);
       }
    }
}