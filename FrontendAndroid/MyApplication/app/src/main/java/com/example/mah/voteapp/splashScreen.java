package com.example.mah.voteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.example.mah.voteapp.login.MyPREFERENCES;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent splash = new Intent(splashScreen.this,cllfstlogo.class);
                splash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                splash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(splash);
                finish();



            }
        }, 3000);
    }


}

